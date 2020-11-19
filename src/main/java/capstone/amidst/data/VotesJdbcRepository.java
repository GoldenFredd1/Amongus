package capstone.amidst.data;

import capstone.amidst.data.mappers.VoteMapper;
import capstone.amidst.models.Votes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VotesJdbcRepository implements VotesRepository {

    private final JdbcTemplate jdbcTemplate;


    public VotesJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Votes> findAll(){
        final String sql = "select voteId, gameRoomCode, votedForPlayerId, playerId " +
                "from Votes;";

        return jdbcTemplate.query(sql,new VoteMapper());
    }

    @Override
    public Votes findById(int voteId){
        final String sql = "select voteId, gameRoomCode, votedForPlayerId,playerId " +
                "from Votes " +
                "where voteId = ?";

        return jdbcTemplate.query(sql,new VoteMapper(), voteId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Votes add(Votes vote){
        final String sql = "insert into Votes (gameRoomCode,votedForPlayerId, playerId) " +
                "values (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vote.getGameRoomCode());
            ps.setInt(2,vote.getVotedForPlayerId());
            ps.setInt(3, vote.getPlayerId());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        vote.setVoteId(keyHolder.getKey().intValue());
        return vote;
    }

    @Override
    public boolean deleteAll() {
        System.out.println("You've made it to deleteAll() in the Votes repository.");
        jdbcTemplate.update("set SQL_SAFE_UPDATES = 0;");
        jdbcTemplate.update("delete from Votes;");
        return jdbcTemplate.update("set SQL_SAFE_UPDATES = 1;") > 0;
    }
}
