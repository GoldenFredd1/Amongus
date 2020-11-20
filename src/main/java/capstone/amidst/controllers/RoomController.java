package capstone.amidst.controllers;

import capstone.amidst.domain.RoomService;
import capstone.amidst.models.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<Room> findByRoomId(@PathVariable int roomId){
        Room room = service.findById(roomId);
        if(room == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(room);
    }

}
