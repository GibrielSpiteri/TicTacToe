import java.util.*;
public class AlphaBeta {
	public static final int MAX = -1000, MIN = -1000;
	public int MAX_DEPTH = 100;        // After multiple tests it appears 
									   // a maximum depth search of 2 results in an unbeatable AI
	public String choice; // choose how to score our moves
	
	public AlphaBeta(){

	}
	/**Basic search values
	 * Return 10 if the player wins the game
	 * Return -10 if the computer wins the game
	 * Return 0 if no one wins*/
	public static int evaluateBoard(Board board, Space player){
		if(board.winner(Space.O)){
			return 10; // 10 points for the computer
		}
		else if(board.winner(Space.X)){
			return -10; // -10 for the player
		}

		else{
			return 0; // 0 for anything else
		}
		/* Ways to make this better?:
		 * Implement a heuristic that awards or takes away points for the 
		 * each cell in a row that the computer or player owns
		 * 1 point for one cell, 10 point for 2 cells in a row, 100 points for 3 in a row
		 * We could also add or subtract points when the AI or player owns valuable squares
		 * such as in the center, or corners
		 * */
	}
	
	/**Evaluate win possibilities, the more possibilities for a win, the higher the score
	 * The AI will ignore the players possible win if it can put a move in a space that will give 
	 * it more winning moves*/
	public static int evaluate(Board board, Space player){

			int score = 0;
			score += evaluateWin(0, 0, 0, 1, 0, 2, board); // first row
			score += evaluateWin(1, 0, 1, 1, 1, 2, board); // second row
			score += evaluateWin(2, 0, 2, 1, 2, 2, board); // third row
			score += evaluateWin(0, 0, 1, 0, 2, 0, board); // first column
			score += evaluateWin(0, 1, 1, 1, 2, 1, board); // second column
			score += evaluateWin(0, 2, 1, 2, 2, 2, board); // third column
			score += evaluateWin(0, 0, 1, 1, 2, 2, board); // diagonal
			score += evaluateWin(0, 2, 1, 1, 2, 0, board); // second diagonal
			//score += evaluateBoard(board, player);
			//System.out.println("Score " + score);
			return score;
	}
	
	/**Returns +/- 1 point for one cell occupied by O or X
	 * Returns +/- 10 points for two cells occupied by O or X
	 * Returns +/- 100 points for all three cells with O or X */
	public static int evaluateWin(int row1, int col1, int row2, int col2, int row3, int col3, Board board){
		int score = 0; // Basic value of 0
		
		// If first cell has O or X award 1 point
		if(board.cells[row1][col1].content == Space.O){
			score = 1;
		}
		else if(board.cells[row1][col1].content == Space.X){
			score = -1;
		}
		
		// 10 points if the previous cell also had an O
		if(board.cells[row2][col2].content == Space.O){
			if(score == 1){ // Neighboring O cell
				score = 10;
			}
			else if(score == -1){ // Opponent cell
				return 0; // A win by this combo is blocked, just return 0
			}
			else{ // Empty cell
				score = 1;
			}
		}
		// -10 points if the previous cell also had an X
		else if(board.cells[row2][col2].content == Space.X){
			if(score == -1){ // adjacent cell is X
				score = -10;
			}
			else if (score == 1){ // adjacent cell is O
				return 0;
			}
			else{ // next to empty cell
				score = -1;
			}
		}
		
		// 100 points or 10 points depending on the previous cells also having O's
		if(board.cells[row3][col3].content == Space.O){
			if(score > 0){ // adjacent cells are O
				score *= 10;
				//When we evaluate the corner spaces the computer ignores the player when 
				//We take side spaces and loses
				/*score += bestSpaces(row1, col1);
				score += bestSpaces(row2, col2);
				score += bestSpaces(row3, col3);*/
			}
			else if(score < 0){ // adjacent cells are X
				return 0;
			}
			else{ // Empty cell
				score = 1;
			}
		}
		else if(board.cells[row3][col3].content == Space.X){
			if(score < 0){ // adjacent cells are X
				score *= 10;
				/*score -= bestSpaces(row1, col1);
				score -= bestSpaces(row2, col2);
				score -= bestSpaces(row3, col3);*/
			}
			else if (score > 0){ // adjacent cells are O
				return 0;
			}
			else{ // adjacent cells are empty
				score = -1;
			}
		}		
		//System.out.println("Score " + score);
		return score;
	}
	
	/** Return a score of 50 if a move is on the corner or middle spaces*/
	public static int bestSpaces(int row, int col){
		if(     (row == 0 && col == 0) || (row == 0 && col == 3) || 
						 (row == 2 && col == 2) || 
				(row == 3 && col == 1) || (row == 3 && col == 3)){
			
			return 50;
		}
		else{
			return 0;
			}
	}
	/**Fills the list with the location of every empty cell*/
	public List<int[]> generateMoves(Board board, Space player){
		List<int[]> moves = new ArrayList<int[]>(); // make an arraylist
		//If the game is over return an empty array
		if(board.winner(player)){
			return moves;
		}
		
		for(int row = 0; row < Board.SIZE; row++){
			for(int col = 0; col < Board.SIZE; col++){
				if(board.cells[row][col].content == Space.EMPTY){
					moves.add(new int[] {row, col}); // add each row and column that is empty
				}
			}
		}
		return moves;
	}
	
	/**The AlphaBeta algorithm recursively searches through every empty board space to check for
	 * possible moves that would result in its win. */
	public int[] alphabeta(Board board, int depth, Space player, int alpha, int beta){
		List<int[]> moves = generateMoves(board, player); // List of empty board spaces
		//System.out.println("" + moves.get(1)[1]);
		// Default values
		int score, bestRow = -1, bestCol = -1;
		
		// Game over or depth reached
		if(moves.isEmpty() || depth == MAX_DEPTH){
			// Change heuristic type here
			if(choice.contains("y")){
				score = evaluate(board, player); // Evaluate the board state based on our heuristic
				return new int[] {score, bestRow, bestCol}; // return the move
			}
			else{
				score = evaluateBoard(board,player);
				return new int[] {score, bestRow, bestCol};
			}
		}
			
		else{
			// Loop through every empty board space
			for(int[] move: moves){
				//System.out.println(move[0] + " " + move[1]);
				
				// Test a move
				board.cells[move[0]][move[1]].content = player;
				
				// Maximize our value
				if(player == Space.O){
					score = alphabeta(board, depth+1, Space.X, alpha, beta)[0]; // Recursively call the function again, 
																				// Swapping the player and increasing the depth
																			    // Get the score value
					if(score > alpha){ // If the score is better than our current alpha, save it
						//System.out.println("Score " + score+ " Better than alpha " +alpha + " Depth " +depth + "Best Move " + move[0] + " " + move[1]);
						//board.print();
						alpha = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				}
				
				// Minimize our value
				else{
					score = alphabeta(board, depth+1, Space.O, alpha, beta)[0]; // Do the same as before
					
					if(score < beta){ // swap beta if the score is less than our current beta
						//System.out.println("Score " + score+ " Better than beta " +beta + " Depth " + depth + "Best Move " + move[0] + " " + move[1]);
						//board.print();
						beta = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				}
				
				// Undo the change
				board.cells[move[0]][move[1]].content = Space.EMPTY;
				
				// Prune the tree
				if(beta <= alpha){
					break;
				}
			}
			// Return the result
			if(player == Space.O){
				return new int[] {alpha, bestRow, bestCol};
			}
			else{
				return new int[] {beta, bestRow, bestCol};
			}
		}
	}
}
