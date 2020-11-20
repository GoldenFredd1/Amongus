package capstone.amidst.domain;

import capstone.amidst.data.GameRepository;
import capstone.amidst.data.PlayerRepository;
import capstone.amidst.data.VotesRepository;
import capstone.amidst.models.Game;
import capstone.amidst.models.Votes;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class VoteService {

    final private VotesRepository votesRepository;
    final private GameRepository gameRepository;
    final private ComputerPlayers computerPlayersService;
    final private PlayerRepository playerRepository;

    public VoteService(VotesRepository votesRepository, GameRepository gameRepository, ComputerPlayers computerPlayersService,
                       PlayerRepository playerRepository) {
        this.votesRepository = votesRepository;
        this.gameRepository = gameRepository;
        this.computerPlayersService = computerPlayersService;
        this.playerRepository = playerRepository;
    }

    public List<Votes> findAll(){return votesRepository.findAll();}
    public Votes findById(int voteId){return votesRepository.findById(voteId);}

    public Result<Votes> add(Votes vote){
        Result<Votes> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Votes>> violations = validator.validate(vote);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Votes> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }
        if (vote.getVoteId() != 0) {
            result.addMessage("voteId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        vote = votesRepository.add(vote);
        VoteCounter(vote);
        result.setPayload(vote);

        return result;
    }

    public void VoteCounter(Votes vote){
        List<Game> allPlayers = gameRepository.findByGameCode(vote.getGameRoomCode());
        List<Integer> votingList = computerPlayersService.ComputerVote(vote.getGameRoomCode());
        votingList.add(vote.getVotedForPlayerId());
        int MostVotedForPlayer = 0;
        int PlayerId = 0;
        for (Game allPlayer : allPlayers) {
            int count=0;
            for (Integer integer : votingList) {
                if (allPlayer.getPlayerId() == integer) {
                    count++;
                }
            }
            if (count > MostVotedForPlayer) {
                MostVotedForPlayer = count;
                PlayerId = allPlayer.getPlayerId();
            } else if(count == MostVotedForPlayer){
                PlayerId = allPlayer.getPlayerId();
            }
        }
        //update player...
        playerRepository.updateIsDead(playerRepository.findById(PlayerId));
    }

    public boolean deleteAll() {
        return votesRepository.deleteAll();
    }
}
