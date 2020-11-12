package capstone.amidst.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class Player {
    // Private Fields
    @PositiveOrZero(message = "Player ID must be 0 or higher.")
    private int playerId;

    @NotBlank(message = "Player name is required.")
    private String playerName;

    @NotNull(message = "Player must be either dead or alive.")
    private boolean isDead;

    @NotNull(message = "Player must be either an imposter or a crewmate.")
    private boolean isImposter;

    @PositiveOrZero(message = "User ID must be 0 or higher.")
    private int appUserId;

    // Constructors
    public Player(int playerId, String playerName, boolean isDead, boolean isImposter, int appUserId) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.isDead = isDead;
        this.isImposter = isImposter;
        this.appUserId = appUserId;
    }
    public Player() {
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public boolean isDead() {
        return isDead;
    }
    public void setDead(boolean dead) {
        isDead = dead;
    }
    public boolean isImposter() {
        return isImposter;
    }
    public void setImposter(boolean imposter) {
        isImposter = imposter;
    }
    public int getAppUserId() {
        return appUserId;
    }
    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerId == player.playerId &&
                isDead == player.isDead &&
                isImposter == player.isImposter &&
                appUserId == player.appUserId &&
                playerName.equals(player.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, playerName, isDead, isImposter, appUserId);
    }
}
