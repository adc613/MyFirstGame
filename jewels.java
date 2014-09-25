import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JFrame;
import java.applet.Applet;
import javax.swing.JButton;
import java.util.Random;
import java.lang.Object;
import java.lang.Integer;
import javax.swing.border.*;

/**This class represents a working game similar to Jewel Quest
  * @author Adam Collins
  */ 
public class Jewels extends JFrame implements ActionListener{
  
  private JPanel board;                       //saves  instance of the JPanel board that is used to hold the JButtons inside a grid
  private GridLayout grid;                    //used to create a grid layout that can be put inside of JPanel, so that the JButtons can be put in a grid
  private JFrame frame;                       //represents the interaction window that displaces the JPanel with the JButtons inside of it
  private JButton[][] button;                 //an array that represents every button, and therefore every jewels inside of JPanel
  private int numJewels;                      //stores the number of jeweles being used on the board
  private int countColumns;                   //used to count how many jewels there are in a row, horizontally
  private int countRows;                      //used to count how many jewels are in a row, verticallly
  private int countRight;                     //counts how many jewels to the right of a jewel with the same color exist
  private int countLeft;                      //counts how many jewels to the left of a left with the same color exist
  private int countUp;                        //counts how many jewels are below the jewels exist with the same color
  private int countDown;                      //counts hor many jewels above the jewel exist with the same color
  private boolean goodMove;                   //Used to check and see check if a move that give the user 3 jewels in a row
  private boolean possibleMove;               //used to check to see if the button being moved is adajcent
  private int dontTouch;                      //used to hold the integer of the column that shouldn't be adjusted by the dropRow method
  private boolean avoidColumn;                //used for an if statment in dropRow that determines if it's necesary to skip a column
  private String saveText;                    //saves the text inside a JButton to convert it back once it's clicked
  private boolean victory = false;            //a boolean that decides if a game is won
  private JButton button1 = new JButton();    //stores the button that the user clicks first
  private JButton button2 = new JButton();    //stores the button that the user clicks second
  private boolean clickCount = true;          //used to determine whether it's the first or second click
  private final int rows;                     //stores how many rows are used for the game
  private final int columns;                  //stores how many columns are used for the game                  
  
  /**Take no inputs and creates an 8 by 10 grid with 4 different colors of Jewels
    */
  public Jewels(){
    rows = 8;
    columns = 10;
    numJewels = 4;
    frame = new JFrame();
    grid = new GridLayout(rows, columns);
    board = new JPanel(grid);
    frame.getContentPane().add(board, "Center");
    button = new JButton[rows][columns];
    this.declareButtons();
    this.randomColors();
    this.addButtons();
  }
  
  /**Allows for a custom grid with a size selected by the user and d with a specific number of different jewels
    * @param rows represents how many rows are on the grid
    * @param columns represents how many columns are on the grid
    * @param numJewels represents how many different Jewels are on the grid
    */
  public Jewels(int rows, int columns, int numJewels){
    this.rows = rows;
    this.columns = columns;
    this.numJewels = numJewels;
    frame = new JFrame();
    grid = new GridLayout(rows, columns);
    board = new JPanel(grid);
    frame.getContentPane().add(board, "Center");
    button = new JButton[rows][columns];
    this.declareButtons();
    this.addButtons();
    this.randomColors();
  }
  
  /**Declares all JButtons in the array as a new JButton(), and adds actionListener to each Jbutton
    */
  public void declareButtons(){
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < columns; j++){
        button[i][j] = new JButton();
        button[i][j].addActionListener(this);
      }
    }
  } 
    
  /**Puts all buttons in the array inside the JPanel
    */ 
  public void addButtons(){
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < columns; j++){
        int spot = 1;
        board.add(button[i][j]);
      }
    }
  }
  
  /**Gives every JButton a random color
    */
  public void randomColors(){
    Random randomNumber = new Random();
    int color;    
    
    for(int i = 0; i < rows; i++){                                 //loop goes through each array and selects a random color depending on what numJewels equals
      for(int j = 0; j < columns; j++){
         color =(int)(randomNumber.nextDouble() * numJewels);
         button[i][j].setOpaque(true);
         switch (color){
           case 0:
             button[i][j].setBackground(Color.BLUE);
             break;
           case 1:
             button[i][j].setBackground(Color.GREEN);
             break;
           case 2:
             button[i][j].setBackground(Color.RED);
             break;
           case 3:
             button[i][j].setBackground(Color.BLACK);
             break;
           case 4:
             button[i][j].setBackground(Color.RED);
             break;
           case 5:
             button[i][j].setBackground(Color.CYAN);
             break;
           case 6:
             button[i][j].setBackground(Color.MAGENTA);
             break;
           case 7:
             button[i][j].setBackground(Color.ORANGE);
             break;
           default:
             button[i][j].setBackground(Color.YELLOW);
             break;
         }                              
      }
    }
  }
  
  /**Takes two buttons and switches their colors
    * @author <Adam Collins>
    * @param button1 is the first button
    * @param button2 is the second button
    */ 
  public void switchButtons(JButton button1, JButton button2){
    JButton button3 = new JButton();
   
    button3.setBackground(button2.getBackground());
    button2.setBackground(button1.getBackground());
    button1.setBackground(button3.getBackground());
  }
  
   /**Returns the first index of an array of JButtons, this index represents the row
     * that the JButton is crurrently in, the first row being 0, and as the number row
     * higher the button row gets lower in the JFrame
     * @author <Adam Collins>
     * @param button the button which row needs to be determined
     */
 public int getRow(JButton button){
   for(int i = 0; i < rows; i++){
     for(int j = 0; j < columns; j++){
       if(button == this.button[i][j])    //goes through each JButton in the array until it finds the one that matches button
         return i;
     }
   }
   return 0;
 }
     
  
  /**Returns the second index of an array of JButtons, this index represents the column
     * that the JButton is crurrently in, the first row being 0, and as the number column
     * higher the button row gets lower in the JFrame
     * @param button the button which column needs to be determined
     */
  public int getColumn(JButton button){     
   for(int i = 0; i < rows; i++){
     for(int j = 0; j < columns; j++){      //goes through each JButton in the array until it finds the one that matches button
       if(button == this.button[i][j])
         return j;
     }
   }
   return 0;
  }
  
  /**Checks to see if the button in the given row and column has a 3 in a row combination of the same color in a
    * horizontal direction or a vertical direction. If there's is a three in a row combination
    * the text inside the JButton is set to *, but only in the direction(s) that has a three in
    * a row or greater combination, and only for the series of consecutive colors.
    * @param row is the row of the JButton being investigated
    * @param column is the colun of the JButton being investigated
    */
  public void compare(int row, int column){
    this.countRows = 1;
    this.countColumns = 1;
    
    //this series of if statments checks for any posible vertical 3 in a row combination
    if(row + 1 < rows && button[row][column].getBackground() == button[row + 1][column].getBackground()){     
      countRows++;                                         
      if(row + 2 < rows && button[row][column].getBackground() == button[row + 2][column].getBackground())
        countRows++;
    }
    
    if(row - 1 >= 0 && button[row][column].getBackground() == button[row - 1][column].getBackground()){
      countRows++;
      if(row - 2 >= 0 && button[row][column].getBackground() == button[row - 2][column].getBackground())
         countRows++;
    }
    
    //if countRows>= 3 then we know that there's three in a row vertically therefore we go through the next 2 loops one that goes up from the sart the other goes down and 
    //this will set the text in the whole series of same colored buttons to "*"
    if(countRows >= 3){ 
      int i = row;
      while(i < rows && button[row][column].getBackground() == button[i][column].getBackground()){
        button[i++][column].setText("*");
        countUp++;
      }
    
      i = row;
      while(i >= 0 && button[row][column].getBackground() == button[i][column].getBackground()){
        button[i--][column].setText("*");
        countDown++;
      }
      goodMove = true;
    }

    //this does the same as the first section of if statements just does it horizontally
    if(column + 1 < columns && button[row][column].getBackground() == button[row][column + 1].getBackground()){
      countColumns++;
       if(column + 2 < columns && button[row][column].getBackground() == button[row][column + 2].getBackground())
         countColumns++;
    }
    
    if(column - 1 >= 0 && button[row][column].getBackground() == button[row][column - 1].getBackground()){
      countColumns++;
       if( column - 2 >= 0 && button[row][column].getBackground() == button[row][column - 2].getBackground())
         countColumns++;
    }
    //this cause the columns to go through to the right and to the left set text to * until it reaches the end of a button with a different color, becasue if countColumns
    //is >= 3 then we know that there's a series of at least three in a row
    if(countColumns >= 3){
     
     button[row][column].setText("*");
      int j = column;
     while(++j < columns && button[row][column].getBackground() == button[row][j].getBackground()){
       button[row][j].setText("*");
       countRight++;
     }   
     j = column;
     while(--j >= 0 && button[row][column].getBackground() == button[row][j].getBackground()){
       button[row][j].setText("*");
       countLeft++;
    }
     goodMove = true;
   }       
  }
  
  /**copys the color of the above row and applies it to the row below, but only between a given set of columns
    * @param row represent the first row that needs to be droped
    * @param start is the first column that will have it's colors droped
    * @param end is the last column that will have it's colors droped
    */
  public void dropRow(int row, int start, int end){
    //goes to the jbutton above the current one and sets takes its color
    for(int i = start; i <= end; i++){
      //in order to stop the move method from changing a button when it shouldn't because drop column already droped the correct piece in this if statement is used, and
      //if the column its in already called drop column cus it had a vertical three in a row then this method won't drop it, it will just let drop column drop it
      if(avoidColumn && dontTouch == i){
      }else
        button[row][i].setBackground(button[row - 1][i].getBackground());
    }
  }
  
  /**Changes the color of the top row of a grid of JButtons, between the given constraints
    * @param start represents the the first column where the color wil to change
    * @param end represents the last column where the color will to change
    */ 
  public void addTopRow(int start, int end){
    Random randomNumber = new Random();
    int color;
    //Causes the top row to have a new random color between certain bounds, in order to simulate a new jewel falling in
    for(int i = start; i <= end; i++){
      color =(int)(randomNumber.nextDouble() * numJewels);
      switch (color){
           case 0:
             button[0][i].setBackground(Color.BLUE);
             break;
           case 1:
             button[0][i].setBackground(Color.GREEN);
             break;
           case 2:
             button[0][i].setBackground(Color.RED);
             break;
           case 3:
             button[0][i].setBackground(Color.BLACK);
             break;
           case 4:
             button[0][i].setBackground(Color.RED);
             break;
           case 5:
             button[0][i].setBackground(Color.CYAN);
             break;
           case 6:
             button[0][i].setBackground(Color.MAGENTA);
             break;
           case 7:
             button[0][i].setBackground(Color.ORANGE);
             break;
           default:
             button[0][i].setBackground(Color.YELLOW);
             break;
      }
    }
  }
  /**Drops all the colors in a specific column, between a given gap
    * @param column is the column that will be droped
    * @param start is the bottom of the set of buttons that will have it colored changed to the button end until end reaches the top
    * @param end is the first button who's color the buttons from start and a above will obtain
    */
  public void dropColumn(int column, int start, int end){
    //cause the row to drop a certain gap on the board in order to simulate a long vertical set of jewels being removed and there falling
    for(int i = 0; end - i >= 0; i++)
      button[start - i][column].setBackground(button[end - i][column].getBackground());
  }
  
  /**Randomly changes the color of the buttons starting at the top and going until countRow equals 0
    * @param column is the column who's button will be randomly changed
    */
  public void addTopColumn(int column){
    Random randomNumber = new Random();
    int color;    
    //Causes the top so many jewels to have a new color depending on how long the three in a row vertical combination was
    for(int i = countRows - 1; i >= 0; i--){
      color =(int)(randomNumber.nextDouble() * numJewels);
      switch (color){
           case 0:
             button[i][column].setBackground(Color.BLUE);
             break;
           case 1:
             button[i][column].setBackground(Color.GREEN);
             break;
           case 2:
             button[i][column].setBackground(Color.RED);
             break;
           case 3:
             button[i][column].setBackground(Color.BLACK);
             break;
           case 4:
             button[i][column].setBackground(Color.RED);
             break;
           case 5:
             button[i][column].setBackground(Color.CYAN);
             break;
           case 6:
             button[i][column].setBackground(Color.MAGENTA);
             break;
           case 7:
             button[i][column].setBackground(Color.ORANGE);
             break;
           default:
             button[i][column].setBackground(Color.YELLOW);
             break;
      }
    }
  }
  /**Metod determines if all the buttons have the text "*", and therefore the user won the game, if the game has been won it will return true
    * if not it will return false
    */
  public boolean didIWin(){
    Object compare = new Object();
    //goes through each object JButton to see if the text is "*" and if it is true is returned 
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < columns ; j++){
        victory = button[i][j].getText().equals("*");
        if(!victory){
          i = rows;
          j = columns;
        }
      }
    }
    return victory;
     
  }

  /**Method does everything required for one move in Jewels, if the two buttons can be legally moved they will be swithched and then compare
   * if they create a three in a row combination then rows and columns are droped as necessar so that those buttons will be "removed" and
   * new buttons will come in from the top.
   * @param button1 represents the second button that the user clicks
   * @param button2 represents the first button that the user clicks
   */
  public void move(JButton button1, JButton button2){
    int row = this.getRow(button1);
    int column = this.getColumn(button1);
    int start;
    int end;
    goodMove = false;
    countRight = 0;
    countUp = 0;
    countDown = 0;
    countLeft = 0;    
    possibleMove = false;
    avoidColumn = false;
    
    //this is just a way to determine if the 2 butons that are clicked are adjacent
    if (column + 1 < columns && button2 == button[row][column + 1])
      possibleMove = true;
    if(column - 1 >= 0 && button2 == button[row][column-1])
      possibleMove = true; 
    if(row + 1 < rows && button2 == button[row+1][column])
      possibleMove = true;
    if(row - 1 >= 0 && button2 == button[row-1][column])
      possibleMove = true;
    
    if(possibleMove){
      //since we know the buttons are adjacent the buttons can then be switched and the "Cilck" is removed, and then they are compared
      this.switchButtons(button1, button2);
      button2.setText(saveText);
      this.compare(row,column);  
    
      if(goodMove){          
          //if there is in fact a three in a row so it's a good move then this set of if statement will adjust the board so that all jewels are removed and dropped
        if(countRows >= 3 && countColumns >= 3 ){
          start = row + countUp - 1;
          end = row - countDown;
          this.dropColumn(column, start, end);
          this.addTopColumn(column);
          
          avoidColumn = true;
          dontTouch = column;
          
          start = column - countLeft;
          end = column + countRight;
          for(int i = row; i > 0; i--){            
            this.dropRow(i, start, end);
          }
          this.addTopRow(start, end);        
        }else if(countRows >= 3){
          start = row + countUp - 1;
          end = row - countDown;
          this.dropColumn(column, start, end);
          this.addTopColumn(column);
        }else if(countColumns >= 3){  
          start = column - countLeft;
          end = column + countRight;
          for(int i = row; i > 0; i--){            
            this.dropRow(i, start, end);
          }
          this.addTopRow(start, end);

        }
      
      }else{
        //if it wasn't a good move then the text is fixed and the buttons are switched
        this.switchButtons(button1, button2);
        button2.setText(saveText);      
      }
    }
      if(!goodMove)
        button2.setText(saveText);
      if(this.didIWin()){
        for(int i = 0; i < rows; i++){
          for(int j = 0; j < columns; j++)
            button[i][j].setText("You Win");
        }
      }
  }
  /**Keeps track of which buttons are clicked and once two buttons are clicked it applies both buttons to the move method
    * @param e represents a click of a JButton
    */
  @Override
   public void actionPerformed(ActionEvent e){
    if(clickCount){
      button1 = (JButton)e.getSource();
      saveText = button1.getText();
      button1.setText("Click");
    }else{
      button2 = (JButton)e.getSource();
      this.move(button2,button1);
    }
    clickCount = !clickCount;
  }  
          
/**Main method takes the integers and applies them to the constructor in the order row, column, number jewels and applies it to the constructor, and sets the JFrame to visible.
  * if no integers are give the defaul Jewels() constructor is called.
  * @author <Adam Collins>
  * @param args is a string array that allows user to decide the size of the grid and the number of jewels
  */
  public static void main(String[] args){
    Jewels game = new Jewels();
    
    try{
      game = new Jewels(Integer.valueOf(args[0]), Integer.valueOf(args[1]),
                        Integer.valueOf(args[2]));      
    
    }catch(ArrayIndexOutOfBoundsException e){
    }finally{       
    game.frame.pack();
    game.frame.setVisible(true);
    }
  }
  

  //All the methods below are simple getter/setter methods used for testing purposes
  /**returns button[row][column]
    * @param row represents the first index of the button array
    */
  public JButton getButton(int row, int column){
    return button[row][column];
  }
   
  /**returns the JPanel board
    */
  public JPanel getBoard(){
    return board;
  }
  
  /**allows countRows to be manually set
    * @param countRows the value the countRows for this paticular instance will take on
    */
  public void setCountRows(int countRows){
    this.countRows = countRows;
  }
}
  