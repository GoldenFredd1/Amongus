import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from './models/game';
import { Observable } from 'rxjs';
import { Player } from './models/player';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private voteUrl: string;
  private gameUrl: string;
  private game: Game;
  

  constructor(private http: HttpClient) {
    this.voteUrl = 'http://localhost:8080/votes';
    this.gameUrl = 'http://localhost:8080/game';
  }

  public findGameById(): Observable<Game[]> {
    return this.http.get<Game[]>(this.gameUrl);
  }

  public getGameRoomCode() {
      // console.log(this.game.gameRoomCode);
      return this.game.gameRoomCode;
  }

  public async addGame(game: Game) {
    return await this.http.post(this.gameUrl, game, this.httpOptions).toPromise();
  }

  public deleteGame(gameId: number) {
    return this.http.delete(this.gameUrl + (`/${gameId}`));
  }

  public votingPlayerOut(vote){
    return this.http.post(this.voteUrl,vote,this.httpOptions).toPromise();
  }


  public editGame(game: Game) {
    //it doesn't know the gameID.....
    console.log(this.gameUrl + (`/${game.gameId}`));
    console.log("start of edit");
    console.log(game.gameId);
    console.log(game.gameRoomCode);
    console.log(game.roomId);
    console.log("end of edit game");
      return this.http.put(this.gameUrl + (`/${game.gameId}`), game);
  }

  public setUpGame(players: Player[]) {
    var gameRoomCode = this.generateGameCode();
    var roomId = 1;
    var imposterIndex = this.generateImposterIndex(players.length-1);

    for (var i=0; i<players.length; i++) {
        this.game = new Game();
        this.game.gameRoomCode = gameRoomCode;
        this.game.roomId = roomId;
        this.game.playerId = players[i].playerId;
        if (i == imposterIndex) {
            players[i].imposter = true;
        }
        // console.log(this.game);
        // console.log(players[i]);
        this.game = this.game;
        this.addGame(this.game);
    }
  }

  public getGame(){
    console.log("STARTING HERE")
    console.log(this.game.gameId);
    console.log(this.game.roomId);
    console.log(this.game.gameRoomCode);
    console.log(this.game.playerId);
    console.log("ENDING HERE")
    return this.game;
  }


//   public setUpGame() {
//     this.playerService.findAll().subscribe(data => {
//         this.players = data, this.realSetUp(this.players)});
//   }

//   public setPlayers(players: Player[]) {
//       this.players = players;
//   }

  generateImposterIndex(numPlayers: number) {
    return Math.floor(Math.random() * (numPlayers + 1));
  }

  generateGameCode() {
    var code="";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for(var i=0; i<6; i++)
      code += possible.charAt(Math.floor(Math.random()*possible.length));
    // console.log(code);
    return code;
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
