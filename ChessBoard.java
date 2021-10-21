 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
/*****************************************************************************
 * This is the UI class. It handles all (or as much as possible) of the
 * front-end implementation. It inherits from the JPanel class.
 *****************************************************************************/
 @SuppressWarnings("serial")
public class ChessBoard extends JPanel{
    private static JLabel message = new JLabel("Ready to play!");
    private static JButton nextButt = new JButton("Next Solution");    
    private static JToggleButton clickedButt;
    private static int totalSolutions = 0;
    public static int solCount = 1;
    public static JToggleButton[][] tilesButt = new JToggleButton[8][8];
    /**********************************************************************
     * creates the toolbar for the UI.
     **********************************************************************/
    public static JToolBar toolbar(JFrame frame){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JToolBar toolbar = new JToolBar();                   // toolbar constructor
        toolbar.setRollover(true);                           // turn on the rollover visual effect
        toolbar.addSeparator();                          // separate toolbar content
        JButton newGameButt = new JButton("New Game");       // construct 'New Game' JButton
        toolbar.add(newGameButt);                            // add button to toolbar
        toolbar.addSeparator();                          // separate toolbar content        
        toolbar.add(nextButt);                               // add button to toolbar (defined as an instance variable)
        nextButt.setEnabled(false);                          // disable nextButt until user clicks board
        toolbar.addSeparator();                          // separate toolbar content
        toolbar.add(message);                                // add JLabel message to toolbar
        
        Container contentPane = frame.getContentPane();
        contentPane.add(toolbar, BorderLayout.NORTH);
        frame.setSize(screenSize.width, 150);
        toolbar.setFloatable(false);
        
        newGameButt.addActionListener( new ActionListener() {// add action listener
        @Override                                            // override other buttons(/methods with same name)
              public void actionPerformed(ActionEvent e) {
              clearBoard();                                  // revert board to original
              Queen.resetData();                             // reset stack and list to null
              nextButt.setEnabled(false);                    // re-disable next solution button
              clickedButt.setEnabled(true);                  // re-enable clicked button
              solCount = 1;                                  // reset solCount
              setMSG("Ready to Play!");                      // set UI message
        }
        });
        
        nextButt.addActionListener( new ActionListener() {   // add action listener
        @Override                                            // override other buttons(/methods with same name)
              public void actionPerformed(ActionEvent e) {
              Queen.iterate();                               // get and print next solution in Queen.
              solCount++;                                    // increment solCount
              setMSG("Solution " + solCount + "/" + totalSolutions); // set UI message
        }
        });        
        return toolbar;                                      // return the toolbar to main to be placed into frame
    }
    
    /*************************************************
     * Setter method: For 'totalSolutions' variable.
     *************************************************/
    public static void setTotSol(int x){
        totalSolutions = x;                                      // set totalSolutions as provided int
    }
    
    /************************************************************
     * Setter method: Alters message at top of UI.
     ************************************************************/
    public static void setMSG(String x){
        message.setText(x);                                      // set message as provided string
    }
    
    /************************************************************
     * Method called when every solution has been displayed.
     ************************************************************/    
    public static void printEnd(){
        setMSG("Solution " + (solCount) + "/" + totalSolutions); // print current/total solutions
        nextButt.setEnabled(false);                              // disable nextButt so currently displayed solution isn't cleared
    }
    
    /**************************************************************
     * Method that clears the board of queen images and selections.
     * Does not enable the clicked button to retain its colour.
     **************************************************************/
    public static void clearBoard(){
        for (int x = 0; x < 8; x++) {                             // loop over board,
            for (int y = 0; y < 8; y++) {						  
                JToggleButton temp = tilesButt[x][y];			  // each button..
                temp.setIcon(null);								  // remove icon
                temp.setSelected(false);						  // removes selections
                if(temp != clickedButt){						  // unless the clicked button; enable
                    temp.setEnabled(true);
                }
            }
        }
    }
    
    /********************************************************************************
     * ChessBoard object. This is the checkered grid of the Chessboard, created from
     * 8x8 grid of JToggleButtons that the user can interact with.
     ********************************************************************************/
    public ChessBoard() {                          // constructor
        Dimension dims = new Dimension(64, 64);    // instantize dimension for tiles
        setLayout(new GridLayout(8, 8));           // set the board layout
        for (int x = 0; x < 8; x++) {              // loop through the 8 x 8 grid..
            for (int y = 0; y < 8; y++) {
                JToggleButton b = new JToggleButton(); // button to add to each tile
                b.setPreferredSize(dims);          // set dimension for tiles
                b.setMinimumSize(dims);            // set minimum dimension for tiles            
                if ((x + y + 1) % 2 == 0) {        // set the checkered colours for the board
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                add(b);                            // add button
                tilesButt[x][y] = b;               // set location
                final int i = x;                   // column number (to send to Queen)
                
                b.addActionListener( new ActionListener() {     // adds listener to each button
                    @Override
                    public void actionPerformed(ActionEvent e) {// when clicked..
                        clickedButt = b;           // set clicked button variable
                        Queen.iterate(b, i);       // calls the iterate method from the Queen class to begin the algorithm
                        queenIMG(b);               // add queen image to board
                        b.setEnabled(false);       // essentially highlighting the image through the iterations
                        nextButt.setEnabled(true); // enables the 'next solution' button
                        setMSG("Solution " + solCount + "/" + totalSolutions); // set UI message
                   }
                });
            }        
        }
    }
    
    /******************************************************************************
     * method to import and set the queen image. First gets the image from location,
     * scales it down to fit inside the tile (64 x 64) and assigns to an
     * ImageIcon. It then sets this image at inside the required button.
     ******************************************************************************/ 
    public static void queenIMG(JToggleButton q){ // method to import image
           try {
            final Image queenBuff = ImageIO.read(new File("queenIMG.png"));
            Image queenImage = queenBuff.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH);
            final ImageIcon queenIcon = new ImageIcon(queenImage);
            q.setIcon(queenIcon);
             }catch (IOException e){
            System.out.println(e.toString());
            System.out.println("Could not find file 'queenIMG.png'");
           }
    }
}