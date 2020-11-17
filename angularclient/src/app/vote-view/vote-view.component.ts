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
    //TODO: CONNECT THE PLAYERID
    this.vote.playerId = 5;
    this.gameService.votingPlayerOut(this.vote);
    this.router.navigate(["/game"]);
  }

}
