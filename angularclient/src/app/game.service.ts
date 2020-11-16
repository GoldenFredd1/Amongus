import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from './models/game';
import { Observable } from 'rxjs';
import { PlayerServiceService } from './player-service.service'
import { Player } from './models/player';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private gameUrl: string;
//   private players: Player[];
  private game: Game;

  constructor(private http: HttpClient) {
    this.gameUrl = 'http://localhost:8080/game';
  }

  public findGameById(): Observable<Game[]> {
    return this.http.get<Game[]>(this.gameUrl);
  }

  public getGameRoomCode() {
      console.log(this.game.gameRoomCode);
      return this.game.gameRoomCode;
  }

  public async addGame(game: Game) {
    return await this.http.post(this.gameUrl, game, this.httpOptions).toPromise();
  }

  public deleteGame(gameId: number) {
    return this.http.delete(this.gameUrl + (`/${gameId}`));
  }

  public editGame(game: Game) {
      return this.http.put(this.gameUrl + (`/${game.gameId}`), game);
  }

  public realSetUp(players: Player[]) {
    var gameRoomCode = this.generateGameCode();
    var roomId = 1;
    for(let player of players) {
        this.game = new Game();
        this.game.gameRoomCode = gameRoomCode;
        this.game.roomId = roomId;
        this.game.playerId = player.playerId;
        console.log(this.game);
        this.addGame(this.game);
    }
  }

//   public setUpGame() {
//     this.playerService.findAll().subscribe(data => {
//         this.players = data, this.realSetUp(this.players)});
//   }

//   public setPlayers(players: Player[]) {
//       this.players = players;
//   }

  generateGameCode() {
    var code="";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for(var i=0; i<6; i++)
      code += possible.charAt(Math.floor(Math.random()*possible.length));
    console.log(code);
    return code;
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
