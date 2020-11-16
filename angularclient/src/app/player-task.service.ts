import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PlayerTask } from './models/player-task';
import { Player } from './models/player';

// TODO: MAKE MODEL FOR PLAYER TASK
@Injectable({
  providedIn: 'root'
})
export class PlayerTaskService {

  private playerTaskUrl: string;
  private playerTask: PlayerTask;

  constructor(private http: HttpClient) {
    this.playerTaskUrl = 'http://localhost:8080/assignedTask';
  }

  public findAll(): Observable<PlayerTask[]> {
    return this.http.get<PlayerTask[]>(this.playerTaskUrl);
  }

  public findPlayerTaskById(): Observable<PlayerTask[]> {
    return this.http.get<PlayerTask[]>(this.playerTaskUrl);
  }

  public async save(playerTask: PlayerTask) {
    return await this.http.post<PlayerTask>(this.playerTaskUrl, playerTask, this.httpOptions).toPromise();
  }

  public async updatePlayerTask(playerTask: PlayerTask){
    return await this.http.put(this.playerTaskUrl + (`/${playerTask.taskId}`), playerTask).toPromise();
  }

  public assignTasks(players: Player[]) {
    for (let i=0; i<players.length; i++) {
      var firstTaskId = this.getRandomTaskId();
      var secondTaskId = this.getRandomTaskId();
      console.log("First Task ID: " + firstTaskId);
      console.log("Second Task ID: " + secondTaskId);
      this.playerTask = new PlayerTask();
      this.playerTask.taskId = firstTaskId;
      this.playerTask.playerId = players[i].playerId;
      this.playerTask.isComplete = false;
      console.log(this.playerTask);
      this.save(this.playerTask);

      this.playerTask = new PlayerTask();
      this.playerTask.taskId = secondTaskId;
      this.playerTask.playerId = players[i].playerId;
      this.playerTask.isComplete = false;
      console.log(this.playerTask);
      this.save(this.playerTask);
    }
  }

  getRandomTaskId() {
    // hard coded the number of tasks in our list... could refactor later
    return Math.floor(Math.random() * (12) + 1);
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
