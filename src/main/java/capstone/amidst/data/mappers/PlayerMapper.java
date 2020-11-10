package capstone.amidst.data.mappers;

import capstone.amidst.models.Player;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PlayerMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet resultSet, int i) throws SQLException {
        Player player = new Player();
        player.setPlayerId(resultSet.getInt("playerId"));
        player.setPlayerName(resultSet.getString("playerName"));
        player.setDead(resultSet.getBoolean("isDead"));
        player.setImpostor(resultSet.getBoolean("isImpostor"));
        player.setAppUserId(resultSet.getInt("app_user_id"));
        return player;
    }
}
