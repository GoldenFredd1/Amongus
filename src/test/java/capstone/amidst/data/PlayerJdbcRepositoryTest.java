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
        assertTrue(players.size() >= 3);
        // delete or add could run first
    }

    @Test
    void shouldAddPlayer() {
        Player player = newPlayer();
        Player actual = repository.add(player);
        assertNotNull(actual);
        assertEquals(5, actual.getPlayerId());
    }

    @Test
    void shouldAddComputerPlayer() {
        Player player = new Player();
        Player actual = repository.addComputerPlayer(player);
        assertNotNull(actual);
        assertEquals(6, actual.getPlayerId());
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

    @Test
    void shouldFindZerooTOOneImposters() {
        List<Player> imposters = repository.findByIsImposter(true);

        assertTrue(imposters.size()>=0);
    }

    @Test
    void shouldFindFourCrewMates() {
        List<Player> crewMates = repository.findByIsImposter(false);

        assertTrue(crewMates.size() >= 3);
    }

    @Test
    void shouldFindZeroDead() {
        List<Player> deadPlayers = repository.findByIsDead(true);

        assertEquals(0, deadPlayers.size());
    }

    @Test
    void shouldFindFourAlive() {
        List<Player> alivePlayers = repository.findByIsDead(false);

        assertTrue(alivePlayers.size() >= 3);
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(3));
        assertTrue(repository.findAll().size() >= 3);
    }

    @Test
    void shouldNotDeleteMissingId() {
        assertFalse(repository.deleteById(1000));
    }

    @Test
    void shouldEditExistingPlayer() {
        assertEquals("testPlayerAlpha", repository.findById(1).getPlayerName());

        Player player = newPlayer();
        player.setPlayerId(1);

        assertTrue(repository.update(player));
        assertEquals("Xx3litexX", repository.findById(1).getPlayerName());
    }

    @Test
    void shouldNotEditMissingPlayer() {
        Player player = newPlayer();
        player.setPlayerId(1000);
        assertFalse(repository.update(player));
    }

    @Test
    void shouldUpdateIsDead() {
        Player player = repository.findById(1);
        assertEquals(1, player.getPlayerId());

        repository.updateIsDead(player);
        assertTrue(player.isDead());
    }

    @Test
    void shouldUpdateIsImposter() {
        Player player = repository.findById(1);
        assertEquals(1, player.getPlayerId());

        repository.updateIsImposter(player);
        assertTrue(player.isImposter());
    }

    @Test
    void shouldUpdateReset() {
        Player player = repository.findById(1);
        assertEquals(1, player.getPlayerId());

        repository.updateIsDead(player);
        assertTrue(player.isDead());
        repository.updateIsImposter(player);
        assertTrue(player.isImposter());

        repository.updateResetPlayer(player);
        assertFalse(player.isDead());
        assertFalse(player.isImposter());
    }


    private Player newPlayer() {
        Player player = new Player();
        player.setPlayerName("Xx3litexX");
        player.setDead(false);
        player.setImposter(false);
        player.setAppUserId(1);
        return player;
    }
}