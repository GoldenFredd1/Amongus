import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { Task } from '../models/task';
import { PlayerServiceService } from '../player-service.service';
import { TaskService } from '../task-service';
import { RoomsService } from '../rooms.service';
import { Rooms } from '../models/rooms';
import { FormBuilder } from "@angular/forms";
import { Game } from '../models/game';
import { GameService } from "../game.service";


@Component({
  selector: 'app-game-view',
  templateUrl: './game-view.component.html',
  styleUrls: ['./game-view.component.css']
})
export class GameViewComponent implements OnInit {
  players: Player[];
  tasks: Task[];
  rooms: Rooms[];
  title: string;
  public game;

  constructor(
      private playerServiceService: PlayerServiceService,
      private taskService: TaskService,
      private roomsService: RoomsService,
      private gameService: GameService,
      public fb: FormBuilder
      ) {
        this.game = gameService.getGame();
  }

  ngOnInit() {
    this.getPlayers();
    this.getTasks();
    this.getRooms();
  }


  getPlayers() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data});
  }

  getTasks() {
    this.taskService.findAll().subscribe(data => {
      this.tasks = data, console.log(data)});
  }

  getRooms() {
    this.roomsService.findAll().subscribe(data => {
      this.rooms = data, console.log(data)});
  }

  async updateTask(taskId) {
    await this.taskService.updateTask(taskId)
    this.getPlayers();
    this.getTasks();
    this.getRooms();
  }

  // actual roomId
  roomNameForm = this.fb.group({
    roomName: ['']
  })

  onSubmit() {
    //this.updateRoom(JSON.stringify(this.roomNameForm.value).slice(12,-1));
    console.log("start");
    console.log(this.game.roomId);
    this.game.roomId = (JSON.stringify(this.roomNameForm.value).slice(12,-1));
    console.log(this.game.roomId);
    console.log("end");
    this.gameService.editGame(this.game);
  }

  //TODO: change this to update in the game table..not room table duh
  updateRoom(roomName) {
    console.log(roomName);
     this.roomsService.findByRoomName(roomName);
  }
}
