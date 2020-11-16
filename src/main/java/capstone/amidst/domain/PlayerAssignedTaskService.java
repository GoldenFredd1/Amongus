package capstone.amidst.domain;

import capstone.amidst.data.PlayerAssignedTaskRepository;
import capstone.amidst.models.PlayerAssignedTask;
import capstone.amidst.models.Task;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class PlayerAssignedTaskService {
    private final PlayerAssignedTaskRepository repository;

    public PlayerAssignedTaskService(PlayerAssignedTaskRepository repository) {
        this.repository = repository;
    }

    public List<PlayerAssignedTask> findAllByGame(String gameCode){
        return repository.findAllByGame(gameCode);
    }

    public List<PlayerAssignedTask> findAPlayersTasks(String gameCode, int playerId){
        return repository.findAPlayersTasks(gameCode,playerId);
    }

    public PlayerAssignedTask specificTask(String gameCode, int playerId){
        return repository.specificTask(gameCode,playerId);
    }
    public PlayerAssignedTask findById(int taskId){
        return repository.findById(taskId);
    }

    public Result<PlayerAssignedTask> update(PlayerAssignedTask PAT){
        Result<PlayerAssignedTask> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PlayerAssignedTask>> violations = validator.validate(PAT);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<PlayerAssignedTask> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (PAT.getTaskId() <= 0) {
            result.addMessage("taskId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.updateTask(PAT)) {
            String msg = String.format("taskId: %s, not found", PAT.getTaskId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        else{
            repository.updateTask(PAT);
        }
        System.out.println(result);
        return result;
    }
}
