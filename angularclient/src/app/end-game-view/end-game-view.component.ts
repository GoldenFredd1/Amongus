import { Component, OnInit } from '@angular/core';
import { GameService } from '../game.service';

@Component({
  selector: 'app-end-game-view',
  templateUrl: './end-game-view.component.html',
  styleUrls: ['./end-game-view.component.css']
})

export class EndGameViewComponent implements OnInit {
  didImposterWin: boolean;

  constructor(
    private gameService: GameService,
  ) {}

  async ngOnInit() {
    this.didImposterWin = await this.gameService.checkImposterWin(this.gameService.getGameRoomCode()).toPromise();
    console.log(this.didImposterWin);
  }

}
