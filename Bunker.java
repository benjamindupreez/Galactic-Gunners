/* Name: BM du Preez
   Student number: 18486460 */

public class Bunker extends Critter
{
  private int health;
  
  public Bunker(double posX, double posY)
  {
    super(posX, posY, "Bunker.png"); //Initialise inherited variables
    
    //Initialise own variables
    health = 100;       
  }
  
  //Called when the Bunker is hit with a bullet, decreases health with 20 and updates image
  public void damage()
  {
    health -= 20;
    
    //Update bunker image to reflect damage
    switch(getHealth())
    {
      case 80: setImageFile("Bunker80.png");
      break;
      case 60: setImageFile("Bunker60.png");
      break;
      case 40: setImageFile("Bunker40.png");
      break;
      case 20: setImageFile("Bunker20.png");
      break;
    }
  }
  
  public void setHealth(int health)
  {
    this.health = health;
  }
  public int getHealth()
  {
    return health;
  }
  
}