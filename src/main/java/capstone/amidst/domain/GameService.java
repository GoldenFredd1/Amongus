package capstone.amidst.domain;

import capstone.amidst.data.GameRepository;
import capstone.amidst.data.PlayerRepository;
import capstone.amidst.models.Game;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class GameService {

    private final GameRepository repository;
    private final PlayerRepository playerRepository;


    public GameService(GameRepository repository, PlayerRepository playerRepository) {
        this.repository = repository;
        this.playerRepository = playerRepository;
    }

    public List<Game> findAll() {
        return repository.findAll();
    }

    public Game findById(int gameId) {
        return repository.findById(gameId);
    }

    public Game findByGameRoomCode(String gameRoomCode){
        return repository.findByGameCode(gameRoomCode).stream().findFirst().orElse(null);
    }

    public Game findByPlayerGame(String gameCode, int playerId){
        return repository.findByPlayerGameCode(playerId, gameCode);
    }


    public Result<Game> add(Game game) {
        Result<Game> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Game>> violations = validator.validate(game);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Game> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (game.getGameId() != 0) {
            result.addMessage("gameId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        game = repository.add(game);
        result.setPayload(game);
        return result;
    }

    public Result<Game> update(Game game) {
        Result<Game> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Game>> violations = validator.validate(game);

        if (!repository.update(game)) {
            String msg = String.format("gameId: %s, not found", game.getGameId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Game> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }
        return result;
    }


    public boolean deleteById(int gameId) {
        return repository.deleteById(gameId);
    }

    public boolean isGameOver(String gameRoomCode) {
        return repository.checkEndGame(gameRoomCode);
    }

    public boolean didImposterWin(String gameRoomCode) {
        return repository.didImposterWin(gameRoomCode);
    }

    public boolean deadBodyInRoomCheck(Game game){
        List<Game> allPlayers = repository.findByGameCode(game.getGameRoomCode());
        for (Game allPlayer : allPlayers) {
            Game nonCurrentPlayer = repository.findByPlayerGameCode(allPlayer.getPlayerId(), game.getGameRoomCode());

            if (playerRepository.findById(allPlayer.getPlayerId()).isDead()
            && game.getRoomId() == nonCurrentPlayer.getRoomId()) {
                //this is the dead person...
                return true;
            }
        }

        return false;
    }

}

