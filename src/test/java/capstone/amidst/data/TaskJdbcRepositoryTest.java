package capstone.amidst.data;

import capstone.amidst.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TaskJdbcRepositoryTest {

    @Autowired
    TaskJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllTask(){
        List<Task> task = repository.findAll();

        assertNotNull(task);
        assertEquals(12, task.size());
    }

    @Test
    void shouldFindByValidTaskId(){
        Task task = repository.findById(2);

        assertEquals(2,task.getTaskId());
        assertEquals("Collect moldy sandwich", task.getTaskName());
        assertEquals(1,task.getRoomId());
    }

    @Test
    void shouldNotFindWithCrazyId() {
        Task task = repository.findById(88888);
        assertNull(task);
    }

    @Test
    void shouldFindByRoomId(){
        List<Task> task = repository.findByRoomId(3);

        assertNotNull(task);
        assertEquals(2, task.size());
    }

    @Test
    void shouldNotFindByCrazyRoomId(){
        List<Task> task = repository.findByRoomId(33333333);
        assertEquals(0, task.size());
    }

}