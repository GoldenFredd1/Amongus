package capstone.amidst.controllers;

import capstone.amidst.domain.PlayerService;
import capstone.amidst.models.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    // Mappings
    @GetMapping("/players")
    public List<Player> displayAll() {
        return service.findAll();
    }

}
