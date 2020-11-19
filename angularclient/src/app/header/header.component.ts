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

  constructor(
    public loginService:AuthenticationService,
    public gameService: GameService,
    public playerServiceService: PlayerServiceService,
    public playerTaskService: PlayerTaskService,
    private router: Router,
    ) { }

  ngOnInit(): void {
  }

  async resetGame() {
    console.log("trying to reset the game...");
    await this.gameService.deleteAllGames().toPromise();
    await this.playerTaskService.deleteAllPlayerTasks().toPromise();
    await this.gameService.deleteAllVotes().toPromise();
    await this.playerServiceService.deleteAllPlayersButRealPlayer().toPromise();
    await this.playerServiceService.addComputerPlayer(new Player());
    await this.playerServiceService.addComputerPlayer(new Player());
    await this.playerServiceService.addComputerPlayer(new Player());
    console.log("done resetting the game...");
    await this.router.navigate(["/"]);
  }

}
