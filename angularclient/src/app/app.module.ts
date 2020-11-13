import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlayerListComponent } from './player-list/player-list.component';
<<<<<<< HEAD
import { HttpClientModule, HTTP_INTERCEPTORS  } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { BasicAuthHtppInterceptorService } from './service/basic-auth-interceptor.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header.component';
=======
import { HttpClientModule } from '@angular/common/http';
import { PlayerServiceService } from './player-service.service';
import { GameViewComponent } from './game-view/game-view.component';
>>>>>>> dc613ba0d7f4c7c24588da9b59f728e4c13f8be2

@NgModule({
  declarations: [
    AppComponent,
    PlayerListComponent,
<<<<<<< HEAD
    LoginComponent,
    LogoutComponent,
    HeaderComponent,
=======
    GameViewComponent,
>>>>>>> dc613ba0d7f4c7c24588da9b59f728e4c13f8be2
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: BasicAuthHtppInterceptorService, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
