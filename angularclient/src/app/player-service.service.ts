import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Player } from './models/player';
import { Observable, of } from 'rxjs';

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
    console.log(this.appUserUrl + (`/${username}`));
    return this.http.get<Player>(this.appUserUrl + (`/${username}`)); 
  }

  public findRealPlayer(username: string): Observable<Player> {
    console.log(this.appUserUrl + (`/${username}`));
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

  public getRealPlayer() {
    return this.realPlayer;
  }

  // public setUpRealPlayer() {
  //   this.realPlayer = this.findRealPlayer(1);
  // }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
