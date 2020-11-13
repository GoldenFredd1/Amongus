package capstone.amidst.data;

import capstone.amidst.models.PlayerAssignedTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerAssignedTaskJdbcRepositoryTest {

    @Autowired
    PlayerAssignedTaskRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllPerGame(){
        List<PlayerAssignedTask> PAT = repository.findAllByGame("HELPME");
        assertNotNull(PAT);
        assertEquals(8,PAT.size());
    }

    @Test
    void shouldFindNonePerGame(){
        List<PlayerAssignedTask> PAT = repository.findAllByGame("NEVERU");
        assertEquals(0,PAT.size());
    }

    @Test
    void shouldFindTwoForPlayer(){
        List<PlayerAssignedTask> PAT = repository.findAPlayersTasks("HELPME", 1);
        assertEquals(2,PAT.size());
    }

    @Test
    void shouldFindNoneForMadeUpPlayer(){
        List<PlayerAssignedTask> PAT = repository.findAPlayersTasks("HELPME", 111111);
        assertEquals(0,PAT.size());
    }


    @Test
    void shouldFindSpecificTask(){
        PlayerAssignedTask PAT = repository.specificTask("HELPME", 1);
        assertEquals(1,PAT.getTaskId());
        assertEquals(1,PAT.getPlayerId());
        assertEquals(false,PAT.isComplete());
    }

    @Test
    void shouldNotFindSpecificTask(){
        PlayerAssignedTask PAT = repository.specificTask("NEVERU", 88);
        assertNull(PAT);
    }

    @Test
    void shouldUpdate(){
        PlayerAssignedTask PAT = repository.specificTask("HELPME",2);
        repository.updateTask(PAT);
        PAT = repository.specificTask("HELPME",2);
        assertEquals(true, PAT.isComplete());
    }

}