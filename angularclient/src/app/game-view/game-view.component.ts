import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { Task } from '../models/task';
import { PlayerServiceService } from '../player-service.service';
import { TaskService } from '../task-service';
import { RoomsService } from '../rooms.service';
import { Rooms } from '../models/rooms';


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

  constructor(
      private playerServiceService: PlayerServiceService,
      private taskService: TaskService,
      private roomsService: RoomsService,
      ) {
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

}
