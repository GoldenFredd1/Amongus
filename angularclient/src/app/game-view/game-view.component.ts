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
  playersInRoom: Player[];
  public realPlayer;
  public game;
  playerId: number;
  gameRoomCode: string;
  isGameOver: boolean;
  didImposterWin: boolean;
  appUserIdReal: number = 1;
  public username;
  isValid: any;

  constructor(
      private playerServiceService: PlayerServiceService,
      private playerTaskService: PlayerTaskService,
      private roomsService: RoomsService,
      private gameService: GameService,
      private authenticationService: AuthenticationService,
      public fb: FormBuilder,
      private router: Router
      ) {
        this.username = authenticationService.getUser();
        this.realPlayer = this.getRealPlayer(this.username);
        this.gameRoomCode = gameService.getGameRoomCode();
        this.game = this.getRealGame(this.gameRoomCode);
         
  }

  async ngOnInit() {
    await this.getPlayers();
    await this.getRooms();
   // await this.getIsGameOver();
   // await this.getDidImposterWin();
    await this.deadBodyCheck();
    await this.playersToKillCheck();
    await this.getPlayerTasks();
  }

  async getIsGameOver() {
    await this.gameService.checkGameOver(this.gameService.getGameRoomCode())
    .subscribe(data => {
      this.isGameOver = data
    });
    if(this.isGameOver) {
      await this.getDidImposterWin();
    }
  }

  async deadBodyCheck(){
    await this.gameService.checkForDeadBody(this.gameRoomCode).subscribe(data => {
      this.isValid = data});
  }

  async playersToKillCheck(){
    console.log("players in room here: ");
    await this.gameService.getListOfPlayersInRoom(this.gameRoomCode, this.appUserIdReal).subscribe(
      data => {this.playersInRoom = data, console.log(data)}
    );
  }

  async getDidImposterWin() {
    await this.gameService.checkImposterWin(this.gameService.getGameRoomCode())
    .subscribe(data => {
      this.didImposterWin = data});
  }

  async getPlayers() {
    await this.playerServiceService.findAll().subscribe(data => {
      this.players = data});
  }

  async getRealPlayer(username:string){
    await this.playerServiceService.findUser(username)
    .subscribe(playerdata => {this.realPlayer = playerdata});
  }

  async getRealGame(roomCode:string) {
    //console.log("ROOM CODE " + roomCode);
    await this.gameService.getRealPlayerGame(1, roomCode)
    .subscribe(gameData => { this.game = gameData});
  }

  async getPlayerTasks() {
    await this.playerTaskService.findPlayerTaskByPlayerId(this.gameService.getGameRoomCode(),
     1).subscribe(data => {
      this.playerTasks = data});
  }


  async getRooms() {
    console.log("Listing rooms");
    await this.roomsService.findAll().subscribe(data => {
      this.rooms = data, console.log(data)});
  }

  async updateTask(taskId) {
    await this.playerTaskService.updatePlayerTask(taskId, this.game);
    await this.deadBodyCheck();
    await this.playersToKillCheck();
  }

  // actual roomId
  roomNameForm = this.fb.group({
    roomName: ['']
  })

  onSubmit() {
    this.game.roomId = (JSON.stringify(this.roomNameForm.value).slice(12,-1));
    this.gameService.editGame(this.game);
    this.deadBodyCheck();
    this.playersToKillCheck();
  }

  KillPlayerInRoom = this.fb.group({
    killPeople: ['']
  })

  onKillSubmit() {
    console.log(JSON.stringify(this.KillPlayerInRoom.value).slice(14,-1));
    this.playerId = parseInt(JSON.stringify(this.KillPlayerInRoom.value).slice(14,-1));
    this.playerServiceService.killPlayer(this.playerId, this.game);
    this.deadBodyCheck();
    this.playersToKillCheck();
  }
}
