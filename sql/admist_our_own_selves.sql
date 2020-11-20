drop database if exists amidst_our_own_selves;
create database amidst_our_own_selves;
use amidst_our_own_selves;


create table app_user (
  app_user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username varchar(50) not null unique,
  password_hash varchar(2048) not null,
  disabled boolean not null default(0));



create table app_role (
  app_role_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL unique);


create table app_user_role (
  app_user_id INT NOT NULL,
  app_role_id INT NOT NULL,
  CONSTRAINT fk_app_user_role_app_user_id
    FOREIGN KEY (app_user_id)
    REFERENCES app_user (app_user_id),
  CONSTRAINT fk_app_user_role_app_role_role_id
    FOREIGN KEY (app_role_id)
    REFERENCES app_role (app_role_id));

create table Player (
  playerId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  playerName VARCHAR(45) NOT NULL,
  isDead TINYINT NOT NULL,
  isImposter TINYINT NOT NULL,
  app_user_id INT NULL,
  CONSTRAINT fk_player_app_user_role_id
    FOREIGN KEY (app_user_id)
    REFERENCES app_user_role (app_user_id));

create table Room (
  roomId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  roomName VARCHAR(45) NOT NULL);


create table Task (
  taskId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  taskName VARCHAR(45) NOT NULL,
  roomId INT NOT NULL,
  CONSTRAINT fk_task_room_id
    FOREIGN KEY (roomId)
    REFERENCES Room (roomId));
    
    
create table Votes (
	voteId  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    gameRoomCode VARCHAR(6) NOT NULL,
    votedForPlayerId INT NOT NULL,
    playerId Int Not Null,
    CONSTRAINT fk_votes_player_id
    FOREIGN KEY (playerId)
    REFERENCES Player (playerId)
);


create table Game (
  gameId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  gameRoomCode VARCHAR(6) NOT NULL,
  playerId INT NOT NULL,
  roomId INT NOT NULL,
  CONSTRAINT fk_game_player_id
    FOREIGN KEY (playerId)
    REFERENCES Player (playerId),
  CONSTRAINT fk_game_room_id
    FOREIGN KEY (roomId)
    REFERENCES Room (roomId));


create table Player_Assigned_Task (
  taskId INT NOT NULL,
  playerId INT NOT NULL,
  isComplete TINYINT NOT NULL,
  CONSTRAINT fk_player_assigned_task_task_id
    FOREIGN KEY (taskid)
    REFERENCES Task (taskId),
  CONSTRAINT fk_player_assigned_task_player_id
    FOREIGN KEY (playerId)
    REFERENCES Player (playerId));
    


