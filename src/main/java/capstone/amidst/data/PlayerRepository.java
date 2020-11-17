package capstone.amidst.data;

import capstone.amidst.models.Player;

import java.util.List;

public interface PlayerRepository {

    List<Player> findAll();
    Player findById(int playerId);

    Player findByAppUserId(int appUserId);

    List<Player> findByIsImposter(boolean Imposter);
    List<Player> findByIsDead(boolean Dead);

    Player add(Player player);

    Player addComputerPlayer(Player player);

    boolean update(Player player);

    Boolean updateIsDead(Player player);

    Boolean updateIsImposter(Player player);

    Boolean updateResetPlayer(Player player);

    boolean deleteById(int playerId);
}
