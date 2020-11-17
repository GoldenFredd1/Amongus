package capstone.amidst.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class Votes {
    @PositiveOrZero(message = "Room ID must be 0 or higher.")
    private int voteId;
    @NotBlank(message = "Game Room Code cannot be empty." )
    private String gameRoomCode;
    @PositiveOrZero(message = "Room ID must be 0 or higher.")
    private int votedForPlayerId;
    @PositiveOrZero(message = "Room ID must be 0 or higher.")
    private int playerId;


    public Votes() {
    }

    public Votes(int voteId, String gameRoomCode,int votedForPlayerId,int playerId) {
        this.voteId = voteId;
        this.gameRoomCode = gameRoomCode;
        this.votedForPlayerId =votedForPlayerId;
        this.playerId = playerId;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getGameRoomCode() {
        return gameRoomCode;
    }

    public void setGameRoomCode(String gameRoomCode) {
        this.gameRoomCode = gameRoomCode;
    }

    public int getVotedForPlayerId() {
        return votedForPlayerId;
    }

    public void setVotedForPlayerId(int votedForPlayerId) {
        this.votedForPlayerId = votedForPlayerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
