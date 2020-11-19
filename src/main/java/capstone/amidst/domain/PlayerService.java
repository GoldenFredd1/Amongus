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

    public Player findById(int playerId) {
        return repository.findById(playerId);
    }

    public Player findByAppUserId(int appUserId) {
        return repository.findByAppUserId(appUserId);
    }

    public Player findByUsername(String username){
        return repository.findByUserName(username);
    }

    public List<Player> findByIsImposter(boolean isImposter) {
        return repository.findByIsImposter(isImposter);
    }

    public List<Player> findByIsDead(boolean isDead) {
        return repository.findByIsDead(isDead);
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

    public Result<Player> update(Player player) {
        Result<Player> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Player>> violations = validator.validate(player);

        if (!repository.update(player)) {
            String msg = String.format("playerId: %s, not found", player.getPlayerId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Player> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }
        return result;
    }

    public boolean deleteById(int playerId) {
        return repository.deleteById(playerId);
    }

    public boolean deleteAllButRealPlayer() {
        System.out.println("You've made it to deleteAllButRealPlayer() in the Player service.");
        return repository.deleteAllButRealPlayer();
    }

    public boolean updateResetPlayer(Player player) {
        return repository.updateResetPlayer(player);
    }

    public Result<Player> killPlayer(int playerId){
        Player playerBeingKilled = repository.findById(playerId);
        Result<Player> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Player>> violations = validator.validate(playerBeingKilled);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Player> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (playerBeingKilled.getPlayerId() <= 0) {
            result.addMessage("playerId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.updateIsDead(playerBeingKilled)) {
            String msg = String.format("playerId: %s, not found", playerBeingKilled.getPlayerId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }
}
