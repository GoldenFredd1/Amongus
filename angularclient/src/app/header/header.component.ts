import { Component, OnInit } from '@angular/core';
import { GameService } from '../game.service';
import { PlayerServiceService } from '../player-service.service';
import { PlayerTaskService } from '../player-task.service';
import { AuthenticationService } from '../service/authentication.service';
import { Player } from '../models/player';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public username;
  player: Player;

  constructor(
    public loginService:AuthenticationService,
    public gameService: GameService,
    public playerServiceService: PlayerServiceService,
    public playerTaskService: PlayerTaskService,
    private authenticationService: AuthenticationService,
    private router: Router,
    ) { 
      this.username = authenticationService.getUser();
    }

  ngOnInit(): void {
  }

  async getSpecificPlayer(){
    console.log("hitting header resetplayer ");
    console.log(this.username);
    this.player = await this.gameService.getPlayer('arjohnson').toPromise();
  }

  async resetGame() {
    console.log("trying to reset the game...");
    await this.gameService.deleteAllGames().toPromise();
    await this.playerTaskService.deleteAllPlayerTasks().toPromise();
    await this.gameService.deleteAllVotes().toPromise();
    await this.getSpecificPlayer();
    this.player.dead = false;
    this.player.imposter = false;
    await this.playerServiceService.editPlayer(this.player);
    await this.playerServiceService.deleteAllPlayersButRealPlayer().toPromise();
    await this.playerServiceService.addComputerPlayer(new Player());
    await this.playerServiceService.addComputerPlayer(new Player());
    await this.playerServiceService.addComputerPlayer(new Player());
    console.log("done resetting the game...");
    await this.router.navigate(["/"]);
  }

}
