import { NgModule } from '@angular/core';
import { PlayerListComponent } from './player-list/player-list.component';
import { GameViewComponent } from './game-view/game-view.component';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthGuardService } from './service/auth-guard.service';
import { VoteViewComponent } from './vote-view/vote-view.component'; 
import { EndGameViewComponent } from './end-game-view/end-game-view.component';

const routes: Routes = [
  { path: '', component: PlayerListComponent,canActivate:[AuthGuardService] },
  { path: 'players', component: PlayerListComponent,canActivate:[AuthGuardService]},
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent,canActivate:[AuthGuardService] },
  { path: 'game', component: GameViewComponent},
  { path: 'voteScreen', component: VoteViewComponent},
  { path: 'gameOver', component: EndGameViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
