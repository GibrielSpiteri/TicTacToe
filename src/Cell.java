
public class Cell {
   Space content; // enum that has possible space states, EMPTY, X, O
   int row, col; // row and column of this cell
 
   /** Constructor to initialize this cell */
   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      clear();  // clear content
   }
 
   /** Clear the cell content to EMPTY */
   public void clear() {
      content = Space.EMPTY;
   }
 
   /** Print itself */
   public void print() {
      switch (content) {
         case X:  System.out.print(" X "); break;
         case O: System.out.print(" O "); break;
         case EMPTY:  System.out.print("   "); break;
      }
   }
}
