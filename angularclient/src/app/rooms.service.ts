import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rooms } from './models/rooms';

@Injectable({
  providedIn: 'root'
})
export class RoomsService {
  private roomUrl: string;

  constructor(private http: HttpClient) {
    this.roomUrl = 'http://localhost:8080/rooms';
   }

   public findAll(): Observable<Rooms[]> {
     return this.http.get<Rooms[]>(this.roomUrl);
   }

   public save(rooms: Rooms) {
    return this.http.post<Rooms>(this.roomUrl, rooms);
  }

  public findByRoomName(roomName: string) {
    console.log(this.roomUrl + (`/${roomName}`));
    return this.http.post<Rooms>(this.roomUrl + (`/${roomName}`),roomName);
  }

}
