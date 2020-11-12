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
        final String sql = "select playerId, playerName, isDead, isImposter, app_user_id" +
                " from Player;";
        return jdbcTemplate.query(sql, new PlayerMapper());
    }

    @Override
    public Player findById(int playerId) {
        return null;
    }

    @Override
    public Player findByIsImposter(boolean isImposter) {
        return null;
    }

    @Override
    public List<Player> findByIsDead(boolean isDead) {
        return null;
    }

    @Override
    public Player add(Player player) {
        final String sql = "insert into Player(playerName, isDead, isImposter, app_user_id) " +
                "values (?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getPlayerName());
            ps.setBoolean(2, player.isDead());
            ps.setBoolean(3, player.isImposter());
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
        final String sql = "insert into Player(playerName, isDead, isImposter, app_user_id) " +
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
        return false;
    }
}
