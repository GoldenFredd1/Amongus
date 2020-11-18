package capstone.amidst.data;

import capstone.amidst.models.Votes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VotesRepositoryTest {

    @Autowired
    VotesJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<Votes> votes= repository.findAll();
        assertNotNull(votes);
        assertEquals(2,votes.size());
    }

    @Test
    void shouldFindByValidId(){
        Votes vote = repository.findById(2);
        assertEquals(2, vote.getVoteId());
        assertEquals("HELPME",vote.getGameRoomCode());
    }
    
    @Test
    void shouldNotFndInvalidId(){
        Votes vote = repository.findById(10000);
        assertNull(vote);
    }

    @Test
    void shouldAdd(){
        Votes vote = new Votes();
        vote.setGameRoomCode("HELPME");
        vote.setVotedForPlayerId(2);
        vote.setPlayerId(1);
    }
}