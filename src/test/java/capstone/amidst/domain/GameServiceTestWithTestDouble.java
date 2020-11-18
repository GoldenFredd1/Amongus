package capstone.amidst.domain;

import capstone.amidst.data.GamePlayersTestDouble;
import capstone.amidst.data.PlayerTestDouble;
import capstone.amidst.models.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTestWithTestDouble {

    GameService service = new GameService(new GamePlayersTestDouble(), new PlayerTestDouble());


    @Test
    void testingDeadPlayerInRoom(){
        Game game = new Game(5,"HELPME",5,3);
        assertTrue( service.deadBodyInRoomCheck(game));
    }

    @Test
    void testingDeadPlayerInRoomFail(){
        Game game = new Game(6,"HELPME",5,1);
        boolean isThereADeadBodyInHere = service.deadBodyInRoomCheck(game);
        assertFalse(isThereADeadBodyInHere);
    }

}