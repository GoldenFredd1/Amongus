package capstone.amidst.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAssignedTaskTest {

    @Test
    void NegativePlayerIdShouldFail(){
        PlayerAssignedTask PAT = new PlayerAssignedTask();
        PAT.setPlayerId(-10);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PlayerAssignedTask>> violations = validator.validate(PAT);
        assertEquals(1, violations.size());
    }

    @Test
    void NegativeTaskIdShouldFail(){
        PlayerAssignedTask PAT = new PlayerAssignedTask();
        PAT.setTaskId(-10);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PlayerAssignedTask>> violations = validator.validate(PAT);
        assertEquals(1, violations.size());
    }

}