import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { GameService } from '../game.service';
import { PlayerServiceService } from '../player-service.service';
import { PlayerTaskService } from '../player-task.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';


@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {
  players: Player[];
  public username;
  player: Player;

  constructor(
    private playerServiceService: PlayerServiceService,
    private gameService: GameService,
    private playerTaskService: PlayerTaskService,
    private authenticationService: AuthenticationService,
    private router: Router) {
      this.username = authenticationService.getUser();
  }

  ngOnInit() {
    this.getPlayers();
    this.getSpecificPlayer();
  }

  getPlayers() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data, console.log(data)});
  }

  async getSpecificPlayer(){
    console.log(this.username);
    this.player = await this.gameService.getPlayer(this.username).toPromise();
  }

  async add() {
    await this.playerServiceService.addComputerPlayer(new Player())
    this.getPlayers();
  }

  async delete(playerId) {
    await this.playerServiceService.deleteComputerPlayer(playerId)
    this.getPlayers();
  }

  async setUpGame() {
    //TODO call on  a method to reset all players to false/false..
    await this.getSpecificPlayer();
    console.log("AppUSer ID");
    console.log(this.player.playerId);
    await this.gameService.setUpGame(this.players);

    for(let i=0; i<this.players.length; i++) {
      await this.playerServiceService.editPlayer(this.players[i]);
    }

    await this.playerTaskService.assignTasks(this.players);

    this.router.navigate(["/game"]);
  }

}
