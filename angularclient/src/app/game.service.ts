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
  private appUserUrl: string;
  private player: Player;
  private game: Game;

  constructor(private http: HttpClient) {
    this.voteUrl = 'http://localhost:8080/votes';
    this.gameUrl = 'http://localhost:8080/game';
    this.appUserUrl = `http://localhost:8080`;
  }

  public checkGameOver(gameRoomCode: string): Observable<boolean> {
    return this.http.get<boolean>(this.gameUrl + "/gameOver"+ (`/${gameRoomCode}`));
  }

  public checkImposterWin(gameRoomCode: string): Observable<boolean> {
    return this.http.get<boolean>(this.gameUrl + "/whoWon" + (`/${gameRoomCode}`));
  }

  public checkForDeadBody(gameId): Observable<boolean> {
    return this.http.get<boolean>(this.gameUrl+ "/bodyCheck"+ (`/${gameId}`));
  }

  public getRealPlayerGame(playerId: number, gameRoomCode: string): Observable<any>{
    return this.http.get<any>(this.gameUrl +"/findReal" +  (`/${playerId}`)+ (`/${gameRoomCode}`));
  }

  public getListOfPlayersInRoom(gameRoomCode: string, playerId: number): Observable<Player[]>{
    return this.http.get<Player[]>(this.gameUrl +"/findPlayers" + (`/${gameRoomCode}`) + (`/${playerId}`));
  }

  public getGameRoomCode() {
      return this.game.gameRoomCode;
  }

  public getGameId() {
    return this.game.gameId;
  }

  public async addGame(game: Game) {
    return await this.http.post(this.gameUrl, game, this.httpOptions).toPromise();
  }

  public async deleteGame(gameId: number) {
    return await this.http.delete(this.gameUrl + (`/${gameId}`)).toPromise();
  }

  public deleteAllGames(): Observable<boolean> {
    return this.http.delete<boolean>(this.gameUrl, this.httpOptions);
  }

  public deleteAllVotes(): Observable<boolean> {
    return this.http.delete<boolean>(this.voteUrl, this.httpOptions);
  }

  public async votingPlayerOut(vote){
    return await this.http.post(this.voteUrl,vote,this.httpOptions).toPromise();
  }

  public getPlayer(username:String): Observable<Player>{
    return this.http.get<Player>(this.appUserUrl + (`/${username}`));

  }

  public editGame(game: Game) {
    return this.http.put(this.gameUrl + "/updateGame" +  (`/${game.gameId}`), game).toPromise();
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
        }
        const data: any = await this.addGame(this.game);
        this.game.gameId=data.gameId;
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
    return code;
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
