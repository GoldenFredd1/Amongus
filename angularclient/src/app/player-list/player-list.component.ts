import { Component, OnInit } from '@angular/core';
import { Player } from '../models/player';
import { PlayerServiceService } from '../player-service.service';

@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {
  players: Player[];
  title: string;

  constructor(private playerServiceService: PlayerServiceService) {
    this.title="Amidst Our Own Selves";
  }

  ngOnInit() {
    this.playerServiceService.findAll().subscribe(data => {
      this.players = data;
    });
  }

}
