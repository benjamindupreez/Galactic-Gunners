/* Name: BM du Preez
   Student number: 18486460 */

import javax.sound.sampled.AudioInputStream; //Music
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File; //Music file

public class PowerUp extends Critter
{ 
  private int type; // 0 = score, 1 = ammo
  private long time;
  
  public PowerUp(double posX, double posY)
  {
    //initialies inherited variables
    super(posX, posY, "PowerUp.png");
    
    //initialise own variables
    type = (int)(Math.random()*2);
    time = 2000;
  }
  
  //Override Critter's draw -> checks if PowerUp should be displayed and does so only if necessary
  @Override
  public void draw()
  {
    if(time >= 10)
    {
      StdDraw.picture(getPosX(), getPosY(), getImageFile(), getAngle());
      time -= 10;
    }    
  }
  
  public void playSound()
  {
    try 
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("powerup.wav").getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } 
    catch(Exception ex) 
    {
      System.out.println("Error with playing sound.");
      ex.printStackTrace();
    }    
  }
  
  public int getType()
  {
    return type;
  }
  public long getTime()
  {
    return time;
  }
}