/* Name: BM du Preez
   Student number: 18486460 */

import javax.sound.sampled.AudioInputStream; //Music
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File; //Music file

public class Projectile extends Critter
{
  private double velocity; //scale unites / per frame
  private boolean active;
  
  Projectile (double posX, double posY, String imageFile, double angle, double velocity)
  {
    super(posX, posY, imageFile, angle); //Initialise inherited variables
    
    //Initialise own variables
    this.velocity = velocity;
    active = true;
    
    //Play firing sound
    try 
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("shoot.wav").getAbsoluteFile());
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
  
  //Moves the projectile one step ahead on its path
  //Critter's move() method not used here -> automates the missile firing process
  public void move()
  {
    //Projectile moves at velocity = scale units / frames. Therefore scale units moved = velocity * 1 
    setPosX(getPosX() + velocity * Math.sin(-Math.toRadians(getAngle())));
    setPosY(getPosY() + velocity * Math.cos(-Math.toRadians(getAngle())));
    
    //Draw new position with Critter's move
    move(getPosX(), getPosY(), getAngle());    
  }
  
  //Deactivates the projectile (makes it invisible and innefective)
  public void deactivate()
  {
    active = false;
  }
  
  /*Returns true if the projectile hasn't been deactivated*/
  boolean isActive()
  {
    return active;
  }
  
  //Simple getters
  public double getVelocity()
  {
    return velocity;
  }
  
  //Simple setters
  public void setVelocity(double velocity)
  {
    this.velocity = velocity;
  }
}