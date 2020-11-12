package capstone.amidst.domain;

import capstone.amidst.data.PlayerRepository;
import capstone.amidst.models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerServiceTest {

    @Autowired
    PlayerService service;

    @MockBean
    PlayerRepository repository;

    // No tests for findAll()?
    // Didn't seem like there were in past projects

    @Test
    void shouldAdd() {
        Player mockIn = newPlayer();
        Player mockOut = newPlayer();
        mockOut.setPlayerId(1);

        when(repository.add(mockIn)).thenReturn(mockOut);

        Result<Player> actual = service.add(mockIn);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalid() {
        Player player = newPlayer();
        player.setPlayerName("     ");

        Result<Player> actual = service.add(player);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldAddComputer() {
        Player mockIn = new Player();
        Player mockOut = new Player();
        mockOut.setPlayerId(1);

        when(repository.addComputerPlayer(mockIn)).thenReturn(mockOut);


        Result<Player> actual = service.addComputerPlayer(mockIn);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    Player newPlayer(){
        Player player = new Player();
        player.setPlayerName("Xx3litexX");
        player.setDead(false);
        player.setImpostor(false);
        player.setAppUserId(1);
        return player;
    }

}