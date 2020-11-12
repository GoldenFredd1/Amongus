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
        assertTrue(players.size() >= 4);
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



    private Player newPlayer(){
        Player player = new Player();
        player.setPlayerName("Xx3litexX");
        player.setDead(false);
        player.setImposter(false);
        player.setAppUserId(1);
        return player;
    }

}