package capstone.amidst.data;

import capstone.amidst.models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RoomJdbcRepositoryTest {

    @Autowired
    RoomJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllRoom(){
        List<Room> room = repository.findAll();

        assertNotNull(room);
        assertEquals(7, room.size());
    }

    @Test
    void shouldFindByValidRoomId(){
        Room room = repository.findById(3);

        assertEquals(3,room.getRoomId());
        assertEquals("Toys R Us",room.getRoomName());
    }

    @Test
    void shouldNotFindWithCrazyId() {
        Room room = repository.findById(7777);
        assertNull(room);
    }

    @Test
    void shouldFindRoom(){
        Room room = repository.findByRoomName("Toys R Us");

        assertEquals(3, room.getRoomId());
    }

    @Test
    void shouldNotFindRoom(){
        Room room = repository.findByRoomName("Made Up Room");

        assertNull(room);
    }
}