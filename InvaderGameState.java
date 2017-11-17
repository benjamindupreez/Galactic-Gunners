/* Name: BM du Preez
 Student number: 18486460 */

import java.awt.event.KeyEvent; //Buttons
import java.awt.Font;
import java.util.List; //Linked list
import java.util.LinkedList; //Linked list
import java.io.File; //High score file
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class InvaderGameState
{ 
  private int score;
  private int level;
  private int lives;
  private boolean paused;
  private long pTime;
  
  InvaderGameState()
  {
    score = 0;
    level = 1;
    lives = 3;
    paused = false;
    pTime = 0;
  }
  
  //STARTS THE GAME LOOP
  public void start()
  {    
    long lastShotTime = -500000000;
    boolean movingLeft = true;
    
    //Initialise shooter
    Shooter shooter = new Shooter(50, 2,  "Shooter.png", 0);
    
    //Initialise enemies
    List<Enemy> enemies = new LinkedList<Enemy>();
    int enemyX = 10;
    int enemyY = 100;
    int amountOfEnemies = 9 * (3 + level);
    double enemyMoveDistance = .05 + level*.025;
    //Cap number of enemies and their speed so it doesn't become impossible to quickly at higher levels
    if(amountOfEnemies > 90)
    {
      amountOfEnemies = 90;
    }
    if(enemyMoveDistance > .2)
    {
      enemyMoveDistance = .2;
    }
    
    for(int i = 0; i < amountOfEnemies; i++)
    {
      enemies.add(new Enemy(enemyX, enemyY, "Enemy.png", 0));
      enemies.get(i).draw();      
      if(enemyX == 90)
      {
        enemyX = 0;
        enemyY += 10;
      }
      enemyX += 10;
    }
    
    //Initialise bunkers
    List<Bunker> bunkers = new LinkedList<Bunker>();
    for(int x = 0; x < 3; x++)
    {
      bunkers.add(new Bunker(10 + 40*x, 11));
    }
    
    //Other
    List<Projectile> projectiles = new LinkedList<Projectile>();
    List<Message> messages = new LinkedList<Message>();
    List<PowerUp> powerups = new LinkedList<PowerUp>();
    
    messages.add(new Message(50, 70, "Level " + level, new Font("Impact", 1, 50), 750));
    
    //GAME LOOP
    while(true)
    {    
      if(paused)
      {
        if (StdDraw.isKeyPressed(KeyEvent.VK_P) && System.nanoTime() - pTime > 1000000000)
        {
          pTime = System.nanoTime();
          messages.add(new Message(50, 70, "Unpaused", new Font("Impact", 1, 50), 750));
          paused = false;
        }
      }
      if(!paused)
      {
        //Before beginning check if the user wants to quit/pause
        if (StdDraw.isKeyPressed(KeyEvent.VK_Q))
        {
          exit();
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_P) && System.nanoTime() - pTime > 1000000000)
        {
          pTime = System.nanoTime();
          messages.add(new Message(50, 70, "Paused", new Font("Impact", 1, 50), 10));
          paused = true;
        }
        
        //Clear the canvas
        StdDraw.clear();
        StdDraw.picture(50, 50, "Space.png");
        
        ////////// POWER UPS /////////
        //Manage current power ups
        for(int i = 0; i < powerups.size(); i++)
        {
          if(powerups.get(i).getTime() > 0)
          {
            powerups.get(i).draw();
          }
          else
          {
            powerups.remove(i);
          }
        }
        //1/1000 chance to add a powerup add powerups (about 1 in 10 seconds)
        if((int)(Math.random() * 1000) == 0)
        {
          //Choose random X position atleast 40 away from the shooter
          double posX = 3 + Math.random() * 94;
          while(Math.abs(posX - shooter.getPosX()) < 40)
          {
            posX = 3 + Math.random() * 94;
          }
          powerups.add(new PowerUp(posX, 2));
        }
        
        ////////// SHOOTER //////////
        //Handle shooter rotation
        if (StdDraw.isKeyPressed(KeyEvent.VK_A) && shooter.getAngle() <= 88)
        {
          shooter.setAngle(shooter.getAngle() + 2);
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_D) && shooter.getAngle() >= -88)
        {
          shooter.setAngle(shooter.getAngle() - 2);
        }
        
        //Move shooter if necessary
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && shooter.getPosX() > .5)
        {
          shooter.setPosX(shooter.getPosX() - .5);
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && shooter.getPosX() < 99.5)
        {
          shooter.setPosX(shooter.getPosX() + .5);
        }
        shooter.move(shooter.getPosX(), shooter.getPosY(), shooter.getAngle());
        
        //Determine what type of ammo the shooter should shoot this frame
        shooter.checkAmmo();
        
        ////////  BUNKERS  ////////
        for(int x = 0; x < bunkers.size(); x++)
        {
          bunkers.get(x).draw();
        }
        
        ////////  BULLETS  ////////
        //Check if user tried to take a shot
        if (StdDraw.isKeyPressed(KeyEvent.VK_S))
        {
          //Check if enough time has elapsed to shoot
          if(System.nanoTime() - lastShotTime > 500000000)
          {
            lastShotTime = System.nanoTime();
            
            //Single shot
            if(!shooter.isDoubleAmmo())
            {
              projectiles.add(new Projectile(shooter.getPosX(), shooter.getPosY(), "Bullet.png", shooter.getAngle(), 1));
            }
            //Double shot
            else
            {
              projectiles.add(new Projectile(shooter.getPosX() - (5 * Math.cos(-Math.toRadians(shooter.getAngle()))), shooter.getPosY() + (5 * Math.sin(-Math.toRadians(shooter.getAngle()))), "Bullet.png", shooter.getAngle(), 1));
              projectiles.add(new Projectile(shooter.getPosX() + (5 * Math.cos(-Math.toRadians(shooter.getAngle()))), shooter.getPosY() - (5 * Math.sin(-Math.toRadians(shooter.getAngle()))), "Bullet.png", shooter.getAngle(), 1)); 
            }
          }
        }
        
        //Randomly allow a enemy to counterattack
        int counterAttack = (int)((500 / level) * Math.random());
        if (counterAttack == 0) //Approx one of 300 frames (which will be about 3 seconds) one of the enemies shoots 
        {
          //Choose enemy to take the shot
          int shootingEnemy = (int)(enemies.size() * Math.random());
          
          //Take shot
          projectiles.add(new Projectile(enemies.get(shootingEnemy).getPosX(), enemies.get(shootingEnemy).getPosY(), "Bullet.png", 180, 1));
        }
        
        //Update position of all the projectiles in motion
        for(int i = 0; i < projectiles.size(); i++)
        {
          projectiles.get(i).move();
          
          /*If it is now out of the screen, remove from the list.
           Add a bit of extra pixels for safety.*/
          if(projectiles.get(i).getPosX() > 110 || projectiles.get(i).getPosX() < -10 || projectiles.get(i).getPosY() > 110 || projectiles.get(i).getPosY() < -10)
          {
            projectiles.remove(i);
          }        
        }
        
        //////////  ENEMIES  /////////
        //Check if the end of the screen has been reached
        for(int i = 0; i < enemies.size(); i++)
        {
          if((!movingLeft && 100 - enemies.get(i).getPosX() < .2) || (movingLeft && enemies.get(i).getPosX() < .2))
          {   
            //If yes move all the enemies down
            for(int n = 0; n < enemies.size(); n++)
            {
              enemies.get(n).move(enemies.get(n).getPosX(), enemies.get(n).getPosY() - 5, 0);
            }
            //Change direction flag to move back
            if(movingLeft)
            {
              movingLeft = false;
            }
            else
            {
              movingLeft = true;
            }          
            break;
          }
        }
        
        if(movingLeft)
        {
          //Move enemies left
          for(int i = 0; i < enemies.size(); i++)
          {      
            enemies.get(i).move(enemies.get(i).getPosX() - enemyMoveDistance, enemies.get(i).getPosY(), 0);
          }
        }
        else
        {
          //Move enemies right
          for(int i = 0; i < enemies.size(); i++)
          {       
            enemies.get(i).move(enemies.get(i).getPosX() + enemyMoveDistance, enemies.get(i).getPosY(), 0);
          }
        }
        
        //CHECK FOR COLLISIONS        
        //1. Projectile/something collisions
        //For each projectiles in the list and check if it collides with something
        for(int i = 0; i < projectiles.size(); i++)
        {
          // 1.1 With Enemy
          for(int n = 0; n < enemies.size(); n++)
          {
            /*If the bullet has an angle of 180 it has been shot by an enemy (himself) and should be ignored
             Only bullets with a different angle are taken as a hit.*/              
            if(projectiles.get(i).getAngle() != 180)
            {              
              //Is the bullet at the y level of the enemies
              if(Math.abs(projectiles.get(i).getPosY() - enemies.get(n).getPosY()) < 5.66)
              {              
                //Is the bullet within the x boundaries of a column of enemies
                if(Math.abs(projectiles.get(i).getPosX() - enemies.get(n).getPosX()) < 3.761)
                {
                  //The enemy must die now
                  enemies.get(n).die();
                  messages.add(new Message(enemies.get(n).getPosX(), enemies.get(n).getPosY(), "+1", new Font("Impact", 1, 20), 300));
                  
                  //Deactivate bullet (flag for removal)
                  projectiles.get(i).deactivate();
                  
                  score++;
                }
              }
            }
          }
          //Remove dead enemies
          for(int n = 0; n < enemies.size(); n++)
          {
            if(!enemies.get(n).isAlive())
            {
              enemies.remove(n);
            }
          }
          
          // 1.2 With Shooter
          /*If the bullet has an angle between 90 and -90 it has been shot by the shooter and should be ignored.
           * Only active bullets with an angle outside of this range (shot by enemies) are taken as a hit. */
          if(projectiles.get(i).getAngle() < -90 || projectiles.get(i).getAngle() > 90)
          {
            //Is the bullet at the y level of the shooter
            if(Math.abs(projectiles.get(i).getPosY() - shooter.getPosY()) < 1)
            {              
              //Is the bullet within the x boundaries of the shooter
              if(Math.abs(projectiles.get(i).getPosX() - shooter.getPosX()) < 4.72)
              {
                lives--;
                messages.add(new Message(84, 88, "-1 life", new Font("Impact", 1, 25), 1000));
                
                //Deactivate bullet (flag for removal)
                projectiles.get(i).deactivate();
              }
            }
          }
          
          // 1.3 With Bunker
          for(int x = 0; x < bunkers.size(); x++)
          {
            //Is the bullet at the y level of the bunker
            if(Math.abs(projectiles.get(i).getPosY() - bunkers.get(x).getPosY()) < 1)
            {              
              //Is the bullet within the x boundaries of the bunker
              if(Math.abs(projectiles.get(i).getPosX() - bunkers.get(x).getPosX()) < 7.08)
              {
                //Decrease bunker health
                bunkers.get(x).damage();
                
                //If bunker is destroyed remove from the list
                if(bunkers.get(x).getHealth() == 0)
                {
                  bunkers.remove(x);
                }
                
                //Deactivate bullet (flag for removal)
                projectiles.get(i).deactivate();
              }
            }
          }        
        }
        //Remove deactivated bullets
        for(int i = 0; i < projectiles.size(); i++)
        {
          if(!projectiles.get(i).isActive())
          {
            projectiles.remove(i);
          }
        }
        
        //2. Shooter/Power up collision
        for(int x = 0; x < powerups.size(); x++)
        {
          if(Math.abs(shooter.getPosX() - powerups.get(x).getPosX()) < 7.25)
          {
            switch(powerups.get(x).getType())
            {
              case 0: messages.add(new Message(powerups.get(x).getPosX(), powerups.get(x).getPosY(), "+25", new Font("Impact", 1, 25), 1000));
              score += 25;
              break;  
              case 1: messages.add(new Message(50, 50, "Ammo Upgraded", new Font("Impact", 1, 40), 500));
              shooter.upgradeAmmo();
              break;
            }
            powerups.get(x).playSound();
            powerups.remove(x);
          }
        }
        //REDRAW CURRENT LEVEL
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Impact", 1, 30));
        StdDraw.text(5, 95, "Level " + level, 0);
        
        //DRAW UPDATED SCORE
        StdDraw.text(95, 95, score + "", 0);
        
        //DRAW UPDATED LIVES
        int heart1PosX = 80;
        for(int x = 0; x < lives; x++)
        {
          StdDraw.picture(heart1PosX, 96, "Heart.png");
          heart1PosX += 4;
        }
        
        //////// DRAW MESSAGES  ////////
        for(int x = 0; x < messages.size(); x++)
        {
          if(messages.get(x).getTime() >= 0)
          {
            messages.get(x).show();
          }
          else
          {
            messages.remove(x);
          }
        }
        
        //CHECK FOR GAME OVER / USER WIN    
        //All enemies dead -> user wins
        if(allEnemiesDead(enemies))
        {
          //At level 15 the user has completed the game
          if(level == 15)
          {
            showEndGameScreen("YOU WON THE GAME!"); 
          }
          else
          {
            //Upgrade level
            level++;
            
            StdDraw.show(10);
            
            //If the user has lost live, he regains one for completing the level
            if(lives < 3)
            {
              lives++;
            }       
            //Restart game loop for the new level
            start();
          }
          //Close this instance of the start() method
          return; 
        }
        
        //Enemies too close or lives are up -> game over
        if(gameIsLost(enemies, shooter))
        {                
          showEndGameScreen("GAME OVER");
          return;//End this instance of start()
        }
        StdDraw.show(10);
      }
    }
  }
  
  //END GAME
  public void exit()
  {
    System.exit(0);
  }
  
  private void showEndGameScreen(String message)
  {    
    //Save time when game ended
    long gameEndTime = System.nanoTime();
    
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(new Font("Impact", 1, 50));
    StdDraw.text(50, 80, message);        
    StdDraw.setFont(new Font("Impact", 1, 30));
    StdDraw.text(50, 60, "game will auto-restart in 3 seconds");
    StdDraw.text(50, 50, "press q to quit");
    
    //Check highscore
    checkHighscore(score);
    
    StdDraw.show(10);
    
    while(System.nanoTime() - gameEndTime < 3000000000.00)
    {
      //The game isn't due to be restarted yet, so check if the user wants to quit
      if(StdDraw.isKeyPressed(KeyEvent.VK_Q))
      {
        exit();
      }
    }
    
    //The 3 seconds has passed. Reset level, lives and score and retart game.
    level = 1;
    score = 0;
    lives = 3;
    start();    
  }
  
  private boolean allEnemiesDead(List<Enemy> enemies)
  {
    for(int i = 0; i < enemies.size(); i++)
    {
      if(enemies.get(i).isAlive())
      {
        return false;
      }
    }
    return true;
  }
  
  private boolean gameIsLost(List<Enemy> enemies, Shooter shooter)
  {
    if(lives == 0)
    {
      return true;
    }
    for(int i = 0; i < enemies.size(); i++)
    {
      //Ground level or collision with the shooter
      if(enemies.get(i).getPosY() < 0 || (Math.abs(enemies.get(i).getPosX() - shooter.getPosX()) < 7 && Math.abs(enemies.get(i).getPosY() - shooter.getPosY()) < 7))
      {
        return true;
      }
    }    
    return false;
  }
  
  private void checkHighscore(int score)
  {
    //Check if the new score is a high score, react appropriately
    try
    {
      FileReader fr = new FileReader("highscore.txt");
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      if(line != null)
      {
        int highScore = Integer.parseInt(line);
        if(score > highScore)
        {
          FileWriter fw = new FileWriter("highscore.txt", false);
          fw.write(score + "");
          fw.flush();
          fw.close();
          
          StdDraw.setFont(new Font("Impact", 1, 60));
          StdDraw.text(50, 30, "NEW HIGH SCORE!");
        }
      }
      else
      {
        FileWriter fw = new FileWriter("highscore.txt", false);
        fw.write(score + "");
        fw.flush();
        fw.close();
      }
    }
    catch(IOException e)
    {
      System.out.println(e);
    }
  }
}