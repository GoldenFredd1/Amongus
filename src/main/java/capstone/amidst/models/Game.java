package capstone.amidst.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class Game {
    // Private Fields
    @PositiveOrZero(message = "Game ID must be 0 or higher.")
    private int gameId;

    @NotBlank(message = "Game Room Code is required.")
    private String gameRoomCode;

    @PositiveOrZero(message = "Player ID must be 0 or higher.")
    private int playerId;

    @PositiveOrZero(message = "Room ID must be 0 or higher.")
    private int roomId;

    // Constructors
    public Game(int gameId, String gameRoomCode, int playerId, int roomId) {
        this.gameId = gameId;
        this.gameRoomCode = gameRoomCode;
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public Game() {
    }

    // Getters and Setters
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameRoomCode() {
        return gameRoomCode;
    }

    public void setGameRoomCode(String gameRoomCode) {
        this.gameRoomCode = gameRoomCode;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
