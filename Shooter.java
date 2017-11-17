/* Name: BM du Preez
   Student number: 18486460 */

public class Shooter extends Critter
{
  private boolean doubleAmmo;
  private long time; // time double ammo still has left if activated in ms
  
  Shooter (double posX, double posY, String imageFile, double angle)
  {
    super(posX, posY, imageFile, angle); //Initialise inherited variables
    
    //initialise own variables
    doubleAmmo = false;
    time = 0;
  }
  
  public void upgradeAmmo()
  {
    doubleAmmo = true;
    time = 5000;    
  }
  public void checkAmmo()
  {
   time -= 10;
   if(time == 0) //ammo should be reverted to standard ammo
   {
     doubleAmmo = false;
   }
  }
  
  //simple getters and setters
  public long getTime()
  {
    return time;
  }
  public boolean isDoubleAmmo()
  {
    return doubleAmmo;
  }
}