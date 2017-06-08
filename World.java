import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.geom.Ellipse2D;
import java.util.TimerTask;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class World extends JPanel{
   //current user playing
   private User usr;
   //the main frame
   private JFrame frame;
   //arraylist of bubbles
   private ArrayList<Bubble> bbls;
   //arraylist of messages
   private ArrayList<String> msgs;
	
   //player character
	private Player player;
	
   //rope object
 	private Rope rope;  
   
   //used to stop the message center;
   private boolean running;
	
   //constructer
   public World(User user) {
      //set the current user to the supplied one
      usr = user;
      
      //message center should run from beginning
      running = true;
      
      //create 
      frame = new JFrame("Ball Attack");
      //set the window (frame) to a 1200 width and 900 height rectangle
      frame.setSize(1200, 900);
      //end the program when the x is clicked
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      //add the jpanel - that the game is drawn onto - to the frame 
      frame.add(this);
      //if the user could resize the screen, it would give them an advantage, so we don't let them
      frame.setResizable(false);
               
      //initialize the messages array and notify the user that the load was successful
      msgs = new ArrayList<String>();
      msgs.add("Load successful");         
           
      //load the first level
      loadLevel();
		
      //listen for key presses (used to control player)
		frame.addKeyListener(new KeyListener() {	
         //we dont use this method	becuase it only runs when the key is pressed down and then released	
			public void keyTyped(KeyEvent e) {
			}
	      //we use this method becuase it runs as soon as the key is pressed down, allowing for the user to hold it down
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
            //if the user hit the left arrow key, move left if the user isn't already off the left edge of the screen
				if (code == KeyEvent.VK_LEFT)
					if (player.getX() > 0)
						player.move(-1);
            //if the user hit the right arrow key, move right if the user isnt already to the edge of the screen
				if (code == KeyEvent.VK_RIGHT)
					if (player.getX() + player.getWidth() < frame.getWidth())
						player.move(1);
            //if the up key is hit, fire the rope if the rope can be shot
				if (code == KeyEvent.VK_UP)
					if (player.canShoot()) {
						rope = new Rope((player.getX() * 2 + player.getWidth()) / 2, player.getY() - 20);
                  player.shoot();
               }
            //repaint the image to show the new positions of every item
				repaint();
			}
	      //this method is run when the key that has been pressed is released by the user, so we don't use it as you cannot hold down a key with this method
			public void keyReleased(KeyEvent e) {
			}
		});
      
      //create a new timer that handles bubble movement
      Timer tmr = new Timer(10, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            //create a polygon that holds the trapeziod that reflects the player's current position on the screen
            Polygon p1 = new Polygon(new int[] {player.getX(), player.getX() + 40, player.getX() + 50, player.getX() + 90}, new int[] {player.getY() + 100, player.getY(), player.getY(), player.getY() + 100}, 4);
            Rectangle bounds;
            if (rope != null) {
               bounds = new Rectangle(rope.getX(), rope.getY(), rope.getWidth(), rope.getHeight());
            } else 
               bounds = null;
                  
            //go through every bubble in the array
            for (int i = 0; i < bbls.size(); i ++) {
               //set the bubble we want to check to be the current one in the array
               Bubble bbl = bbls.get(i);
               //if the bubble is about to hit(or has) the boarder of the screen, flip the xDirection the ball is traveling in
               if (bbl.getX() + bbl.getWidth() > getWidth() || bbl.getX() < 0)
                  bbl.flipXDirection();
               //if the bubble has hit the upper or lower boarder, flip the yDirection the ball is traveling in
               if (bbl.getY() + bbl.getHeight() > getHeight() - 70 || bbl.getY() < 0)
                  bbl.flipYDirection();
               //create an ellipse to check if the ball has collided with the player. we prove it with the bubbles x and y cooridnates as well as it's width and height
               Ellipse2D.Double bubble1 = new Ellipse2D.Double(bbl.getX(), bbl.getY(), bbl.getWidth(), bbl.getHeight());
               //if the user and ball hit each other
               if (bubble1.getBounds().intersects(p1.getBounds())) {
                  //the user looses a life
                  usr.loseLife();
                  //if the user is out of lives, end the game
                  if (usr.getLives() == 0) {
                     endGame();
                  } else 
                     msgs.add("You lost a life!");
                  /* basically, this finds whether the two objects are touching vertically or horizontally by calculating the distance
                   * between the right edge of an object and the left edge of a second object and then finding the minimum of that 
                   * and the left edge of the first and the right edge of the second. Then that is compared to the top and
                   * bottom of a second object through finding the minimum once again. If the distance in the x direction is smaller
                   * than the difference in the y direction, the two objects are touching in the x direction and vice versa.
                  */
                  if (Math.min(Math.abs((bbl.getX() + bbl.getWidth()) - (player.getX())), Math.abs((bbl.getX()) - (player.getX() + player.getWidth()))) < Math.min(Math.abs((bbl.getY() + bbl.getHeight()) - (player.getY())), Math.abs((bbl.getY()) - (player.getY() + player.getHeight())))) {
                     //the ball hit the side of the player, so flip the ball's x direction
                     bbl.flipXDirection();                  
                  } else {
                     //the ball hit the top of the player, flip the vertical direction of the ball
                     bbl.flipYDirection();
                  }
               }
               //or if a ball intersects the rope
               else if (bounds!= null && bounds.getBounds().intersects(bubble1.getBounds())){
                  //if the bubble is already the smallest size possible, we don't need to worry about splitting it
                       if (bbl.getWidth() / 20 - 1 == 0) {
                           //stop the rope
									rope = null;
                           //remove the bubble
									bbls.remove(i);
                           //if this was the last bubble, move on to the next level
                           if (bbls.size() == 0) nextLevel();
                           //add to the user's score
                           usr.incrementScore(4);
                           //end the loop
									break;
								}
                        
                        /* basically, this finds whether the two objects are touching vertically or horizontally by calculating the distance
                         * between the right edge of an object and the left edge of a second object and then finding the minimum of that 
                         * and the left edge of the first and the right edge of the second. Then that is compared to the top and
                         * bottom of a second object through finding the minimum once again. If the distance in the x direction is smaller
                         * than the difference in the y direction, the two objects are touching in the x direction and vice versa.
                        */
                        if (Math.min(Math.abs((rope.getX() + rope.getWidth()) - (bbl.getX())), Math.abs((rope.getX()) - (bbl.getX() + bbl.getWidth()))) < Math.min(Math.abs((rope.getY() + rope.getHeight()) - (bbl.getY())), Math.abs((rope.getY()) - (bbl.getY() + bbl.getHeight())))) {
                           //x collision
								   if (bbl.getWidth() / 20 - 1 != 0) {
                              //create 2 new bubbles, both traveling back the way they came, but one with the vertical velocity flipped
									   Bubble bb1 = new Bubble((bbl.getWidth() / 20) - 1, bbl.getX(), bbl.getY(), -bbl.getXDirection(), -bbl.getYDirection());
									   Bubble bb2 = new Bubble((bbl.getWidth() / 20) - 1, bbl.getX(), bbl.getY(), -bbl.getXDirection(), bbl.getYDirection());
									   //add the new bubbles to the end of the bubble arraylist
                              bbls.add(bb1);
									   bbls.add(bb2);
                              //stop the rope
									   rope = null;
                              //remove the old bubble from the list
									   bbls.remove(i);
                              //add to the user's score
                              usr.setScore(usr.getScore() + (5 - (bbl.getWidth() / 20)));
                              break;
   								}
                        } else {
                           //y collision
   								if (bbl.getWidth() / 20 - 1 != 0) {
                              //create 2 new bubbles, both traveling upwards, and opposite x velocities
   									Bubble bb1 = new Bubble((bbl.getWidth() / 20) - 1, bbl.getX(), bbl.getY(), bbl.getXDirection(), -Math.abs(bbl.getYDirection()));
   									Bubble bb2 = new Bubble((bbl.getWidth() / 20) - 1, bbl.getX(), bbl.getY(), -bbl.getXDirection(), -Math.abs(bbl.getYDirection()));
   									//add the new bubbles to the array
                              bbls.add(bb1);
   									bbls.add(bb2);
   									//renove the rope
                              rope = null;
   									//remove the old bubble from the array
                              bbls.remove(i);
                              //add to the user's score
                              usr.setScore(usr.getScore() + ( 5 - (bbl.getWidth() / 20)));
                              break;
   								}
                       	}	
                     }

               //move the ball once we are certain which way we should move it
               bbl.move(1);
               }
            //repaint the screen to show the movement of the ball
            repaint();
         }
      });
      //start the timer so the ball can move around
      tmr.start();
		
      //start the timer that controls the rope
		Timer ropeTimer = new Timer(20, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            //run the cooldown on the player every 20ms - this means the player can shoot every 60 cycles of the timer x 20ms = 1200ms or 1.2 s
            player.cooldown();
            //if the rope exists (it has been shot and hasn't hit a ball or the top of the screen yet), continue
				if (rope != null)
               //if the rope has gone past the top of the screen, remove it
					if (rope.getY() < 0) 
						rope = null;
					else {
                  //move the rope (extend it vertcally by 15 pixels
						rope.move(0);
			   }
            //we dont repaint here becuase the bubble method runs every 10 ms and this runs every 20 ms, so the bubble methods will repaint on the same intervals as this, negating the need to repaint again
			   //repaint();
		   }
		});
		//start the rope timer
		ropeTimer.start();
      
      //create a new thread to handle the message center (not a new timer(!))
		Thread msgThread = new Thread(new Runnable() {
			public void run() {
            //always run this
				while(running) {
				   try {
                  //i dont know why (and ive spend over an hour trying to figure this out), but without printing a blank string (or any string but i dont want to make the console messy)
                  //this loop stops running when it runs out of messages, and doesnt properly clear out future messages
                  System.out.print("");
                  //if there are messages in the queue
                  if (msgs.size() != 0){
                     //sleep the thread for 2 seconds to allow the user to see the message
                     Thread.sleep(2000);
                     //remove the currently showing message from the queue
							msgs.remove(0);
                  }
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
      //start the message center thread
		msgThread.start();
      
      //make the window visible
      frame.setVisible(true);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      //paint bubbles
      for (Bubble bbl : bbls) {
         g.setColor(bbl.getColor());
         g.fillOval(bbl.getX(), bbl.getY(), bbl.getWidth(), bbl.getHeight());
      }
      
      //paint player
		g.setColor(player.getColor());
		g.fillPolygon(new int[] {player.getX(), player.getX() + 40, player.getX() + 50, player.getX() + 90}, new int[] {player.getY() + 100, player.getY(), player.getY(), player.getY() + 100}, 4);
      
      //paint rope if shot
		if (rope != null) {
			g.setColor(rope.getColor());
			g.fillRect(rope.getX(), rope.getY(), rope.getWidth(), rope.getHeight());
		}
		
		//draw divider separating the game from the info area
		g.setColor(Color.black);
		g.fillRect(0, 800, getWidth(), 2);
		
      //set the font to a sansserif font
		g.setFont(new Font("SansSerif", Font.BOLD, 30)); 
      //draw the user's info
		g.drawString("Score: " + usr.getScore(), 10, 850);
		g.drawString("Lives: " + usr.getLives(), 210, 850);
		g.drawString("Level: " + usr.getLevel(), 410, 850);
      //if there is a message to display, show it
      if (msgs.size() != 0) 
		   g.drawString(msgs.get(0), 610, 850);
   }
   
   //save the user's current state
   public void save() {
      try { 
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
   	   out.writeObject(usr);
   	   out.flush();
   	   out.close();  
      } catch (Exception e) {
         e.printStackTrace();
      }
   }  
   
   //load the level the user is on
   public void loadLevel() {
      Level lvl;
      if (usr.getLevel() % 2 == 0) {
         lvl = new Level2();
      } else {
         lvl = new Level1();
      }
      //get the bubbles from the level;
      bbls = lvl.getBubbles();
            
      //set the player to the starting position
		player = new Player();
      
      //add a message to the message center saying the current level
      msgs.add("Level " + usr.getLevel());
   }
   
   public void nextLevel() {
      //add a life at the end of every level
      usr.addLife();
      //go to the next level on the user's account
      usr.nextLevel();
      //save the user's progress
      save(); 
      //alert the user they got an extra life
      msgs.add("You received an extra life!");
      //remove all the bubbles from the array
      bbls.removeAll(bbls);
      //load the next level
      loadLevel();
   }
   
   public void endGame() {
      //stop the message center
      running = false;
      //alert the user the game is over
      msgs.add("Game Over!");
		//remove all the bubbles from the screen so the user cannot hit them anymore
      bbls.removeAll(bbls);
      try {
			//to empty out the save.dat file, we close it right after opening it
         new FileOutputStream("save.dat").close();
      } catch (Exception e) {
         e.printStackTrace();
      }
		
		//create a new leaderboard object that contains the leaderboard file
		Leaderboard lb = new Leaderboard();
		//try to add the current user to the leaderboard - the place the user is inserted in the leaderboard is set as an int called yo
		int yo = lb.addUser(usr);
		//selection will hold what option the user selects on the final score screen on whether they want to go to the main menu or exit (yes or cancel)
      int selection;
		//if the user's score wasn't high enough to make the leaderboards
		if (yo == 0)
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". Unforunately you did not make the leaderboards.\nWould you like to return to the main menu?", "Return To The Main Menu?", JOptionPane.YES_NO_OPTION);
		else if (yo == 1)
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the highest score. Congrats!\nWould you like to return to the main menu?", "Return To The Main Menu?", JOptionPane.YES_NO_OPTION);
		else if (yo == 2) 
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the " + yo + "nd highest score.\nWould you like to return to the main menu?", "Return To The Main Menu?", JOptionPane.YES_NO_OPTION);
		else if (yo == 3) 
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the " + yo + "rd highest score.\nWould you like to return to the main menu?", "Return To The Main Menu?", JOptionPane.YES_NO_OPTION);
		else 
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the " + yo + "th highest score.\nWould you like to return to the main menu?", "Return To The Main Menu?", JOptionPane.YES_NO_OPTION);
      //if the user said yes, they want to return to the main menu, run the main method of ballattack to start the game over again, and dispose of the current frame
      if (selection == JOptionPane.YES_OPTION) {
         String[] args = {};
         BallAttack.main(args);
         frame.dispose();
     } else
     		System.exit(0);
         //end the program
   }
}