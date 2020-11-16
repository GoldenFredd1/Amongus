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


    public ComputerPlayers(PlayerRepository playerRepository, GameRepository gameRepository, RoomRepository roomRepository, PlayerAssignedTaskRepository patRepositry, TaskRepository taskRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.roomRepository = roomRepository;
        this.patRepository = patRepositry;
        this.taskRepository = taskRepository;
    }

//TODO add a voting method.

//    public void Vote(Game game) {
//        List<Game> allPlayers = gameRepository.findByGameCode(game.getGameRoomCode());
//        List<Game> aliveComputers = ComputerPlaysLeft(allPlayers);
//
//        for (Game aliveComputer : aliveComputers) {
//            Player currentPlayer = playerRepository.findById(aliveComputer.getPlayerId());
//            if (currentPlayer.isImposter()) {
//                //ToDo Vote random
//            } else {
//                //search for the dead bod
//                int roomOfDeadBody = deadBodyLocation(allPlayers);
//                List<Player> playersInDeadBodyRoom = playersInCurrentRoom(allPlayers, roomOfDeadBody, currentPlayer);
//                if (playersInDeadBodyRoom.size() > 0) {
//                    //vote for that person...
//                    //but what if there is more than one?? Skip? Random?
//                } else {
//                    //ToDo Vote random
//                }
//            }
//        }
//    }

    private int deadBodyLocation(List<Game> allPlayers) {
        for (Game allPlayer : allPlayers) {
            if (playerRepository.findById(allPlayer.getPlayerId()).isDead()) {
                //this is the dead person...
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
                System.out.println("Impostor turn. " + currentPlayer.getPlayerName());
                List<Player> players = playersInCurrentRoom(allPlayers, currentRoom, currentPlayer);
                if (players.size() == 1) {
                    playerRepository.updateIsDead(players.get(0));
                    System.out.println("\n\nImposter KILLED Player " + players.get(0).getPlayerName() + " " + players.get(0).isDead());
                } else {
                    System.out.println("Start Room: " + currentRoom);
                    System.out.println("New Room: " + newRoom);
                    currentFinalComputer.setRoomId(newRoom);
                    gameRepository.update(currentFinalComputer);
                }
            }
            //so if the computer is NOT the imposter they need to report dead bodies, do tasks, move
            else {
                //check to see if they were killed during this round
                if (currentPlayer.isDead()) {
                    break;
                }
                System.out.println("Crew Turn " + currentPlayer.getPlayerName());
                //REPORT BODIES
//                for(Game player : allPlayers){
//                    Player currentInList = playerRepository.findById(player.getPlayerId());
//                    if(currentInList.isDead() && currentRoom == player.getRoomId()){
//                        //TODO report body trigger goes here...
//                    }
//                }

                //DO TASK
                //list of tasks from PlayerAssignedTask table
                List<PlayerAssignedTask> patList = patRepository.findAPlayersTasks(game.getGameRoomCode(), currentPlayer.getPlayerId());
                //loop through the tasks to see if there is a task that needs to be complete in the current room.
                for (PlayerAssignedTask playerAssignedTask : patList) {
                    Task currentTask = taskRepository.findById(playerAssignedTask.getTaskId());
                    System.out.println(currentTask.getTaskName() + " is done: " + playerAssignedTask.isComplete());
                    if (currentRoom == (currentTask.getRoomId()) && !playerAssignedTask.isComplete()) {
                        System.out.println("You hit a task!!");
                        patRepository.updateTask(playerAssignedTask);
                        //set completed task to true so they don't move.
                        completedTask = true;
                        //we will update one, but if both tasks are in the same room we do not want to do both at once.
                        break;
                    }
                }
                //MOVE ROOM
                //move room if they didn't complete a task.
                if (!completedTask) {
                    System.out.println("Start Room: " + currentRoom);
                    System.out.println("New Room: " + newRoom);
                    currentFinalComputer.setRoomId(newRoom);
                    gameRepository.update(currentFinalComputer);
                }
            }
        }
        return currentFinalComputers;
    }

    private List<Game> ComputerPlaysLeft(List<Game> allPlayersInGame) {
        //to get only a list of ALIVE computer players. Checking the appUserId, and isDead
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
