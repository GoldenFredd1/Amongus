Plan

[x]Make a github repo - 15 minutes. Actual: 3 minutes

[X]Wire frame UI, using MockPlus- 1 - 2 hours . Actual:1.5 hours - MockPlus kept giving us problems...

Database
[X]Database Schema - 1-2 hours
[]Create tables - 30 minutes
[]Populate tables with data - 1 hour
	Task needs to be populated with a task
	Room needs to be populated with room names and ID

Maven Project
[X]Make a Maven project (push it to git) - 15 minutes

Models
1 hour - this includes adding validation annotation
	[x]Player
		playerId
		playerName
        isDead
		isImposter
	[]Task
		taskId
		taskName
		roomId
	[]Room
		roomId
		roomName
	[]Game 
		gameId
		List<player>
		List<room>	
	[]PlayerAssignedTask
		taskId
		playerId
		isComplete
		
Data  
	[]Player  2 - 4 hours
		[]findAll
			test:
			[]did you find all?
		[]findById
			test:
			[]Can you find an existing ID?
			[]Non Existing ID?
		[]findByIsDead
			test:
			[]can you find all that are dead?
		[]findByisImposter
			test:
			[]find imposter
		[]add
			Test:
			[]can you add?
		[]edit/update
			Test:
			[]can you update?
		[]delete
			Test:
			[]can you delete?
	[]Task 1 -2 hours
		[]findAll
			Test:
			[]did you find all?
		[]findById
			test:
			[]Can you find an existing ID?
			[]Non Existing ID?
		[]findByRoomId
			Test:	
			[]Can you find an existing RoomId?
			[]Non existing room?
	[]Room 1 -2 hours
		[]findAll
			Test:
			[]did you find all?
		[]findByRoomId
			Test:
			[]Can you find an existing RoomId?
			[]Non existing room?
		[]findByRoomName
            Test:
			[]Can you find an existing room?
			[]Non existing room?
	[]Game 2 - 4 hours
		[]findAll
            Test:
			[]did you find all?
		[]findByGameId
            Test:
			[]Can you find an existing gameId?
			[]Non existing gameId?
		[]add
            Test:
			[]can you add?
		[]update(need to update the roomId after every round)
            Test:
			[]can you update?
	[]PlayerAssignedTask 2 - 4 hours
		[]findAll
            Test:
			[]did you find all?
		[]findByTaskId
            Test:
			[]Can you find an existing taskId?
			[]Non existing taskId?
		[]findByPlayerId
            Test:
			[]Can you find an existing playerId?
			[]Non existing playerId?
		[]updateIsComplete
			Test:
			[]can you update?

Domain
 6-8 hours
    []Player
        Test:
        []Should find no errors for a valid player
        []should find error in negative player ID
        []should not have a null name
        []should not have a null impostor boolean
        []should not have a null dead boolean
	[]Task
		Test:
		[]should find no errors for a valid task
		[]should not have a negative task ID
		[]should not have a null name
	[]Room
		Test:
		[]should find no errors for a valid room
		[]should not have a negative room ID
		[]should not have a null name
	[]Game
		Test:
		[]should find no errors for a valid game
		[]should not have a negative game ID
	[]PlayerAssignedTask
		Test:
		[]should find no errors for a valid playerAssignedTask
		[]should not have a null isComplete boolean
		
Controllers 3-5 hours
    []Player
	[]Task
	[]Room
	[]Game
	[]PlayerAssignedTask

[]Security - JWT 3-5 hours

Client
    []Angular UI - Several days....

[]Style - BootStrap 2 hours

Time Goals:
    [] Have a basic CRUD setup by friday - 13th November