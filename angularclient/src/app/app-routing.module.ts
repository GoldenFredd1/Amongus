import { NgModule } from '@angular/core';
import { PlayerListComponent } from './player-list/player-list.component';
import { GameViewComponent } from './game-view/game-view.component';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthGuardService } from './service/auth-guard.service';

const routes: Routes = [
<<<<<<< HEAD
  { path: '', component: PlayerListComponent,canActivate:[AuthGuardService] },
  { path: 'players', component: PlayerListComponent,canActivate:[AuthGuardService]},
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent,canActivate:[AuthGuardService] },
=======
  {path: 'players', component: PlayerListComponent},
  {path: 'game', component: GameViewComponent}
>>>>>>> dc613ba0d7f4c7c24588da9b59f728e4c13f8be2
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
