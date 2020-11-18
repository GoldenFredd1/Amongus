import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { PlayerTask } from '../models/player-task';
import { PlayerServiceService } from '../player-service.service';
import { RoomsService } from '../rooms.service';
import { Rooms } from '../models/rooms';
import { FormBuilder } from "@angular/forms";
import { Game } from '../models/game';
import { GameService } from "../game.service";
import { PlayerTaskService } from '../player-task.service';
import { Task } from '../models/task';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-game-view',
  templateUrl: './game-view.component.html',
  styleUrls: ['./game-view.component.css']
})
export class GameViewComponent implements OnInit {
  players: Player[];
  playerTasks: PlayerTask[];
  tasks: Task[];
  rooms: Rooms[];
  realPlayer: Player;
  title: string;
  public game;
  isGameOver: boolean;
  didImposterWin: boolean;
  appUserIdReal: number = 1;
  public username;
  public player;
  isValid: any;

  constructor(
      private playerServiceService: PlayerServiceService,
      private playerTaskService: PlayerTaskService,
      private roomsService: RoomsService,
      private gameService: GameService,
      public fb: FormBuilder,
      private router: Router
      ) {
         this.game = gameService.getGame();
        // this.username = authenticationService.getUser();
  }

  async ngOnInit() {
    await this.getPlayers();
    await this.getRooms();
   // await this.getIsGameOver();
   // await this.getDidImposterWin();
    await this.getRealPlayer();
    await this.deadBodyCheck();
    this.getPlayerTasks(this.realPlayer.playerId);
  }

  async getIsGameOver() {
    await this.gameService.checkGameOver(this.gameService.getGameRoomCode())
    .subscribe(data => {
      this.isGameOver = data
    });
    // console.log("Is the Game over? " + this.isGameOver);
    if(this.isGameOver) {
      this.getDidImposterWin();
    }
  }

  async deadBodyCheck(){
    await this.gameService.checkForDeadBody(this.game.gameId).subscribe(data => {
      this.isValid = data, console.log(data);
    });
  }

  async getDidImposterWin() {
    await this.gameService.checkImposterWin(this.gameService.getGameRoomCode())
    .subscribe(data => {
      this.didImposterWin = data
    });
    // console.log("Did the imposter win? " + this.didImposterWin);
  }

  async getPlayers() {
    await this.playerServiceService.findAll().subscribe(data => {
      this.players = data});
  }

  async getRealPlayer() {
    this.realPlayer = await this.playerServiceService.findRealPlayer(this.appUserIdReal);
  }

  async getPlayerTasks(playerId: number) {
    await this.playerTaskService.findPlayerTaskByPlayerId(this.gameService.getGameRoomCode(), playerId).subscribe(data => {
      this.playerTasks = data, console.log(data)});
  }


  async getRooms() {
    await this.roomsService.findAll().subscribe(data => {
      this.rooms = data, console.log(data)});
  }

  async updateTask(taskId) {
    // console.log("Triggering update tasks");
    // console.log("Task id: " + taskId);
    // console.log("GameID " + this.game.gameId);
    // console.log("Game Room Code: " + this.game.gameRoomCode);
    // console.log("ROOM ID: " + this.game.roomId);
    // console.log("PlayerID ID: " + this.game.playerId);
    await this.playerTaskService.updatePlayerTask(taskId, this.game);
    await this.deadBodyCheck();
  }

  // actual roomId
  roomNameForm = this.fb.group({
    roomName: ['']
  })

  onSubmit() {
    console.log("start submitting the Room Change");
    console.log("GameID " + this.game.gameId);
    console.log("Game Room Code: " + this.game.gameRoomCode);
    console.log("ROOM ID: " + this.game.roomId);
    this.game.roomId = (JSON.stringify(this.roomNameForm.value).slice(12,-1));
    console.log("RoomID After: " + this.game.roomId);
    console.log("end the Room Change");
    this.gameService.editGame(this.game);
    this.deadBodyCheck();
  }

  //TODO: change this to update in the game table..not room table duh
  // updateRoom(roomName) {
  //   console.log(roomName);
  //    this.roomsService.findByRoomName(roomName);
  // }
}
