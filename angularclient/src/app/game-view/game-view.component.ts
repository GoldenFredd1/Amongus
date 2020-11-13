import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { Task } from '../models/task';
import { PlayerServiceService } from '../player-service.service';
import { TaskService } from '../task-service';


@Component({
  selector: 'app-game-view',
  templateUrl: './game-view.component.html',
  styleUrls: ['./game-view.component.css']
})
export class GameViewComponent implements OnInit {
  players: Player[];
  tasks: Task[];
  title: string;

  constructor(
      private playerServiceService: PlayerServiceService,
      private taskService: TaskService,
      ) {
    this.title="Amidst Our Own Selves";
  }

  ngOnInit() {
    this.getPlayers();
    this.getTasks();
  }

  getPlayers() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data});
  }

  getTasks() {
    this.taskService.findAll().subscribe(data => {
      this.tasks = data});
  }

}
