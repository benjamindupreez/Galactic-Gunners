/* Name: BM du Preez
   Student number: 18486460 */

import java.awt.Font;

public class Message
{ 
  private double posX;
  private double posY;
  private String messageText;
  private Font font;
  private long time; // How long the message should be displayed
  
  Message(double posX, double posY, String messageText, Font font, long time)
  {
    //initialise variables
    this.posX = posX;
    this.posY = posY;
    this.messageText = messageText;
    this.font = font;
    this.time = time;
  }
  
  //Checks if message should be displayed and does so if necessary
  void show()
  {
    if(time >= 10)
    {
      StdDraw.setPenColor(StdDraw.WHITE);
      StdDraw.setFont(font);
      StdDraw.text(posX, posY, messageText);
      time -= 10;
    }
  }
  
  // Simple getters and setters
  long getTime()
  {
    return time;
  }
}