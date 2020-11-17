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

  // this doesn't make sense, it doesn't pass in an ID... oh well, we probably won't need it
  public findGameById(): Observable<Game[]> {
    return this.http.get<Game[]>(this.gameUrl);
  }

  // public isGameOver(gameRoomCode: string): Observable<Boolean> {
  //   console.log(this.gameUrl + (`/${gameRoomCode}`));
  //   return this.http.get<Boolean>(this.gameUrl + (`/${gameRoomCode}`));
  // }

  public isGameOver(gameRoomCode: string) {
    console.log(this.gameUrl + (`/${gameRoomCode}`));
    return this.http.get(this.gameUrl + (`/${gameRoomCode}`));
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

  public async votingPlayerOut(vote){
    return await this.http.post(this.voteUrl,vote,this.httpOptions).toPromise();
  }


  public editGame(game: Game) {
    console.log(this.gameUrl + (`/${game.gameId}`));
    console.log("start of edit");
    console.log("GameID " +game.gameId);
    console.log("GameRoomCode " + game.gameRoomCode);
    console.log("Player id: "+ game.playerId);
    console.log("ROom ID: " + game.roomId);
    console.log("end of edit game");
    return this.http.put(this.gameUrl + (`/${game.gameId}`), game).toPromise();
  }

  public async setUpGame(players: Player[]) {
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
            // CHANGE THIS: JUST FOR TESTING THE END GAME CONDITION,
            // THE LINE BELOW SHOULD BE DELETED
            // players[i].dead = true;
        }
        const data: any = await this.addGame(this.game);
        // console.log("ADD GAME RETURN VALUE");
        // console.log(data);
        this.game.gameId=data.gameId;
        console.log(this.game);
    }
  }

  public getGame(){
    return this.game;
  }


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
