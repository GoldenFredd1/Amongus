import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { GameService } from '../game.service';
import { PlayerServiceService } from '../player-service.service';
import { PlayerTaskService } from '../player-task.service';
import { TaskService } from '../task-service';
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

  getSpecificPlayer(){
    this.playerServiceService.findUser(this.username);
    // .subscribe(data => {
      // this.players = data, console.log(data)});
      
  }

  async add() {
    await this.playerServiceService.addComputerPlayer(new Player())
    this.getPlayers();
  }

  async delete(playerId) {
    await this.playerServiceService.deleteComputerPlayer(playerId)
    this.getPlayers();
  }


  setUpGame() {
    this.gameService.setUpGame(this.players);

    for(let i=0; i<this.players.length; i++) {
      this.playerServiceService.editPlayer(this.players[i]);
      console.log(this.players[i]);
    }
    console.log("----------");
    console.log(this.players);
    console.log("----------");

    this.playerTaskService.assignTasks(this.players);

    this.router.navigate(["/game"]);
  }

}
