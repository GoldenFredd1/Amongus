package capstone.amidst.domain;

import capstone.amidst.data.*;
import capstone.amidst.models.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


class ComputerPlayersTest {

    ComputerPlayers service= new ComputerPlayers(new PlayerTestDouble(),
            new GamePlayersTestDouble(), new RoomTestDouble(), new PATTestDouble(),
            new TaskTestDouble());

    @Test
    void testingVote(){
        Game testList = new GamePlayersTestDouble().findById(1);
        List<Integer> voteList = service.ComputerVote(String.valueOf(testList));

        voteList.forEach(System.out::println);
    }



    @Test
    void shouldReturnAListOfComputers() {
        Game testList = new GamePlayersTestDouble().findById(1);
        List<Game> finalList=service.ComputerPlayersMovement(testList);
        assertEquals(finalList.size(), 3);
        for (Game game : finalList) {
            System.out.println("player ID :" +game.getPlayerId()+" RoomId "+
                    game.getRoomId());
        }
        service.ComputerPlayersMovement(testList);
        for (Game game : finalList) {
            System.out.println("player ID :" +game.getPlayerId()+" RoomId "+
                    game.getRoomId());
        }
        service.ComputerPlayersMovement(testList);
        for (Game game : finalList) {
            System.out.println("player ID :" +game.getPlayerId()+" RoomId "+
                    game.getRoomId());
        }
        service.ComputerPlayersMovement(testList);
        for (Game game : finalList) {
            System.out.println("player ID :" +game.getPlayerId()+" RoomId "+
                    game.getRoomId());
        }
        service.ComputerPlayersMovement(testList);
        for (Game game : finalList) {
            System.out.println("player ID :" +game.getPlayerId()+" RoomId "+
                    game.getRoomId());
        }
        service.ComputerPlayersMovement(testList);
        for (Game game : finalList) {
            System.out.println("player ID :" +game.getPlayerId()+" RoomId "+
                    game.getRoomId());
        }
        service.ComputerPlayersMovement(testList);
        for (Game game : finalList) {
            System.out.println("player ID :" +game.getPlayerId()+" RoomId "+
                    game.getRoomId());
        }
    }






}