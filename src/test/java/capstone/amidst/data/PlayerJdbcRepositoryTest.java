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
        assertTrue(players.size() == 3 || players.size() == 4 || players.size() == 5); // delete could run first
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
//        List<Player> players = repository.findAll();
//       for(int i=0;i<players.size();i++){
//           System.out.println(players.get(i).getPlayerName());
//       }
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
    void shouldFindZeroImposters() {
        List<Player> imposters = repository.findByIsImposter(true);

        assertEquals(0, imposters.size());
    }

    @Test
    void shouldFindFourCrewMates() {
        List<Player> crewMates = repository.findByIsImposter(false);

        assertTrue(crewMates.size() == 3 || crewMates.size() == 4);
    }

    @Test
    void shouldFindZeroDead() {
        List<Player> deadPlayers = repository.findByIsDead(true);

        assertEquals(0, deadPlayers.size());
    }

    @Test
    void shouldFindFourAlive() {
        List<Player> alivePlayers = repository.findByIsDead(false);

        assertTrue(alivePlayers.size() == 3 || alivePlayers.size() == 4 || alivePlayers.size() == 5);
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(3));
        assertTrue(repository.findAll().size() == 3 || repository.findAll().size() == 4);
    }

    @Test
    void shouldNotDeleteMissingId() {
        assertFalse(repository.deleteById(1000));
    }


    private Player newPlayer(){
        Player player = new Player();
        player.setPlayerName("Xx3litexX");
        player.setDead(false);
        player.setImposter(false);
        player.setAppUserId(1);
        return player;
    }
}