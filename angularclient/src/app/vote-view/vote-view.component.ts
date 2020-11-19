import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { GameService } from '../game.service';
import { Player } from '../models/player';
import { Rooms } from '../models/rooms';
import { Votes } from '../models/votes';
import { PlayerServiceService } from '../player-service.service';
import { PlayerTaskService } from '../player-task.service';
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
  isGameOver: boolean;
  didImposterWin: boolean;

  constructor(
    private playerServiceService: PlayerServiceService,
    private playerTaskService: PlayerTaskService,
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


  async resetGame() {
    console.log("Start of reset game.");
    await this.gameService.deleteAllGames().toPromise();
    console.log("Done with delete all games.");
    await this.playerTaskService.deleteAllPlayerTasks().toPromise();
    console.log("Done with delete all Player Tasks.");
    await this.gameService.deleteAllVotes().toPromise();
    console.log("Done with delete all Votes.");
    await this.playerServiceService.deleteAllPlayersButRealPlayer().toPromise();
    console.log("Done with delete all Players.");
    await this.playerServiceService.addComputerPlayer(new Player());
    await this.playerServiceService.addComputerPlayer(new Player());
    await this.playerServiceService.addComputerPlayer(new Player());
    console.log("End of reset game.");
  }

  async getIsGameOver() {
    this.isGameOver = await this.gameService.checkGameOver(this.gameService.getGameRoomCode()).toPromise();
    if(this.isGameOver == true) {
      await this.getDidImposterWin();
    }
  }

  async getDidImposterWin() {
    this.didImposterWin = await this.gameService.checkImposterWin(this.gameService.getGameRoomCode()).toPromise();
  }

  VotingPlayerOut = this.fb.group({
    playerId: ['']
  })

  async onSubmit() {
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

    console.log("Now checking if the game is over.....");
    await this.getIsGameOver();
    console.log("Game over data: " + this.isGameOver);
    if(this.isGameOver == true) {
      console.log("Game Over!");
      if(this.didImposterWin == true) {
        console.log("The Imposter won.");
      } else {
        console.log("The Crewmates won.");
      }
      // reset Game
      // return to home page
      await this.resetGame();
      this.router.navigate(["/"]);
    }else{
      this.router.navigate(["/game"]);
    }
  }

}
