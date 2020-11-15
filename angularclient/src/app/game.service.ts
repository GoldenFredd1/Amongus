import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from './models/game';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private gameUrl: string;

  constructor(private http: HttpClient) {
    this.gameUrl = 'http://localhost:8080/game';
  }

  public findGameById(): Observable<Game[]> {
    return this.http.get<Game[]>(this.gameUrl);
  }

  public save(game: Game) {
    return this.http.post<Game>(this.gameUrl, game);
  }

  public deleteGame(gameId: number) {
    return this.http.delete(this.gameUrl + (`/${gameId}`));
  }

  public editGame(game: Game) {
      return this.http.put(this.gameUrl + (`/${game.gameId}`), game);
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
