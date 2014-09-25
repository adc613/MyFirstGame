import junit.framework.TestCase;

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

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class JewelsTest extends TestCase {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  //just test to make sure that every JButton gets declared
  
  public void testDeclareButtons() {
  Jewels game = new Jewels();
    for(int i = 0; i > 8; i++){
      for(int j = 0;j > 10; j++)
        //if no buttons return a null then they had to have been declared, becasue before declare method they would have returned null
        assertNotNull(game.getButton(i,j));
    }
      
  }
  
  
  public void testAddButtons(){
    Jewels game = new Jewels();
    int n = 0;
    
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++){
        //if it manages to go through this whole loop with out failing the test then every button has to have been added to the right spot
        assertEquals(game.getBoard().getComponent(n), game.getButton(i,j));
        n++;
      }
    }
  }
  
  public void testAddTopColumn(){
    Jewels game = new Jewels();
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        //sets every button on the grid to gray, and gray is never used inside of Jewels therefore if a button isn't gray it came from the addTopColumn method
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    //test first
    game.setCountRows(3);
    game.addTopColumn(1);
    for(int i = 2; i >= 0; i--)
      assertNotSame(game.getButton(0,1).getBackground(), Color.GRAY);
                                                                       //the only color that shouldn't be gray is the first button in the collum
    for(int i = 7; i > 2; i--)
      assertEquals(game.getButton(i,1).getBackground(), Color.GRAY);
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
        
    game.setCountRows(8);
    game.addTopColumn(1);
   
    //test to see if it will change all the buttons in the column
    for(int i = 8; i >= 0; i--)
      assertNotSame(game.getButton(0,1).getBackground(), Color.GRAY);
    
    game.setCountRows(5);
    game.addTopColumn(2);
    
    //test middle to see if it can change some the of the buttons but not all
    for(int i = 4; i >= 0; i--)
      assertNotSame(game.getButton(0,2).getBackground(), Color.GRAY);
    
    for(int i = 7; i > 4; i--)
      assertEquals(game.getButton(i,2).getBackground(), Color.GRAY);
  }
  
  public void testAddTopRow(){
    Jewels game = new Jewels();
    JButton button = new JButton();
    button.setBackground(Color.GRAY);
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    game.addTopRow(0,9);
    
    //checks to make sure that the whole row is replaced
    for(int j = 0; j < 10; j++)    
      assertNotSame(game.getButton(0,j).getBackground(), Color.GRAY);
            
    for(int i = 0; i < 8; i++){    
      for(int j = 0; j < 10; j++)      
        game.getButton(i,j).setBackground( Color.GRAY);
      }
    
    game.addTopRow(1,9);
    
    //checks to see if everyt button except for the far left extrem will be replaced 
    for(int j = 1; j < 10; j++)
        assertNotSame(game.getButton(0,j).getBackground(), Color.GRAY);
     
    assertEquals(game.getButton(0,0).getBackground(), Color.GRAY);
     
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    
    //checks to make sure that everything except the far right extreme is replaced
    game.addTopRow(0,8);
     for(int j = 0; j < 9; j++)
        assertNotSame(game.getButton(0,j).getBackground(), Color.GRAY);
     assertEquals(game.getButton(0,9).getBackground(), Color.GRAY);
  }
  
  public void testRandomColor(){
    Jewels game = new Jewels();
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    game.randomColors();
     
    //if random color works properly then there shouldn't be any gray left on hte board
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        assertNotSame(game.getButton(i,j).getBackground(), Color.GRAY);
    }
  }
  
  public void testDidIWin(){
    Jewels game = new Jewels();
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setText("*");     
    }
    //only time it should ever return win
     assertEquals(game.didIWin(), true);      
    
     //these test all make sure that it doesn't return true if all except for one of the extremes
     game.getButton(0,0).setText("");
  
     assertEquals(game.didIWin(), false);
     
     game.getButton(0,0).setText("*");
     game.getButton(7,9).setText("");
     
     assertEquals(game.didIWin(), false);
     
     game.getButton(7,9).setText("*");
     game.getButton(7,0).setText("");
     
     assertEquals(game.didIWin(), false);
     
     game.getButton(7,0).setText("*");
     game.getButton(0,9).setText("");
     
     assertEquals(game.didIWin(), false);
         
     game.getButton(0,9).setText("*");
     game.getButton(5,5).setText("");
     
     //this test the middle to make sure that it doesn't skip over that
     assertEquals(game.didIWin(), false);
    
    for(int i = 0; i > 8; i++){
        for(int j = 0; j > 10; j++)
          game.getButton(i,j).setText("");     
      }
    //this final test is test 0 it makes sure it doesn't return true when none of the buttons have a *
    assertEquals(game.didIWin(), false);
  }
  
  public void testDropColumn(){
    Jewels game = new Jewels();
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    //makes sure that it drops the one red jewels
    game.getButton(1,2).setBackground(Color.RED);
    game.dropColumn(2,2,1);
    assertEquals(game.getButton(2,2).getBackground(), Color.RED);
    

    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    
    //makes sure that it can drop multiple jewels
    game.getButton(1,2).setBackground(Color.RED);
    game.getButton(2,2).setBackground(Color.RED);
    game.getButton(3,2).setBackground(Color.RED);
    game.dropColumn(2,7,3);
    for(int i = 7; i > 4; i--)
      assertEquals(game.getButton(i,2).getBackground(), Color.RED);
    
  }
  
  public void testDropRow(){
    Jewels game = new Jewels();
    JButton button = new JButton();
    button.setBackground(Color.GRAY);
    game.randomColors();
    for(int i = 1; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    game.dropRow(1,0,9);
    
    //checks to make sure that the whole row is droped
    for(int j = 0; j < 10; j++)    
      assertEquals(game.getButton(1,j).getBackground(), game.getButton(0 , j).getBackground());
    
    game.randomColors();
    
    for(int i = 1; i < 8; i++){    
      for(int j = 0; j < 10; j++)      
        game.getButton(i,j).setBackground( Color.GRAY);
      }
    
    game.dropRow(1,1,9);
    
    //checks to see if everyt button except for the far left extrem won't be droped 
    for(int j = 1; j < 10; j++)
        assertEquals(game.getButton(1 , j).getBackground(), game.getButton(0, j).getBackground());
     
    assertNotSame(game.getButton(1,0).getBackground(), game.getButton(0, 0).getBackground());
    
    game.randomColors();
     
    for(int i = 1; i < 8; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
    
    //checks to make sure that everything except the far right extreme is not droped
    game.dropRow(1,0,8);
     for(int j = 0; j < 9; j++)
        assertEquals(game.getButton(1,j).getBackground(), game.getButton(0,j).getBackground());
     assertNotSame(game.getButton(0,9).getBackground(), game.getButton(1 , 9).getBackground());
     
     game.randomColors();
     
     for(int i = 0; i < 7; i++){
      for(int j = 0; j < 10; j++)
        game.getButton(i,j).setBackground(Color.GRAY);
    }
     for(int i = 0; i < 10; i++)
       game.getButton(7, i).setBackground(Color.GRAY);
     
     game.dropRow(7,0,9);
     
     //make sure that it can drop any row
     for(int j = 0; j < 10; j++)
        assertEquals(game.getButton(7,j).getBackground(), game.getButton(6,j).getBackground());
    
  }
  
  public void testGetColumn(){
    Jewels game = new Jewels();
    for(int i = 0; i > 8; i++){
      for(int j = 0; j > 10; j++){
        //this has to return the correct column for every single button for it to work
        assertEquals(game.getButton(i,game.getColumn(game.getButton(i,j))), game.getButton(i,j));
      }
    }
  }
  
  public void testGetRow(){
    Jewels game = new Jewels();
    for(int i = 0; i > 8; i++){
      for(int j = 0; j > 10; j++){
        //has to return the correct row for every button for it to work
        assertEquals(game.getButton(game.getRow(game.getButton(i,j)),j), game.getButton(i,j));
      }
    }
  }
     
  public void testSwitchButtons(){
    Jewels game = new Jewels();
    JButton button1 = new JButton();
    JButton button2 = new JButton();
    game.getButton(0,0).setBackground(Color.BLACK);
    game.getButton(3,3).setBackground(Color.RED);
    
    button1.setBackground(game.getButton(0,0).getBackground());
    button2.setBackground(game.getButton(3,3).getBackground());
    
    game.switchButtons(game.getButton(0,0), game.getButton(3,3));
    
    //if the two button both inherit each other color than switchButtons works properly
    assertEquals(game.getButton(3,3).getBackground(), button1.getBackground());
    assertEquals(game.getButton(0,0).getBackground(), button2.getBackground());
  }
  
  public void testCompare(){
  }
    
  public void testMove(){
  }
 
}
