package capstone.amidst.data;

import capstone.amidst.models.Game;
import capstone.amidst.models.Player;
import capstone.amidst.models.PlayerAssignedTask;
import capstone.amidst.models.Task;

import java.util.ArrayList;
import java.util.List;

public class EndGameTestDouble implements GameRepository {

    private final ArrayList<Game> games = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<PlayerAssignedTask> tasks = new ArrayList<>();

    public EndGameTestDouble(){
        games.add(new Game(1,"OKOKOK",1,3));
        games.add(new Game(2,"OKOKOK",2,3));
        games.add(new Game(3,"OKOKOK",3,3));
        games.add(new Game(4,"OKOKOK",4,3));

        players.add(new Player(1,"Joey",false,false,1));
        players.add(new Player(2,"Linda",false,true,2));
        players.add(new Player(3,"Paul",false,false,2));
        players.add(new Player(4,"Bobby",false,false,2));

        tasks.add(new PlayerAssignedTask(1, 1, false));
        tasks.add(new PlayerAssignedTask(8, 1, false));
        tasks.add(new PlayerAssignedTask(4, 2, true));
        tasks.add(new PlayerAssignedTask(10, 2, true));
        tasks.add(new PlayerAssignedTask(2, 3, false));
        tasks.add(new PlayerAssignedTask(8, 3, false));
        tasks.add(new PlayerAssignedTask(3, 4, false));
        tasks.add(new PlayerAssignedTask(7, 4, false));
    }

    //Getters
    public ArrayList<Game> getGames() {
        return games;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public ArrayList<PlayerAssignedTask> getTasks() {
        return tasks;
    }

    @Override
    public List<Game> findAll() {
        return null;
    }

    @Override
    public Game findById(int gameId) {
        return null;
    }

    @Override
    public List<Game> findByGameCode(String gameCode) {
        return null;
    }

    @Override
    public Game add(Game game) {
        return null;
    }

    @Override
    public boolean update(Game game) {
        return false;
    }

    @Override
    public boolean deleteById(int gameId) {
        return false;
    }

    @Override
    public boolean checkEndGame(String gameRoomCode) {
        int numPeopleAlive = getNumPeopleAlive();
        final int numTasksLeft = getTasksRemaining();
        final boolean imposterAlive = isImposterAlive();

        if (numPeopleAlive == 0 || numPeopleAlive == 1 || numTasksLeft == 0 || !imposterAlive) {
            return true;
        } else {
            return false;
        }
    }

    //private methods
    int getNumPeopleAlive() {
        int count = 0;
        for(Player player: players) {
            if (!player.isDead()) {
                count++;
            }
        }
        return count;
    }

    int getTasksRemaining() {
        int count = 0;
        for(PlayerAssignedTask task: tasks) {
            if (!task.isComplete()) {
                count++;
            }
        }
        return count;
    }

    boolean isImposterAlive() {
        for(Player player: players) {
            if (player.isImposter() && player.isDead()) {
                return false;
            }
        }
        return true;
    }
}
