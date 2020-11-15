import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { Rooms } from '../models/rooms';
import { PlayerServiceService } from '../player-service.service';


@Component({
  selector: 'app-vote-view',
  templateUrl: './vote-view.component.html',
  styleUrls: ['./vote-view.component.css']
})
export class VoteViewComponent implements OnInit {
  players: Player[];
  rooms: Rooms[];

  constructor(
    private playerServiceService: PlayerServiceService,
  ) { }

  ngOnInit(): void {
    this.getPlayers();
  }

  getPlayers() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data});
  }


}
