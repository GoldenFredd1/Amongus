package capstone.amidst.data;

import capstone.amidst.data.mappers.PlayerMapper;
import capstone.amidst.models.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlayerJdbcRepository implements PlayerRepository {
    // Fields
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public PlayerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Methods
    @Override
    public List<Player> findAll() {
        final String sql = "select playerId, playerName, isDead, isImpostor, app_user_id" +
                " from Player;";
        return jdbcTemplate.query(sql, new PlayerMapper());
    }

    @Override
    public Player findById(int playerId) {
        final String sql = "select playerId, playerName, isDead, isImpostor, app_user_id" +
                " from Player" +
                " where playerId = ?;";
        return jdbcTemplate.query(sql, new PlayerMapper(), playerId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Player> findByIsImpostor(boolean isImpostor) {
        final String sql = "select playerId, playerName, isDead, isImpostor, app_user_id" +
                " from Player" +
                " where isImpostor = ?;";
        return jdbcTemplate.query(sql, new PlayerMapper(), isImpostor);
    }

    @Override
    public List<Player> findByIsDead(boolean isDead) {
        final String sql = "select playerId, playerName, isDead, isImpostor, app_user_id" +
                " from Player" +
                " where isDead = ?;";
        return jdbcTemplate.query(sql, new PlayerMapper(), isDead);
    }

    @Override
    public Player add(Player player) {
        final String sql = "insert into Player(playerName, isDead, isImpostor, app_user_id) " +
                "values (?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getPlayerName());
            ps.setBoolean(2, player.isDead());
            ps.setBoolean(3, player.isImpostor());
            ps.setInt(4, player.getAppUserId());
            return ps;
        },keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        player.setPlayerId(keyHolder.getKey().intValue());
        return player;
    }

    @Override
    public Player addComputerPlayer(Player player) {
        final String sql = "insert into Player(playerName, isDead, isImpostor, app_user_id) " +
                "values ('Computer',false,false,2);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return ps;
        },keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        int id = keyHolder.getKey().intValue();
        player.setPlayerId(id);
        jdbcTemplate.update("Update Player set playerName = 'Computer"
                +id+"' where playerId = "
                +id);
        return player;
    }

    @Override
    public boolean update(Player player) {
        return false;
    }

    @Override
    public boolean deleteById(int playerId) {
        jdbcTemplate.update("delete from Game where playerId = ?;", playerId);
        jdbcTemplate.update("delete from Player_Assigned_Task where playerId = ?;", playerId);
        return jdbcTemplate.update("delete from Player where playerId = ?;", playerId) > 0;
    }
}
