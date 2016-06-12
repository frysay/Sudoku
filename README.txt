Sudoku - Readme

This app is a Sudoku RESTFull web service that can validate moves and recognize when puzzle has been solved and the game is over.
There is also a future to check for possible moves that don't violate Sudoku constraints but that could lead to a deadlock game where after
that move it's not possible to find a solution to the puzzle.
Only the backend part has been implemented; so there is no Custom Response that adapts the Sudoku table to a more user-friendly visualization.
The implementation has been developed with Spring using a Maven build.

How to run it:
1 - If you download just the sudoku-0.0.1-SNAPSHOT.jar:
	1.1 - you can run the web service from a cmd prompt as "java -jar sudoku-0.0.1-SNAPSHOT.jar"
2 - If you dowload the full projet:
	2.1 - you can use the run.bat in the Sudoku main folder to automatically start the web service
	2.2 - you can build the project either with "run as -> Spring Boot App" or building it as "run as -> maven build (as goals -> clean install)"
		  and the run the web service as at the point 1.1

To access the web service you can use the link http://localhost:8080/... with one of the following requests:
1 - /sudoku: it returns always the table with the update moves and a flag, isOver, that tells you if you filled all the missing spots. In order
	         to add a new move to the table you have to pass 3 parameters:
	p0. value: the value to put in the table
	p1. row: the row where to put it
	p2. column: the column where to put it
	Note: if one of this is not passed or it's not in the domain of sudoku values (1-9) then the table return is the same one of before without
		  any changes.

	Errors:
	a. SudokuMoveNotAllowedException: it means there is a constraint violation in the move you want to do or youa re trying to update a cell that
	   has already a value in it
	b. SudokuDeadLockGameException: it's thrown when the last move done is allowed but it leads to a situation where it will be impossible to win
	Note: the HttpStatus used for these 2 Exceptions are just for fun; of course this is not the proper use of them.

2 - /sudoku/revert: this request let's you revert your last move in order to change it; it's suggested when you encounter the SudokuDeadLockGameException
	                and it's the only way to change your move.
3 - /sudoku/new: you got bored by this game or you want to try new ways to finish it? Just reset it and start again!

Final notes: zero is used to mark the spots that are yet to be filled. It could be easily used the null value with few small changes according with this choice.
	



