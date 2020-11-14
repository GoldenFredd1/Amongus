package capstone.amidst.data.mappers;

import capstone.amidst.models.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet resultSet, int i) throws SQLException {
        Game game = new Game();
        game.setGameId(resultSet.getInt("gameId"));
        game.setGameRoomCode(resultSet.getString("gameRoomCode"));
        game.setPlayerId(resultSet.getInt("playerId"));
        game.setRoomId(resultSet.getInt("roomId"));
        return game;
    }
}
