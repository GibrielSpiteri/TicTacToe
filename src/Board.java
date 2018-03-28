public class Board {  
   // length of the SIZE and columns
   public static final int SIZE = 3;
 
   Cell[][] cells;  // The board
   int currentRow, currentCol;  // current moves row and column
 
   /** Constructor  */
   public Board() {
      cells = new Cell[SIZE][SIZE]; // Create game board
      // loop through array and fill the board
      for (int row = 0; row < SIZE; row++) {
         for (int col = 0; col < SIZE; col++) {
            cells[row][col] = new Cell(row, col); 
         }
      }
   }
 
   /** Set the whole board to empty */
   public void startGame() {
      for (int row = 0; row < SIZE; row++) {
         for (int col = 0; col < SIZE; col++) {
            cells[row][col].clear();  // make every space EMPTY
         }
      }
   }
 
   /** Returns true if there are no more empty cells */
   public boolean draw() {
      for (int row = 0; row < SIZE; row++) {
         for (int col = 0; col < SIZE; col++) {
            if (cells[row][col].content == Space.EMPTY) {
               return false; // if there's still a move available return false
            }
         }
      }
      return true; // no more moves, it's a draw
   }
 
   /** Returns true if there is 3 in a row of a space type */
   public boolean winner(Space space) {
      return (cells[currentRow][0].content == space         // 3 in arow
                   && cells[currentRow][1].content == space
                   && cells[currentRow][2].content == space
              || cells[0][currentCol].content == space      // 3 in a column
                   && cells[1][currentCol].content == space
                   && cells[2][currentCol].content == space
              || cells[0][0].content == space               // 3 in a diagonal
                   && cells[1][1].content == space
                   && cells[2][2].content == space
              ||   cells[0][2].content == space 
                   && cells[1][1].content == space
                   && cells[2][0].content == space);
   }
 
   /** print the board */
   public void print() {
      for (int row = 0; row < SIZE; row++) {
         for (int col = 0; col < SIZE; col++) {
            cells[row][col].print(); // print every cell
            if (col < SIZE - 1) System.out.print("|"); // print divider
         }
         System.out.println();
         if (row < SIZE - 1) {
            System.out.println("-----------"); // print bottom divider
         }
      }
   }
}