 

import javax.swing.JToggleButton;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.ArrayList;
/**************************************************************************************
 * this is the back-end class, where the bulk of the leg-work is completed. It is
 * where the 8-Queens problem is solved using a backtracking algorithm and multiple
 * data structures. It inherits from ChessBoard to have access to the board and UI
 * components.
 **************************************************************************************/
@SuppressWarnings("serial")
public class Queen extends ChessBoard{
   private static Stack<JToggleButton> qStack = new Stack<JToggleButton>();                   // stack to hold the placements throughout backtracking
   private static Stack<JToggleButton> solvedStack = new Stack<JToggleButton>();              // stack to hold completed solutions
   private static ArrayList<Stack<JToggleButton>> stackList = new ArrayList<Stack<JToggleButton>>(); // arrayList to hold the completed stacks
   private static int clickCol;                                                // instance variable to hold the clicked column
     
   /***********************************************************************************
    * Initial method called from ChessBoard when tile is clicked. Is sent clicked
    * column and full location as JToggleButton to add to stack. From here, nextQueen
    * method is called to solve the problem based on this info. Note that this version
    * of iterate gets all the solutions into the stackList but only prints the first
    * solution. The second iterate method(below) is used to get each following soluion
    * from stackList.
    ***********************************************************************************/
   public static void iterate(JToggleButton q, int ii){             
     qStack.push(q);                                // add clicked queen location to stack     
     clickCol = ii;                            // assign the clicked column to clickCol (used to skip original column within 'nextQueen')
     if(nextQueen(0) == false){                     // if nextQueen 
         solvedStack = stackList.get(0);            // get the first stack from the arrayList
         setTotSol(stackList.size());               // set the total number of solutions var. For the UI to use, as well as testing.
         printResult(solvedStack);                  // print the first stack
     }
   }
   
   /******************************************************************************
    * This method gets the next solution from stackList and prints it. If it's the
    * last stack in the list it additionally calls printEnd from ChessBoard.
    ******************************************************************************/
   public static void iterate(){
      if(solCount <= (stackList.size() - 1)){        
          clearBoard();                             // clear board.. moved from 'nextButt' so as to not clear board if out of range.
          solvedStack = stackList.get(solCount);    // pass the next complete stack to solvedStack
          printResult(solvedStack);                 // print the stack          
          if (solCount == (stackList.size() - 1)){  // if this stack is the last stack then..
              printEnd();                           // call the printEnd method from ChessBoard to disable 'Next Solution' button
          }
      }
   }
   
   /******************************************************************************
    * resets the ArrayList (stackList) and Stack (qStack) to empty when starting
    * a new game.
    ******************************************************************************/
    public static void resetData(){
       stackList.clear();
       qStack = new Stack<JToggleButton>();
   }
    
   /******************************************************************************
    * Method to print all queens to the board once the solution has been found.
    * Iterates through the stack, removing one JToggleButton at a time and places
    * the queen image at that location.
    ******************************************************************************/
   public static void printResult(Stack<JToggleButton> queenStack){
       while (!queenStack.isEmpty()){                 // while the stack isn't empty..
            try{                                      // try and..
                JToggleButton toQ = queenStack.pop(); // remove most recent from stack and assign to toQ          
                queenIMG(toQ);                        // place queen image on the board at location toQ
            }catch (EmptyStackException e){
                System.out.println(e.toString());     // catch empty stack error
            }
       }   
   }
   
   /*******************************************************************************
    * The recursive backtracking alrorithm. Where the main part of the puzzle is 
    * solved. The algorithm calls itself repeatedly until a solution is found, 
    * meaning a stack is full which is added to the ArrayList. The algorithm then 
    * backtracks to find the next solution until all possible permutations have
    * been tried.
    *******************************************************************************/ 
   @SuppressWarnings("unchecked")
   public static boolean nextQueen(int col){
      if ((col > 7) || (col > 6 && clickCol == 7)){            // if col is more than 7 then..          
          stackList.add((Stack<JToggleButton>)qStack.clone()); // add a clone (specified type) of stack to ArrayList
          return true;                                         // return true as end of board has been reached so solution must be found
      }
      for (int y = 0; y < 8; y++){                     // iterates through each row up to 7, of current col
          if(col == clickCol && col == 7){             // if col is the column clicked and is the last column then return true/complete
              return true;                             // return true to indicate the solution is found
          }else if(col == clickCol){                   // else if col is the column clicked originally then..
              col += 1;                                // skip this col
          }
          if (canMove(col, y)){                        // checks each row of current column to see if can place queen
              JToggleButton toQ = tilesButt[col][y];   // if true then adds tile location to JToggleButton
              qStack.push(toQ);                        // adds JToggleButton to stack
              nextQueen(col + 1);                      // recursive function call
              if(!qStack.isEmpty()){                   // otherwise if no solution found and stack not empty then..
                  try{                                 // try..
                      qStack.pop();                    // remove most recent from stack (backtrack)
                  }catch (EmptyStackException e){
                      System.out.println(e.toString());//catch empty stack error
                  }
              }
          }  
      }
      return false;
   }
   
   /******************************************************************************************
    * Method to check whether a queen can be placed in any given location on the grid without
    * conflicting with another queen on the board. Checks each possible location the queen
    * could 'attack'. Converts location into a JToggleButton and checks to see if a button
    * with matching location is in the stack at each stage.
    ******************************************************************************************/
   public static boolean canMove(int col, int row){
       JToggleButton tempQ = tilesButt[0][0];     // button to hold location currently being checked
       int x, y;                                  // instance variables       
       // check row
       for (x = 0; x < 8; x++){                   // loops through all 8 tiles
            tempQ = tilesButt[x][row];            // iterates along x whilst keeping y stationary
            if (qStack.contains(tempQ)){          // if current location is already taken by a queen in the stack then..
                 return false;                    // return false as queen found
            }
       }       
       //check lower left diagonal
       for (x = col, y = row; x>=0 && y>=0; x--, y--){
           tempQ = tilesButt[x][y];
           if (qStack.contains(tempQ)){           // if current location is already taken by a queen in the stack then..
                 return false;                    // return false as queen found
           }
       }      
       //check upper right diagonal
       for (x = col, y = row; x<8 && y<8; x++, y++){
           tempQ = tilesButt[x][y];
           if (qStack.contains(tempQ)){           // if current location is already taken by a queen in the stack
                 return false;                    // return false as queen found
            }
       }
       // check lower right diagonal
       for (x = col, y = row; x<8 && y>=0; x++, y--){
           tempQ = tilesButt[x][y];
           if (qStack.contains(tempQ)){           // if current location is already taken by a queen in the stack
                 return false;                    // return false as queen found
            }
       }
       // check upper left diagonal
       for (x = col, y = row; x>=0 && y<8; x--, y++){
           tempQ = tilesButt[x][y];
           if (qStack.contains(tempQ)){           // if current location is already taken by a queen in the stack
                 return false;                    // return false as queen found
            }
       }        
       return true;                               //return true to signify the position is suitable for placement
   }
}
    /* Below are the testing methods used originally to illuminate every move the
     * queen could make:
     
    // set the vertical and horizontal squares the queen can move to
    public static void setVH(JToggleButton q, int col, int row){
       JToggleButton tempQ = tilesButt[0][0];
       // colour horizontal and vertical tiles:        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            tempQ = tilesButt[x][y];
             if (q == tempQ){}else if(x == col || y == row) {
                tempQ.setEnabled(false);
            }
        }
       }
    }
    // set the diagonals that the queen can move to
    public static void setDi(int col, int row){
       // colour diagonal tiles:       
       int iColLR, iColLL, iColTL, iColTR;
       iColLR = iColLL = iColTL = iColTR = col;
       int iRowLR, iRowLL, iRowTL, iRowTR;
       iRowLR = iRowLL = iRowTL = iRowTR = row;       
       for (int x = 0; x < 8; x++) {
           iColLR++; iRowLR--; diagonalCheck(iColLR, iRowLR);
           iColLL--; iRowLL--; diagonalCheck(iColLL, iRowLL);
           iColTL++; iRowTL++; diagonalCheck(iColTL, iRowTL);
           iColTR--; iRowTR++; diagonalCheck(iColTR, iRowTR);
       }
    }
    // check if the diagonal square is within the boundaries of the board
    public static void diagonalCheck(int col, int row){ 
        JToggleButton toRed = tilesButt[0][0];        
        if((col >= 0 && col <8) && (row >= 0 && row <8)){           
           toRed = tilesButt[col][row];
           toRed.setEnabled(false);
        }
    }
} */ 