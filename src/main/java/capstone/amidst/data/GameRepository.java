package capstone.amidst.data;

import capstone.amidst.models.Game;

import java.util.List;

public interface GameRepository {
    List<Game> findAll();

    Game findById(int gameId);

    Game add(Game game);

    boolean update(Game game);

    boolean deleteById(int gameId);
}
