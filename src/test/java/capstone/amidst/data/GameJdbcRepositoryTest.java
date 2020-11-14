package capstone.amidst.data;

import capstone.amidst.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameJdbcRepositoryTest {

    @Autowired
    GameJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Game> games = repository.findAll();

        assertNotNull(games);
        assertTrue(games.size() >= 3);
    }

    @Test
    void shouldFindExistingGame() {
        Game game = repository.findById(2);

        assertNotNull(game);
        assertEquals(2, game.getGameId());
        assertEquals("HELPME", game.getGameRoomCode());
        assertEquals(2, game.getPlayerId());
        assertEquals(2, game.getRoomId());
    }

    @Test
    void shouldNotFindMissingGame() {
        Game game = repository.findById(100);

        assertNull(game);
    }

    @Test
    void shouldAdd() {
        Game game = makeGame();
        Game result = repository.add(game);
        assertNotNull(result);
        assertTrue(result.getGameId() >= 5);
    }

    @Test
    void shouldUpdateExistingGame() {
        Game game = makeGame();
        game.setGameId(3);
        assertTrue(repository.update(game));
    }

    @Test
    void shouldNotUpdateMissingGame() {
        Game game = makeGame();
        game.setGameId(100);
        assertFalse(repository.update(game));
    }

    @Test
    void shouldDeleteExistingGame() {
        assertTrue(repository.deleteById(4));
    }

    @Test
    void shouldNotDeleteMissingGame() {
        assertFalse(repository.deleteById(100));
    }



    private Game makeGame() {
        Game game = new Game();
        game.setGameRoomCode("AHHNOO");
        game.setPlayerId(1);
        game.setRoomId(1);
        return game;
    }

}