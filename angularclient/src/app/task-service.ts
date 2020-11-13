import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Task } from './models/task';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private taskUrl: string;

  constructor(private http: HttpClient) {
    this.taskUrl = 'http://localhost:8080/task';
  }

  public findAll(): Observable<Task[]> {
    return this.http.get<Task[]>(this.taskUrl);
  }

  public save(task: Task) {
    return this.http.post<Task>(this.taskUrl, task);
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
