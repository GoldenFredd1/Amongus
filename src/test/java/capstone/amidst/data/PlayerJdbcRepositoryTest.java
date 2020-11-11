package capstone.amidst.data;

import capstone.amidst.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerJdbcRepositoryTest {

    @Autowired
    PlayerJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Player> players = repository.findAll();

        assertNotNull(players);
        assertTrue(players.size() == 4);
    }

    @Test
    void shouldFindById() {
        Player player = repository.findById(1);

        assertEquals(1, player.getPlayerId());
        assertEquals("testPlayerAlpha", player.getPlayerName());
        assertFalse(player.isDead());
        assertFalse(player.isImposter());
        assertEquals(1, player.getAppUserId());
    }

    @Test
    void shouldNotFindByMissingId() {
        Player player = repository.findById(1000);

        assertNull(player);
    }

}