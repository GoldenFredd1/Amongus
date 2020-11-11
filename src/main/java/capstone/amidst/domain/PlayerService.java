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

}
