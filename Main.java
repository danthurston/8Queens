import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JToolBar;
/*******************************************************
 * the main class of the program; where it is run from.
 *******************************************************/
public class Main{
    public static void main(String[] args) {
       setUI();                                         // calls method to setup the user interface
    }
    public static void setUI(){                         // method to set UI
        JFrame f = new JFrame("Chess");                 // new frame to house the UI
        JToolBar tools = ChessBoard.toolbar(f);         // create toolbar from 'chessboard' class
        ChessBoard chessBoard = new ChessBoard();       // create chessboard object

        f.add(tools,BorderLayout.PAGE_START);           // add toolbar to frame
        f.add(chessBoard);                              // add chessboard to frame
        f.pack();                                       // pack frame components
        f.setLocationRelativeTo(null);                  // ensure position
        f.setVisible(true);                             // set frame visible
    }
}