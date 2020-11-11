import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Player } from '/player';
// TODO: make a model folder for models
import { Observable } from 'rxjs/Observable';

@Injectable(
//   providedIn: 'root'
)
export class PlayerServiceService {
  private playersUrl: string;

  constructor(private http: HttpClient) {
    this.playersUrl = 'http://localhost:8080/players';
    }

    public findAll(): Observable<Player[]> {
      return this.http.get<Player[]>(this.playersUrl);
    }
}
