package capstone.amidst.data.mappers;

import capstone.amidst.models.Votes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteMapper implements RowMapper<Votes> {
    @Override
    public Votes mapRow(ResultSet resultSet, int i) throws SQLException {
        Votes vote = new Votes();
        vote.setVoteId(resultSet.getInt("voteId"));
        vote.setGameRoomCode(resultSet.getString("gameRoomCode"));
        vote.setVotedForPlayerId(resultSet.getInt("votedForPlayerId"));
        vote.setPlayerId(resultSet.getInt("playerId"));
        return vote;
    }
}
