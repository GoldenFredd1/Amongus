package capstone.amidst.data;

import capstone.amidst.data.mappers.GameMapper;
import capstone.amidst.models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
    public Game findByPlayerGameCode(int playerId, String gameCode) {
        final String sql = "select gameId, gameRoomCode, playerId, roomId "
                + "from Game "
                + "where gameRoomCode = ? and playerId = ?;";
        return jdbcTemplate.query(sql, new GameMapper(), gameCode, playerId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Game> findByGameCode(String gameCode) {
        final String sql = "select gameId, gameRoomCode, playerId, roomId "
                + "from Game "
                + "where gameRoomCode = ?;";
        return jdbcTemplate.query(sql, new GameMapper(), gameCode);
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

    @Override
    public boolean deleteAll() {
        System.out.println("You've made it to deleteAllGames() in the Game repository.");
        jdbcTemplate.update("set SQL_SAFE_UPDATES = 0;");
        jdbcTemplate.update("delete from Game;");
        return jdbcTemplate.update("set SQL_SAFE_UPDATES = 1;") > 0;
    }

    @Override
    public boolean checkEndGame(String gameRoomCode) {
        int numPeopleAlive = getNumPeopleAlive(gameRoomCode);
        final int numTasksLeft = getTasksRemaining(gameRoomCode);
        final boolean imposterAlive = isImposterAlive(gameRoomCode);

        if (numPeopleAlive == 0 || numPeopleAlive == 1 || numTasksLeft == 0 || !imposterAlive) {
            System.out.println("True");
            return true;
        } else {
            System.out.println("False");
            return false;
        }
    }

    @Override
    public boolean didImposterWin(String gameRoomCode) {
        int numPeopleAlive = getNumPeopleAlive(gameRoomCode);
        final boolean imposterAlive = isImposterAlive(gameRoomCode);
        if ((numPeopleAlive == 0 || numPeopleAlive == 1) && imposterAlive) {
            System.out.println("Imposter wins! (True)");
            return true;
        } else {
            System.out.println("Crew mates win! (False)");
            return false;
        }
    }

    //Private Methods
    private int getNumPeopleAlive(String gameRoomCode) {
        final String sql = "select count(*) " +
                "from Game g " +
                "join Player p on p.playerId=g.playerId " +
                "where g.gameRoomCode = ? " +
                "and p.isDead = 0 " +
                "and p.isImposter = false;";
        return jdbcTemplate.queryForObject(sql, Integer.class, gameRoomCode);
    }

    private int getTasksRemaining(String gameRoomCode) {
        final String sql = "select count(*) " +
                "from Game g " +
                "join Player p on p.playerId=g.playerId " +
                "join Player_Assigned_Task pat on pat.playerId=p.playerId " +
                "where g.gameRoomCode = ? " +
                "and pat.isComplete=0;";
        return jdbcTemplate.queryForObject(sql, Integer.class, gameRoomCode);
    }

    private boolean isImposterAlive(String gameRoomCode) {
        final String sql = "select count(*) " +
                "from Game g " +
                "join Player p on p.playerId=g.playerId " +
                "where g.gameRoomCode = ? " +
                "and p.isImposter = true " +
                "and p.isDead = true;";
        return jdbcTemplate.queryForObject(sql, Integer.class, gameRoomCode) == 0;
    }

}
