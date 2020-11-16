package capstone.amidst.data;

import capstone.amidst.data.mappers.GameMapper;
import capstone.amidst.models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class GameJdbcRepository implements GameRepository {

    private final JdbcTemplate jdbcTemplate;

    public GameJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> findAll() {
        final String sql = "select gameId, gameRoomCode, playerId, roomId from Game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game findById(int gameId) {
        final String sql = "select gameId, gameRoomCode, playerId, roomId "
                + "from Game "
                + "where gameId = ?;";
        return jdbcTemplate.query(sql, new GameMapper(), gameId).stream().findFirst().orElse(null);
    }

    @Override
    public Game add(Game game) {
        final String sql = "insert into Game (gameRoomCode, playerId, roomId) "
                + " values (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, game.getGameRoomCode());
            ps.setInt(2, game.getPlayerId());
            ps.setInt(3, game.getRoomId());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public boolean update(Game game) {
        final String sql = "update Game set "
                + "gameRoomCode = ?, "
                + "playerId = ?, "
                + "roomId = ? "
                + "where gameId = ?;";

        return jdbcTemplate.update(sql,
                game.getGameRoomCode(),
                game.getPlayerId(),
                game.getRoomId(),
                game.getGameId()) > 0;
    }

    @Override
    public boolean deleteById(int gameId) {
        return jdbcTemplate.update("delete from Game where gameId = ?;", gameId) > 0;
    }


}
