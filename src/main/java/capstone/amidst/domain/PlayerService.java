package capstone.amidst.domain;

import capstone.amidst.data.PlayerRepository;
import capstone.amidst.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    // Methods
    public List<Player> findAll() {
        return repository.findAll();
    }

    public Player findById(int playerId) {
        return repository.findById(playerId);
    }

    public List<Player> findByIsImposter(boolean isImposter) {
        return repository.findByIsImposter(isImposter);
    }

    public List<Player> findByIsDead(boolean isDead) {
        return repository.findByIsDead(isDead);
    }
}
