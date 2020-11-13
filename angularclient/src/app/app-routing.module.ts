import { NgModule } from '@angular/core';
import { PlayerListComponent } from './player-list/player-list.component';
import { GameViewComponent } from './game-view/game-view.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: 'players', component: PlayerListComponent},
  {path: 'game', component: GameViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
