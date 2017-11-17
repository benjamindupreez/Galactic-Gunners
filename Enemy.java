/* Name: BM du Preez
   Student number: 18486460 */

import javax.sound.sampled.AudioInputStream; //Music
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File; //Music file

public class Enemy extends Critter
{
  private boolean isAlive;
  
  Enemy(double posX, double posY, String imageFile, double angle)
  {
    super(posX, posY, imageFile, angle); //Initialise inherited variables
    
    //Initialise own variables
    isAlive = true;
  }
  
  public void die()
  {
    isAlive = false; 
    
    //Play enemy killed sound
    try 
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("invaderkilled.wav").getAbsoluteFile());
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
  
  //Simple getters
  public boolean isAlive()
  {
    return isAlive; 
  } 
}