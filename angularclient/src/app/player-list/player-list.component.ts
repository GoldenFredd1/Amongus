import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from '../models/player';
import { PlayerServiceService } from '../player-service.service';


@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {
  players: Player[];

  constructor(
    private route: ActivatedRoute,
    private playerServiceService: PlayerServiceService) {
  }

  ngOnInit() {
    this.getPlayers();
  }

  getPlayers() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data, console.log(data)});
  }z

  async add() {
    await this.playerServiceService.addComputerPlayer(new Player())
    this.getPlayers();
  }

  async delete(playerId) {
    await this.playerServiceService.deleteComputerPlayer(playerId)
    this.getPlayers();
  }

  // // needs work (still need to get playerIDs)
  // setUpGame() {

  //   var gameRoomCode = this.generateGameCode();
  //   var roomId = 1;
  // }

  // // could be private?
  // generateGameCode() {
  //   var code="";
  //   var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  //   for(var i=0; i<6; i++)
  //     code += possible.charAt(Math.floor(Math.random()*possible.length));
    
  // console.log(code);
  // return code;
  // }


}
