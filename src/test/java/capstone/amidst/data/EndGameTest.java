package capstone.amidst.data;

import capstone.amidst.models.Game;
import capstone.amidst.models.Player;
import capstone.amidst.models.PlayerAssignedTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EndGameTest {

    EndGameTestDouble testDouble;
    ArrayList<Game> games;
    ArrayList<Player> players;
    ArrayList<PlayerAssignedTask> tasks;

    @BeforeEach
    void setUp() {
        testDouble = new EndGameTestDouble();
        games = testDouble.getGames();
        players = testDouble.getPlayers();
        tasks = testDouble.getTasks();
    }

    @Test
    public void shouldFindAllGames() {
        assertNotNull(games);
        assertEquals(4,games.size());
    }

    @Test
    public void shouldFindAllPlayers() {
        assertNotNull(players);
        assertEquals(4,players.size());
    }

    @Test
    public void shouldFindAllTasks() {
        assertNotNull(tasks);
        assertEquals(8,tasks.size());
    }

    @Test
    public void shouldNotEndGameWithOriginalData() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
    }

    @Test
    public void shouldEndGameWithDeadImposter() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (Player player: players) {
            if (player.isImposter()) {
                player.setDead(true);
            }
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
    }

    @Test
    public void shouldEndGameWithAllTasksDone() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (PlayerAssignedTask task: tasks) {
            task.setComplete(true);
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
    }

    @Test
    public void shouldEndGameWithOnePlayerLeft() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (Player player: players) {
            if (player.getPlayerId() != 3) {
                player.setDead(true);
            }
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
    }

    @Test
    public void shouldEndGameWithOnlyImposterLeft() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (Player player: players) {
            if (!player.isImposter()) {
                player.setDead(true);
            }
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
    }

    @Test
    public void shouldEndGameWithNoPlayersLeft() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (Player player: players) {
            player.setDead(true);
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
    }

    @Test
    public void imposterShouldWinWithOneCrewMemberLeft() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (Player player: players) {
            if (player.getPlayerId() != 3 && !player.isImposter()) {
                player.setDead(true);
            }
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
        assertTrue(testDouble.didImposterWin("OKOKOK"));
    }

    @Test
    public void imposterShouldWinWithZeroCrewMemberLeft() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (Player player: players) {
            if (!player.isImposter()) {
                player.setDead(true);
            }
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
        assertTrue(testDouble.didImposterWin("OKOKOK"));
    }

    @Test
    public void crewMatesShouldWinWithAllTasksDone() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (PlayerAssignedTask task: tasks) {
            task.setComplete(true);
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
        assertFalse(testDouble.didImposterWin("OKOKOK"));
    }

    @Test
    public void crewMatesShouldWinWithDeadImposter() {
        assertFalse(testDouble.checkEndGame("OKOKOK"));
        for (Player player: players) {
            if (player.isImposter()) {
                player.setDead(true);
            }
        }
        assertTrue(testDouble.checkEndGame("OKOKOK"));
        assertFalse(testDouble.didImposterWin("OKOKOK"));
    }

}
