import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Player } from './models/player';
import { Observable, of } from 'rxjs';
import { Game } from './models/game';

@Injectable({
  providedIn: 'root'
})
export class PlayerServiceService {

  private playersUrl: string;
  private appUserUrl: string;
  private player: Player;
  private realPlayer: Player;

  constructor(private http: HttpClient) {
    this.playersUrl = 'http://localhost:8080/players';
    this.appUserUrl = `http://localhost:8080`;
  }

  public findAll(): Observable<Player[]> {
    return this.http.get<Player[]>(this.playersUrl);
  }

  public findUser(username:string): Observable<Player>  {
    return this.http.get<Player>(this.appUserUrl + (`/${username}`));
  }

  public findRealPlayer(username: string): Observable<Player> {
    return this.http.get<Player>(this.appUserUrl + (`/${username}`));
  }

  public async addComputerPlayer(player: Player) {
    return await this.http.post(this.playersUrl, player, this.httpOptions).toPromise();
 }

  public async deleteComputerPlayer(playerId: number) {
    return await this.http.delete(this.playersUrl + (`/${playerId}`)).toPromise();
  }

  public async editPlayer(player: Player) {
    return await this.http.put(this.playersUrl + (`/${player.playerId}`), player, this.httpOptions).toPromise();
  }

  public async killPlayer(playerId: number, game:Game){
    return await this.http.put(this.playersUrl+"/killPlayer" + (`/${playerId}`),game, this.httpOptions).toPromise();
  }

  public deleteAllPlayersButRealPlayer(): Observable<boolean> {
    return this.http.delete<boolean>(this.playersUrl, this.httpOptions);
  }

  public getRealPlayer() {
    return this.realPlayer;
  }


  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
