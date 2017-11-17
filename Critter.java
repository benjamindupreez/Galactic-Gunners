/* Name: BM du Preez
   Student number: 18486460 */

public class Critter
{
  private double posX;
  private double posY;
  private String imageFile;
  private double angle; //degrees
  
  //Construct a new critter
  Critter (double posX, double posY, String imageFile, double angle)
  {
    //Initialise all the variables
    this.posX = posX;
    this.posY = posY;
    this.imageFile = imageFile;
    this.angle = angle; 
  }
  Critter (double posX, double posY, String imageFile)
  {
    //Initialise all the variables
    this.posX = posX;
    this.posY = posY;
    this.imageFile = imageFile;
    this.angle = 0;
  }
  
  //Move critter to a specified point facing a specified angle
  public void move(double newPosX, double newPosY, double newAngle)
  {
    //Update values
    posX = newPosX;
    posY = newPosY;
    angle = newAngle;
    
   //Draw critter
    StdDraw.picture(posX, posY, imageFile, angle);
  }
  
  public void draw() //Draws critter at current position
  {
    //Draw critter
    StdDraw.picture(posX, posY, imageFile, angle);
  }
  
  //Simple getters
  public double getPosX()
  {
    return posX;
  }
  public double getPosY()
  {
    return posY;
  }
  public String getImageFile()
  {
   return imageFile; 
  }
  public double getAngle()
  {
    return angle;
  }
  
  //Simple setters
  public void setPosX(double posX)
  {
    this.posX = posX;
  }
  public void setPosY(double posY)
  {
    this.posY = posY;
  }
  public void setImageFile(String imageFile)
  {
    this.imageFile = imageFile;
  }
  public void setAngle(double angle)
  {
    this.angle = angle;
  }
}