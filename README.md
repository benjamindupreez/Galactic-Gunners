# Galactic-Gunners
A Space Invaders game developed for CS E214, Object-Oriented programming in Java

The main class that needs to be compiled and executed to run the program is Invaders.java

Include the stdlib.jar (Princeton University's standard libraries) by including the classpath when compiling and executing the program
Linux: javac -cp .:stdlib.jar Invaders.java, java -cp .:stdlib.jar Invaders
Windows: javac -cp .;stdlib.jar Invaders.java, java -cp .;stdlib.jar Invaders

  Additional work for bonus marks
===================================

* Upgraded graphics.
* Sounds: when enemies die, counter-attack or the user shoots.
* Enemies counter attack: The frequency of the counter attacks are determined by the level.
  A random enemy is selected to take the shot each time.
* Levels: There are 15 levels that get progressively harder. Each level the amount of enemies increase, the speed they move at and the frequency
  of counter attacks increase. There is a cap of 80 enemies and .2 movement per frame (at about level 8). From there on out only the frequency of
  counter attacks increase. This is to stop the game from becoming impossible to quickly while remaining challenging.
* Lives: the player starts with three lives. If the player has less than 3 lives after succesfully completing a level he/she is awarded
  another one. When shot by the enemy the player loses one life.
* Bunkers: Provides some protection from enemy counter attacks. Has hitpoints that decrease with each hit until it dissapears.
* Power-ups: Randomly spawns somewhere every now and then, but always spawns a certain distance from the user to make it challenging to collect.
  Dissapears at collection or after 2 seconds from spawning if not collected. The powerups can either contain upgraded (double) ammo for 5 seconds 
  or a bonus 25 points. There is a 50/50 chance of the powerup being of either type.
* Pause: the game can be paused and unpaused by the player by pressing p.
* High score: the previous high score is saved in a text file and the user is informed if he has beaten his old highscore.
* Messages: pop-up messages that can stay on the screen for a desired amount of time to convey information without stopping the
  game. For example a floating "+1" remains in the place of a killed enemy for a few frames and the level number is displayed in the middle
  of the screen as the user starts a new level. 

   Credits for resources used
===================================

**PICTURES**

Bullet: http://s2.postimg.org/vay5t66t1/bullet.png

UFO: http://piq.codeus.net/static/media/userpics/piq_79017_400x400.png

Heart: http://static.planetminecraft.com/files/resource_media/screenshot/1234/heart-8-bit_3346285.jpg

Shooter: http://www.pixeljoint.com/files/icons/spaceship1_final.png

Powerup: http://files.gamebanana.com/img/ico/sprays/mario_box_by_n1co_thumb.jpg

Bunker: http://cdn.toucharcade.com/wp-content/uploads/2011/02/mzl.zuwbxwkt.png

The start screen planets were cut from this image and pasted and used on the start screen:
http://piq.codeus.net/picture/3938

I made the starry space background myself using Photoshop.


**SOUNDS**

Shooting and invader killed sounds: http://www.classicgaming.cc/classics/spaceinvaders/sounds.php

Power up sound: http://www.audiosoundclips.com/wp-content/uploads/2015/01/SFX_Powerup_01.mp3
