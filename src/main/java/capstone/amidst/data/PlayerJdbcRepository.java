package capstone.amidst.data;

import capstone.amidst.data.mappers.PlayerMapper;
import capstone.amidst.models.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return null;
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
