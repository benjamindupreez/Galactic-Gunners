/* Name: BM du Preez
   Student number: 18486460 */

import javax.sound.sampled.AudioInputStream; //Music
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File; //Music file
import java.awt.event.KeyEvent; //Buttons

public class Invaders
{
  public static void main (String args[])
  {    
    //Prepare canvas and show title screen
    StdDraw.setCanvasSize(678, 512);
    StdDraw.setXscale(0, 100);
    StdDraw.setYscale(0, 100);
    StdDraw.picture(50, 50, "Titlescreen.png");    
        
    //Declare a new game
    InvaderGameState game = new InvaderGameState();
    
    //Display start screen
    while(true)
    {
      if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
      {
        game.start();
        break;
      }
      if(StdDraw.isKeyPressed(KeyEvent.VK_Q))
      {
        game.exit();
        break;
      }        
    }  
  }  
}