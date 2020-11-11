package capstone.amidst.data;

import capstone.amidst.models.Player;

import java.util.List;

public interface PlayerRepository {

    List<Player> findAll();
    Player findById(int playerId);
    Player findByIsImposter(boolean isImposter);
    List<Player> findByIsDead(boolean isDead);

    Player add(Player player);

    boolean update(Player player);

    boolean deleteById(int playerId);
}
