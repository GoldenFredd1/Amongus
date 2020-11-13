use amidst_our_own_selves;

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
(1,'HELPME',1, 1),
(2,'HELPME', 2, 2),
(3,'HELPME', 3, 6),
(4,'HELPME', 4, 3);

insert into Player_Assigned_Task values
(1, 1, false),
(8, 1, false),
(4, 2, false),
(10, 2, false),
(2, 3, false),
(8, 3, false),
(3, 4, false),
(7, 4, false);

select p.playerName, t.taskName, g.gameRoomCode
from Task t
join Player_Assigned_Task pat on  pat.taskId  = t.taskId
join  Player p on p.playerId = pat.playerId
join Game g on g.playerId = p.playerId
where g.gameRoomCode = 'HELPME';

Select pat.taskId, pat.playerId, pat.isComplete
from Player_Assigned_Task pat
join  Player p on p.playerId = pat.playerId 
join Game g on g.playerId = p.playerId 
where g.gameRoomCode = 'HELPME' and taskId = 1;

Select pat.taskId, pat.playerId, pat.isComplete
from Player_Assigned_Task pat
join  Player p on p.playerId = pat.playerId 
join Game g on g.playerId = p.playerId 
 where g.gameRoomCode = 'HELPME' and p.playerId =1;