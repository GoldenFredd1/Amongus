package capstone.amidst.data;

import capstone.amidst.models.Game;

import java.util.List;

public interface GameRepository {
    List<Game> findAll();

    Game findById(int gameId);

    Game findByPlayerGameCode(int playerId, String gameCode);

    List<Game> findByGameCode(String gameCode);

    Game add(Game game);

    boolean update(Game game);

    boolean deleteById(int gameId);

    boolean deleteAll();

    boolean checkEndGame(String gameRoomCode);

    boolean didImposterWin(String gameRoomCode);
}
