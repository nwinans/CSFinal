import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.TimerTask;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class World extends JPanel{

   private User usr;
   private JFrame frame;
   private ArrayList<Bubble> bbls;
	
	private Player player;
	
 	private Rope rope;  
	
   public World(User user) {
      usr = user;
      
      frame = new JFrame("Level " + usr.getLevel());
      frame.setSize(1200, 800);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      frame.add(this);
      frame.setResizable(false);
               
      loadLevel();
		
		frame.addKeyListener(new KeyListener() {			
			public void keyTyped(KeyEvent e) {
			}
	
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_LEFT)
					if (player.getX() > 0)
						player.move(-1);
				if (code == KeyEvent.VK_RIGHT)
					if (player.getX() + player.getWidth() < frame.getWidth())
						player.move(1);
				if (code == KeyEvent.VK_UP)
					if (player.canShoot()) {
						rope = new Rope((player.getX() * 2 + player.getWidth()) / 2, player.getY() - 20);
                  player.shoot();
               }
				repaint();
			}
	
			public void keyReleased(KeyEvent e) {
			}
		});
      
      Timer tmr = new Timer(10, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < bbls.size(); i ++) {
               Bubble bbl = bbls.get(i);
               if (bbl.getX() + bbl.getWH() > getWidth() || bbl.getX() < 0)
                  bbl.flipXDirection();
               if (bbl.getY() + bbl.getWH() > getHeight() || bbl.getY() < 0)
                  bbl.flipYDirection();
               Ellipse2D.Double bubble1 = new Ellipse2D.Double(bbl.getX(), bbl.getY(), bbl.getWH(), bbl.getWH());
               Rectangle p1 = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
               if (bubble1.getBounds2D().intersects(p1.getBounds2D())) {
                  usr.loseLife();
                  if (usr.getLives() == 0) {
                     endGame();
                  }
                  if (Math.min(Math.abs((bbl.getX() + bbl.getWH()) - (player.getX())), Math.abs((bbl.getX()) - (player.getX() + player.getWidth()))) < Math.min(Math.abs((bbl.getY() + bbl.getWH()) - (player.getY())), Math.abs((bbl.getY()) - (player.getY() + player.getHeight())))) {
                     bbl.flipXDirection();                  
                  } else {
                     bbl.flipYDirection();
                  }
               }
               /*for (int j = i + 1; j < bbls.size(); j++) {
                  Bubble b = bbls.get(j);
                  Ellipse2D.Double bubble2 = new Ellipse2D.Double(b.getX(), b.getY(), b.getWH(), b.getWH());
                  if (bubble1.getBounds2D().intersects(bubble2.getBounds2D())){
                     if (Math.min(Math.abs((bbl.getX() + bbl.getWH()) - (b.getX())), Math.abs((bbl.getX()) - (b.getX() + b.getWH()))) < Math.min(Math.abs((bbl.getY() + bbl.getWH()) - (b.getY())), Math.abs((bbl.getY()) - (b.getY() + b.getWH())))) {
                        //x collision
                        //bbl.flipXDirection();
								if (b.getWH() / 20 - 1 != 0) {
									Bubble bb1 = new Bubble((b.getWH() / 20) - 1);
									Bubble bb2 = new Bubble((b.getWH() / 20) - 1);
									bbls.add(bb1);
									bbls.add(bb2);
								}
								bbls.remove(j);
                        //b.flipXDirection();
                     } else {
                        //y collision
                        bbl.flipYDirection();
                        b.flipYDirection();  
                     }
                  }
               }*/
               bbl.move(1);
               }
            repaint();
         }
      });
      
      tmr.start();
		
		Timer ropeTimer = new Timer(20, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            player.cooldown();
				if (rope != null)
					if (rope.getY() < 0) 
						rope = null;
					else {
						rope.move(0);
						Rectangle bounds = new Rectangle(rope.getX(), rope.getY(), rope.getWidth(), rope.getHeight());
               	for (int j = 0; j < bbls.size(); j++) {
                  	Bubble b = bbls.get(j);
                  	Ellipse2D.Double bubbleBounds = new Ellipse2D.Double(b.getX(), b.getY(), b.getWH(), b.getWH());
                  	if (bounds.getBounds2D().intersects(bubbleBounds.getBounds2D())){
								if (b.getWH() / 20 - 1 == 0) {
									rope = null;
									bbls.remove(j);
                           if (bbls.size() == 0) nextLevel();
                           usr.incrementScore(4);
                           frame.setTitle(usr.getScore() + "");
									break;
								}
                        
                        /* basically, this finds whether the two objects are touching vertically or horizontally by calculating the distance
                         * between the right edge of an object and the left edge of a second object and then finding the minimum of that 
                         * and the left edge of the first and the right edge of the second. Then that is compared to the top and
                         * bottom of a second object through finding the minimum once again. If the distance in the x direction is smaller
                         * than the difference in the y direction, the two objects are touching in the x direction and vice versa.
                        */
                        if (Math.min(Math.abs((rope.getX() + rope.getWidth()) - (b.getX())), Math.abs((rope.getX()) - (b.getX() + b.getWH()))) < Math.min(Math.abs((rope.getY() + rope.getHeight()) - (b.getY())), Math.abs((rope.getY()) - (b.getY() + b.getWH())))) {
                           //x collision
								   if (b.getWH() / 20 - 1 != 0) {
									   Bubble bb1 = new Bubble((b.getWH() / 20) - 1, b.getX(), b.getY(), -b.getXDirection(), -b.getYDirection());
									   Bubble bb2 = new Bubble((b.getWH() / 20) - 1, b.getX(), b.getY(), -b.getXDirection(), b.getYDirection());
									   bbls.add(bb1);
									   bbls.add(bb2);
									   rope = null;
									   bbls.remove(j);
                              usr.setScore(usr.getScore() + (5 - (b.getWH() / 20)));
                              frame.setTitle(usr.getScore() + "");
   									break;
   								}
                        } else {
                           //y collision
   								if (b.getWH() / 20 - 1 != 0) {
   									Bubble bb1 = new Bubble((b.getWH() / 20) - 1, b.getX(), b.getY(), b.getXDirection(), -Math.abs(b.getYDirection()));
   									Bubble bb2 = new Bubble((b.getWH() / 20) - 1, b.getX(), b.getY(), -b.getXDirection(), -Math.abs(b.getYDirection()));
   									bbls.add(bb1);
   									bbls.add(bb2);
   									rope = null;
   									bbls.remove(j);
                              usr.setScore(usr.getScore() + ( 5 - (b.getWH() / 20)));
                              frame.setTitle(usr.getScore() + "");
   									break;
   								}
                       	}	
                     }
					   }
			   }
			   repaint();
		   }
		});
		
		ropeTimer.start();
      
      frame.setVisible(true);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      //paint bubbles
      for (Bubble bbl : bbls) {
         g.setColor(bbl.getColor());
         g.fillOval(bbl.getX(), bbl.getY(), bbl.getWH(), bbl.getWH());
      }
      
      //paint player
		g.setColor(Color.red);
		g.fillPolygon(new int[] {player.getX(), player.getX() + 40, player.getX() + 50, player.getX() + 90}, new int[] {player.getY() + 100, player.getY(), player.getY(), player.getY() + 100}, 4);
      
      //paint rope if shot
		if (rope != null) {
			g.setColor(Color.blue);
			g.fillRect(rope.getX(), rope.getY(), rope.getWidth(), rope.getHeight());
		}
   }
   
   public void save() {
      try { 
         usr.nextLevel();
         
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
   	   out.writeObject(usr);
   	   out.flush();
   	   out.close();
         
         System.out.println("Save successfull...");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }  
   
   public void loadLevel() {
      Level lvl;
      if (usr.getLevel() % 2 == 0) {
         lvl = new Level1();
      } else {
         lvl = new Level2();
      }
   
      bbls = lvl.getBubbles();
            
		player = new Player();
   }
   
   public void nextLevel() {
      save();
      bbls.removeAll(bbls);
      loadLevel();
   }
   
   public void endGame() {
      bbls.removeAll(bbls);
      try {
         new FileOutputStream("save.dat").close();
      } catch (Exception e) {
         e.printStackTrace();
      }
		
		Leaderboard lb = new Leaderboard();
		int yo = lb.addUser(usr);
      int selection;
		if (yo == 0)
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". Unforunately you did not make the leaderboards. \nWould you like to return to the main menu?", "Return To The Main Menu?", JOptionPane.YES_NO_OPTION);
		else if (yo == 1)
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the highest score.");
		else if (yo == 2) 
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the " + yo + "nd highest score.");
		else if (yo == 3) 
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the " + yo + "rd highest score.");
		else 
			selection = JOptionPane.showConfirmDialog(null, "Final Score: " + usr.getScore() + ". This is the " + yo + "th highest score.");
         
      if (selection == JOptionPane.YES_OPTION) {
         String[] args = {};
         BallAttack.main(args);
         frame.dispose();
     } else
     System.exit(0);
      
		ArrayList<User> lbs = lb.getLeaderboard();
		for (User user : lbs) {
			System.out.println(user);
		}
      
		
   }
}