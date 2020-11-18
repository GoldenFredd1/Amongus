import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { GameService } from '../game.service';
import { Player } from '../models/player';
import { Rooms } from '../models/rooms';
import { Votes } from '../models/votes';
import { PlayerServiceService } from '../player-service.service';
import { AuthenticationService } from '../service/authentication.service';


@Component({
  selector: 'app-vote-view',
  templateUrl: './vote-view.component.html',
  styleUrls: ['./vote-view.component.css']
})
export class VoteViewComponent implements OnInit {
  players: Player[];
  rooms: Rooms[];
  public game;
  public vote: Votes;
  playerIdVotedFor: string;

  constructor(
    private playerServiceService: PlayerServiceService,
    private gameService: GameService,
    public fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router
  ) { 
    this.game = gameService.getGame();
  }

  ngOnInit(): void {
    this.getPlayers();
  }

  getPlayers() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data});
  }
  

  VotingPlayerOut = this.fb.group({
    playerId: ['']
  })

  onSubmit() {
    this.vote = new Votes();
    this.vote.gameRoomCode = this.game.gameRoomCode;
    this.vote.votedForPlayerId = parseInt(JSON.stringify(this.VotingPlayerOut.value).slice(12,-1));
    //hardcoded and I am not proud of it..
    this.vote.playerId = 1;
    console.log("start voting");
    console.log(this.vote.gameRoomCode);
    console.log(this.vote.playerId);
    console.log(this.vote.votedForPlayerId);
    console.log(JSON.stringify(this.VotingPlayerOut.value));
    this.playerIdVotedFor = (JSON.stringify(this.VotingPlayerOut.value).slice(12,-1));
    console.log(this.playerIdVotedFor);
    console.log("end voting");
    this.gameService.votingPlayerOut(this.vote);
    this.router.navigate(["/game"]);
  }

}
