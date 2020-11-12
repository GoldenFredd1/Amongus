package capstone.amidst.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void emptyShouldFail(){
        Task task = new Task();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
    }

    @Test
    void positiveTaskId(){
        Task task = new Task();
        task.setTaskId(-10);
        task.setTaskName("test task");
        task.setRoomId(3);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(1, violations.size());

        ConstraintViolation<Task> first = violations.stream().findFirst().orElse(null);
        assertEquals("Task ID must be 0 or higher.", first.getMessage());
    }

    @Test
    void positiveRoomId(){
        Task task = new Task();
        task.setTaskId(13);
        task.setTaskName("test task");
        task.setRoomId(-3);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(1, violations.size());

        ConstraintViolation<Task> first = violations.stream().findFirst().orElse(null);
        assertEquals("Room ID must be 0 or higher.", first.getMessage());
    }

    @Test
    void shouldPass(){
        Task task = new Task();
        task.setTaskId(13);
        task.setTaskName("test task");
        task.setRoomId(3);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(0, violations.size());
    }

}