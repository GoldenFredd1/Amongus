package capstone.amidst.controllers;

import capstone.amidst.domain.RoomService;
import capstone.amidst.models.Room;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    // Mappings
    @GetMapping("/rooms")
    public List<Room> displayAll() {
        return service.findAll();
    }
}
