package capstone.amidst.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void emptyShouldFail(){
        Room room = new Room();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertEquals(1, violations.size());
    }

    @Test
    void negativeIdShouldFail(){
        Room room = new Room();
        room.setRoomId(-1);
        room.setRoomName("test room");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertEquals(1, violations.size());

        ConstraintViolation<Room> first = violations.stream().findFirst().orElse(null);
        assertEquals("Room ID must be 0 or higher.", first.getMessage());
    }
    @Test
    void emptyNameShouldFail(){
        Room room = new Room();
        room.setRoomId(1);
        room.setRoomName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertEquals(1, violations.size());

        ConstraintViolation<Room> first = violations.stream().findFirst().orElse(null);
        assertEquals("Room name cannot be empty.", first.getMessage());
    }

    @Test
    void shouldPass(){
        Room room = new Room();
        room.setRoomId(11);
        room.setRoomName("test room");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertEquals(0, violations.size());
    }



}