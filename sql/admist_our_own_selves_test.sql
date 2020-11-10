drop database if exists amidst_our_own_selves_test;
create database amidst_our_own_selves_test;
use amidst_our_own_selves_test;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`app_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`app_user` (
  `app_user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password_hash` VARCHAR(45) NOT NULL,
  `disabled` TINYINT NOT NULL,
  PRIMARY KEY (`app_user_id`),
	INDEX `app_user_id_UNIQUE` (`app_user_id` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`app_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`app_role` (
  `app_role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `app_role_id_UNIQUE` (`app_role_id` ASC) VISIBLE,
  PRIMARY KEY (`app_role_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`app_user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`app_user_role` (
  `app_user_id` INT NOT NULL,
  `app_role_id` INT NOT NULL,
  INDEX `fk_app_user_role_app_user_id_idx` (`app_user_id` ASC) VISIBLE,
  INDEX `fk_app_user_role_app_role_role_id_idx` (`app_role_id` ASC) VISIBLE,
  CONSTRAINT `fk_app_user_role_app_user_id`
    FOREIGN KEY (`app_user_id`)
    REFERENCES `amidst_our_own_selves_test`.`app_user` (`app_user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_app_user_role_app_role_role_id`
    FOREIGN KEY (`app_role_id`)
    REFERENCES `amidst_our_own_selves_test`.`app_role` (`app_role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`Player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`Player` (
  `playerId` INT NOT NULL AUTO_INCREMENT,
  `playerName` VARCHAR(45) NOT NULL,
  `isDead` TINYINT NOT NULL,
  `isImposter` TINYINT NOT NULL,
  `app_user_id` INT NULL,
  PRIMARY KEY (`playerId`),
  INDEX `fk_player_app_user_role_id_idx` (`app_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_player_app_user_role_id`
    FOREIGN KEY (`app_user_id`)
    REFERENCES `amidst_our_own_selves_test`.`app_user_role` (`app_user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`Room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`Room` (
  `roomId` INT NOT NULL,
  `roomName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`roomId`),
  UNIQUE INDEX `roomName_UNIQUE` (`roomName` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`Task` (
  `taskId` INT NOT NULL,
  `taskName` VARCHAR(45) NOT NULL,
  `roomId` INT NOT NULL,
  PRIMARY KEY (`taskId`),
  UNIQUE INDEX `taskName_UNIQUE` (`taskName` ASC) VISIBLE,
  INDEX `fk_task_room_id_idx` (`roomId` ASC) VISIBLE,
  CONSTRAINT `fk_task_room_id`
    FOREIGN KEY (`roomId`)
    REFERENCES `amidst_our_own_selves_test`.`Room` (`roomId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`Game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`Game` (
  `gameId` INT NOT NULL,
  `playerId` INT NOT NULL,
  `roomId` INT NOT NULL,
  INDEX `fk_game_room_id_idx` (`roomId` ASC) VISIBLE,
  INDEX `fk_game_player_id_idx` (`playerId` ASC) VISIBLE,
  CONSTRAINT `fk_game_player_id`
    FOREIGN KEY (`playerId`)
    REFERENCES `amidst_our_own_selves_test`.`Player` (`playerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_room_id`
    FOREIGN KEY (`roomId`)
    REFERENCES `amidst_our_own_selves_test`.`Room` (`roomId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `amidst_our_own_selves_test`.`Player_Assigned_Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amidst_our_own_selves_test`.`Player_Assigned_Task` (
  `task_id` INT NOT NULL,
  `playerId` INT NOT NULL,
  `isComplete` TINYINT NOT NULL,
  INDEX `fk_player_assigned_task_task_id_idx` (`task_id` ASC) VISIBLE,
  INDEX `fk_player_assigned_task_player_id_idx` (`playerId` ASC) VISIBLE,
  CONSTRAINT `fk_player_assigned_task_task_id`
    FOREIGN KEY (`task_id`)
    REFERENCES `amidst_our_own_selves_test`.`Task` (`taskId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_player_assigned_task_player_id`
    FOREIGN KEY (`playerId`)
    REFERENCES `amidst_our_own_selves_test`.`Player` (`playerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

delimiter //
create procedure set_known_good_state()
begin

-- delete from location;
--     alter table location auto_increment = 1;
--     delete from agency_agent;
-- 	delete from agency;
-- 	alter table agency auto_increment = 1;
--     delete from agent;
--     alter table agent auto_increment = 1;
delete from Player;
	alter table Player auto_increment = 1;
delete from app_user_role;
delete from app_role;
	alter table app_role auto_increment = 1;
delete from app_user;
	alter table app_user auto_increment = 1;

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

end //
-- 4. Change the statement terminator back to the original.
delimiter ;


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

select playerId, playerName, isDead, isImposter, app_user_id
from Player;