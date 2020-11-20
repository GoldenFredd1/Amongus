package capstone.amidst.domain;

import capstone.amidst.data.*;
import capstone.amidst.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ComputerPlayers {

    final private PlayerRepository playerRepository;
    final private GameRepository gameRepository;
    final private RoomRepository roomRepository;
    final private PlayerAssignedTaskRepository patRepository;
    final private TaskRepository taskRepository;


    public ComputerPlayers(PlayerRepository playerRepository, GameRepository gameRepository,
                           RoomRepository roomRepository, PlayerAssignedTaskRepository patRepository,
                           TaskRepository taskRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.roomRepository = roomRepository;
        this.patRepository = patRepository;
        this.taskRepository = taskRepository;
    }

    public List<Integer> ComputerVote(String gameRoomCode) {
        List<Game> allPlayers = gameRepository.findByGameCode(gameRoomCode);
        List<Game> aliveComputers = ComputerPlaysLeft(allPlayers);

        List<Integer> listOfVotes = new ArrayList<>();

        for (Game aliveComputer : aliveComputers) {
            Player currentPlayer = playerRepository.findById(aliveComputer.getPlayerId());
            if (currentPlayer.isImposter()) {
                int imposterVote = randomVote(allPlayers);
                listOfVotes.add(allPlayers.get(imposterVote).getPlayerId());
            } else {
                //search for the dead bod
                int roomOfDeadBody = deadBodyLocation(allPlayers);
                List<Player> playersInDeadBodyRoom = playersInCurrentRoom(allPlayers, roomOfDeadBody, currentPlayer);
                if (playersInDeadBodyRoom.size() > 0) {
                    for(int i=0; i<allPlayers.size();i++){
                        if(allPlayers.get(i).getRoomId() == roomOfDeadBody){
                            listOfVotes.add(allPlayers.get(i).getPlayerId());
                            break;
                        }
                    }
                } else {
                    int crewRandom = randomVote(allPlayers);
                    listOfVotes.add(allPlayers.get(crewRandom).getPlayerId());
                }
            }
        }
        return listOfVotes;
    }

    private int randomVote(List<Game> allPlayers) {
        return (int) (Math.random() * allPlayers.size());
    }

    private int deadBodyLocation(List<Game> allPlayers) {
        for (Game allPlayer : allPlayers) {
            if (playerRepository.findById(allPlayer.getPlayerId()).isDead()) {
                return allPlayer.getRoomId();
            }
        }
        return 0;
    }


    public List<Game> ComputerPlayersMovement(Game game) {
        List<Game> allPlayers = gameRepository.findByGameCode(game.getGameRoomCode());
        List<Game> currentFinalComputers = ComputerPlaysLeft(allPlayers);

        for (Game currentFinalComputer : currentFinalComputers) {
            Player currentPlayer = playerRepository.findById(currentFinalComputer.getPlayerId());
            int currentRoom = currentFinalComputer.getRoomId();
            int newRoom = moveRooms(currentRoom);
            boolean completedTask = false;

            if (currentPlayer.isImposter()) {
                List<Player> players = playersInCurrentRoom(allPlayers, currentRoom, currentPlayer);
                if (players.size() == 1) {
                    playerRepository.updateIsDead(players.get(0));
                } else {
                    currentFinalComputer.setRoomId(newRoom);
                    gameRepository.update(currentFinalComputer);
                }
            }
            else {
                if (currentPlayer.isDead()) {
                    break;
                }

                List<PlayerAssignedTask> patList = patRepository.findAPlayersTasks(game.getGameRoomCode(), currentPlayer.getPlayerId());
                for (PlayerAssignedTask playerAssignedTask : patList) {
                    Task currentTask = taskRepository.findById(playerAssignedTask.getTaskId());
                    if (currentRoom == (currentTask.getRoomId()) && !playerAssignedTask.isComplete()) {
                        patRepository.updateTask(playerAssignedTask);
                        completedTask = true;
                        break;
                    }
                }
                if (!completedTask) {
                    currentFinalComputer.setRoomId(newRoom);
                    gameRepository.update(currentFinalComputer);
                }
            }
        }
        return currentFinalComputers;
    }

    private List<Game> ComputerPlaysLeft(List<Game> allPlayersInGame) {
        List<Game> updatedCompPlayerList = new ArrayList<>();
        for (Game computerPlayer : allPlayersInGame) {
            if ((playerRepository.findById(computerPlayer.getPlayerId()).getAppUserId() == 2) &&
                    !(playerRepository.findById(computerPlayer.getPlayerId()).isDead())) {
                updatedCompPlayerList.add(computerPlayer);
            }
        }
        return updatedCompPlayerList;
    }


    private List<Player> playersInCurrentRoom(List<Game> allPlayers, int currentRoom, Player currentPlayer) {
        List<Player> playersInRoom = new ArrayList<>();
        for (Game allPlayer : allPlayers) {
            if (allPlayer.getRoomId() == currentRoom && allPlayer.getPlayerId() != currentPlayer.getPlayerId()
                    && !playerRepository.findById(allPlayer.getPlayerId()).isDead()) {
                playersInRoom.add(playerRepository.findById(allPlayer.getPlayerId()));
            }
        }
        return playersInRoom;
    }


    private int moveRooms(int roomId) {
        List<Room> rooms = roomRepository.findAll();
        int changeRoom;
        do {
            changeRoom = (int) (Math.random() * rooms.size()) + 1;
        } while (roomId == changeRoom);

        return changeRoom;
    }


}
