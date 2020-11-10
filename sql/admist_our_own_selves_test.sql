drop database if exists amidst_our_own_selves_test;
create database amidst_our_own_selves_test;
use amidst_our_own_selves_test;


create table app_user (
  app_user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  password_hash VARCHAR(45) NOT NULL,
  disabled TINYINT NOT NULL);



create table app_role (
  app_role_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL);


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


create table Game (
  gameId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  playerId INT NOT NULL,
  roomId INT NOT NULL,
  CONSTRAINT fk_game_player_id
    FOREIGN KEY (playerId)
    REFERENCES Player (playerId),
  CONSTRAINT fk_game_room_id
    FOREIGN KEY (roomId)
    REFERENCES Room (roomId));


create table Player_Assigned_Task (
  task_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  playerId INT NOT NULL,
  isComplete TINYINT NOT NULL,
  CONSTRAINT fk_player_assigned_task_task_id
    FOREIGN KEY (task_id)
    REFERENCES Task (taskId),
  CONSTRAINT fk_player_assigned_task_player_id
    FOREIGN KEY (playerId)
    REFERENCES Player (playerId));


delimiter //
create procedure set_known_good_state()
begin


delete from Player;
	alter table Player auto_increment = 1;
delete from app_user_role;
delete from app_role;
	alter table app_role auto_increment = 1;
delete from app_user;
	alter table app_user auto_increment = 1;
delete from Task;
	alter table Task auto_increment = 1;
delete from Room;
	alter table Room auto_increment = 1;
delete from Game;
delete from Player_Assigned_Task;

insert into app_user values
(1,'Bob the test player','hashpasswordgoeshere',false),
(2,'Computer','hashpasswordgoeshere',false);

insert into app_role values
(1,'Admin'),
(2,'User'),
(3,'Computer');

insert into app_user_role values
(1,2),
(2,3);

insert into Player(playerId, playerName, isDead, isImposter, app_user_id) values
(1,'testPlayerAlpha',false, false,1),
(2,'Computer1',false, false,2),
(3,'Computer2',false, false,2),
(4,'Computer3',false, false,2);

insert into Room(roomId,roomName) values
(1,'Food Court'),
(2,'Payless ShoeSource'),
(3,'Toys R Us'),
(4,'Sears'),
(5,'Radio Shack'),
(6,'Neiman Marcus'),
(7,'The Hallway of Failure');

insert into Task(taskId,taskName,roomId) values
(1,'Pickup trash',1),
(2,'Collect moldy sandwich',1),
(3,'Pickout some fabulous shoes',2),
(4,'Smash bugs with shoes',2),
(5,'Shoot all the targets at the nerf range',3),
(6,'Put the dolls to bed.',3),
(7,'Re-wire a fridge',4),
(8,'Setup a christmas tree',4),
(9,'Chase down mice with an RC car',5),
(10,'Check for usable batteries',5),
(11,'Find a fancy hat',6),
(12,'Put makeup on a mannequin',6);


insert into Game values
(1, 1, 1),
(1, 2, 2),
(1, 3, 6),
(1, 4, 3);


insert into Player_Assigned_Task values
(1, 1, false),
(8, 1, false),
(4, 2, false),
(10, 2, false),
(2, 3, false),
(8, 3, false),
(3, 4, false),
(7, 4, false);

end //
delimiter ;


-- select playerId, playerName, isDead, isImposter, app_user_id
-- from Player;