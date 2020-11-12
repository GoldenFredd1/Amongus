package capstone.amidst.domain;

import capstone.amidst.data.PlayerRepository;
import capstone.amidst.models.Player;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    // Methods
    public List<Player> findAll() {
        return repository.findAll();
    }


    public Result<Player> add(Player player) {
        Result<Player> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Player>> violations = validator.validate(player);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Player> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (player.getPlayerId() != 0) {
            result.addMessage("playerId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        player = repository.add(player);
        result.setPayload(player);
        return result;
    }

    public Result<Player> addComputerPlayer(Player player) {

        //nothing to validate since the insert is holding the data.
        Result<Player> result = new Result<>();

        if (player.getPlayerId() != 0) {
            result.addMessage("playerId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        player = repository.addComputerPlayer(player);
        result.setPayload(player);
        return result;
    }

}
