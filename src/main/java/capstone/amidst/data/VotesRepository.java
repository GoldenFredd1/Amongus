package capstone.amidst.data;

import capstone.amidst.models.Votes;

import java.util.List;

public interface VotesRepository {
    List<Votes> findAll();

    Votes findById(int voteId);

    Votes add(Votes vote);
}
