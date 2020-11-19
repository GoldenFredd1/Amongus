package capstone.amidst.controllers;


import capstone.amidst.domain.Result;
import capstone.amidst.domain.ResultType;
import capstone.amidst.domain.VoteService;
import capstone.amidst.models.Votes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class VoteController {
    private final VoteService service;

    public VoteController(VoteService service) {
        this.service = service;
    }

    @GetMapping("/votes")
    public List<Votes> findAll(){
        return service.findAll();
    }

    @GetMapping("/votes/{voteId}")
   public Votes findByVoteId(@PathVariable int voteId){
       return service.findById(voteId);
   }

   @PostMapping("/votes")
    public ResponseEntity<Object> addVote(@RequestBody Votes vote){
       System.out.println("Did we even connect?");
        Result<Votes> result = service.add(vote);
       if (result.getType() == ResultType.INVALID) {
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }
       System.out.println("We made it!!!!");
       return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
   }

    @DeleteMapping("/votes")
    public ResponseEntity<Void> deleteAllVotes() {
        System.out.println("You've made it to deleteAll() in the Vote controller.");
        service.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
