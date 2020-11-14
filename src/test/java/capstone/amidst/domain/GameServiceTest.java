package capstone.amidst.domain;

import capstone.amidst.data.GameRepository;
import capstone.amidst.data.PlayerRepository;
import capstone.amidst.models.Game;
import capstone.amidst.models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameServiceTest {

    @Autowired
    GameService service;

    @MockBean
    GameRepository repository;

    @Test
    void shouldAdd() {
        Game gameIn = newGame();
        Game gameOut = newGame();
        gameOut.setGameId(1);

        when(repository.add(gameIn)).thenReturn(gameOut);

        Result<Game> actual = service.add(gameIn);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(gameOut, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalid() {
        Game game = newGame();
        game.setPlayerId(-3);

        Result<Game> actual = service.add(game);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    Game newGame(){
        Game game = new Game();
        game.setGameRoomCode("QQZZYY");
        game.setPlayerId(3);
        game.setRoomId(5);
        return game;
    }
}