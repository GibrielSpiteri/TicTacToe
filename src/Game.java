import java.util.*;
public class Game {
	private Board board; // Game board
	private AlphaBeta ab = new AlphaBeta();// Alpha beta algorithm
	private State state; // Current game state
	private Space currentPlayer;// Current Player 
	private Scanner in = new Scanner(System.in); // Get user moves
	
	/** The constructor does all the work */
	public Game(){
		// Set up the game
		Scanner sc = new Scanner(System.in);
		System.out.println("Would you like to search a maximum depth? (y/n)");
		ab.choice = sc.next().toLowerCase();
		if(ab.choice.contains("y")){
			System.out.println("Enter your Maximum Depth (Recommended Depth of 2): ");
			ab.MAX_DEPTH = sc.nextInt();
		}
		//sc.close();
		
		board = new Board();
		board.startGame();
		currentPlayer = startingPlayer();
		state = State.IN_GAME;
		
		while(state == State.IN_GAME){
			// Get the next move and print the board
			nextMove(currentPlayer);
			board.print();
			
			// Change states if the game is over
			if(state == State.X_WIN){
				System.out.println("X is the Winner!");
			}
			else if(state == State.O_WIN){
				System.out.println("O is the Winner!");
			}
			else if(state == State.DRAW){
				System.out.println("The Game is a Draw!");
			}
			
			// Swap players
			if(currentPlayer == Space.X){
				currentPlayer = Space.O;
			}
			else{
				currentPlayer = Space.X;
			}
		}
		sc.close();
		in.close();
	}
	
	/**Get the next move on the board*/
	public void nextMove(Space currentPlayer){
		boolean check = false; // check that user input is valid
		// Loop until we have a proper move
		while(!check){
			// Get the Humans move
			if(currentPlayer == Space.X){
				System.out.println("Please enter your move, row[1-3] column[1-3]");
				int row = validate() - 1;
				int col = validate() - 1;
				
				// Check the numbers are on the board and the board space is free
				if(row >= 0 && row < Board.SIZE && 
						col >= 0 && col < Board.SIZE && 
						board.cells[row][col].content == Space.EMPTY) {
					
					board.cells[row][col].content = currentPlayer;
					board.currentRow = row;
					board.currentCol = col;
					check = true;
				}
				else{
					System.out.println("This move was not valid!");
				}
			}
			// Get Algorithms move
			else{
				// Output has {score, bestRow, bestCol}
				int[] output = ab.alphabeta(board, 0, currentPlayer, -100000, 100000);
				int row = output[1];
				int col = output[2];
				System.out.println("The computers best value was " + output[0] + " and moved to [" + row + ", " + col + "]");
				//Update board contents
				board.cells[row][col].content = currentPlayer;
				board.currentRow = row;
				board.currentCol = col; 
				check = true;
				
			}
		}
		
		// Update the state
		if(board.winner(currentPlayer)){
			if(currentPlayer == Space.X){
				state = State.X_WIN;
			}
			else{
				state = State.O_WIN;
			}
		}
		else if(board.draw()){
			state = State.DRAW;
		}
	}
	
	/**User input validation*/
	public int validate(){
		while(!in.hasNextInt()){
			System.out.println("Integers only");
			in.next();
		}
		return in.nextInt();
	}
	
	/** Randomly generate the starting player */
	public Space startingPlayer(){
		Random r = new Random();
		int num = r.nextInt(2);
		if(num == 0){
			return Space.X;
		}
		else{
			return Space.O;
		}
	}
	public static void main(String[] args){
		System.out.println("Hello");
		new Game();
	}
}
