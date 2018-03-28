# TicTacToe

VIEW IN RAW TEXT FORM

This is an Object Oriented Tic Tac Toe game that pits a human versus an AI player.
The AI uses the Minimax Algortihm with Alpha Beta Pruning to evaluate the board and decide upon the best move.

When the game is run, you have the choice to enter a maximum search depth for the AI,
if no depth is chosen the AI uses the evaluateBoard() function, located in the AlphaBeta file, otherwise you will choose a Max Depth
and the AI will use the evaluate() function.

The evaluateBoard() function runs each possible board state until completion an awards the play with 10 for an AI win, -10 for a player win, and 0 for a draw. The AI does not play the best it possibly can with this function which is why I implement the 2nd scoring system.

evaluate() checks the eight possible winning lines/diagonals and awards points for every space occupied by the AI or player.
If the AI has one space occupied in a winning line it recieves 1 point, for two owned spaces it recieves 10 points, and for 3 owned spaces it recieves 100 points. For any spaces owned by the player a negative score is given. If there are empty spaces no points are awarded and if win is blocked by the player, and vice versa, a score of 0 is returned.
The scores for each line are then added together and the algorithm chooses the move that awards the highest/lowest amount of points, dependent on its maximize and minimize cycle.

From my experimentation it appears when using this scoring system and maximum depth from 1 to 3 results in an unbeatable AI. However, I do notice there is slight problem with the system where the AI will delay a win if it can choose a space that gives it another winning move. Here is one such example:
The computers best value was 1 and moved to [2, 2]
   |   |   
-----------
   | O |   
-----------
   |   |   
Please enter your move, row[1-3] column[1-3]
1 1
 X |   |   
-----------
   | O |   
-----------
   |   |   
The computers best value was 0 and moved to [1, 2]
 X | O |   
-----------
   | O |   
-----------
   |   |   
Please enter your move, row[1-3] column[1-3]
3 1
 X | O |   
-----------
   | O |   
-----------
 X |   |   
The computers best value was 0 and moved to [2, 1]
 X | O |   
-----------
 O | O |   
-----------
 X |   |   
 
 As you can see the AI decides to block column 1 delaying its win on column 2, this results in the AI recieving at least one guarenteed winning move on the next turn. I don't think it makes this move to simply taunt the player (Although that would be funny reason), but because the algorithm is considering the players move after it already recieves the win, as it isn't recognizing that the game has ended, and wants to block the players move to secure more points. As you can see in this search expansion:
 Score 18
 X | O | X 
-----------
 O | O |   
-----------
 X |   |   
Score 8
 X | O |   
-----------
 O | O | X 
-----------
 X |   |   
Score 0
 X | O |   
-----------
 O | O |   
-----------
 X | X |   
Score 9
 X | O |   
-----------
 O | O |   
-----------
 X |   | X 
 
 VS
 
 Score 0
 X | O |   
-----------
 X | O |   
-----------
 X | O |   
 
 The computer only cares about how many possibilities it has to win, choosing the later would result in only one win state. Choosing the first results in multiple win states.
