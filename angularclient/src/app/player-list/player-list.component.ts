import { Component, OnInit } from '@angular/core';
import { GameService } from '../game.service';
import { Player } from '../models/player';
import { PlayerServiceService } from '../player-service.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {
  players: Player[];

  constructor(
    private playerServiceService: PlayerServiceService,
    private gameService: GameService,
    private router: Router) {
  }

  ngOnInit() {
    this.getPlayers();
  }

  getPlayers() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data, console.log(data)});
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
    // this.gameService.setPlayers(this.players);
    this.gameService.realSetUp(this.players);
    this.router.navigate(["/game"]);
  }

}
