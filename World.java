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
               
      bbls = new ArrayList<Bubble>(4);
      
		player = new Player();
		
		frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
	
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_LEFT)
					if (player.getX() > 0)
						player.moveLeft();
				if (code == KeyEvent.VK_RIGHT)
					if (player.getX() + player.getWidth() < frame.getWidth())
						player.moveRight();
				if (code == KeyEvent.VK_UP)
					if (player.canShoot()) {
						rope = new Rope((player.getX() * 2 + player.getWidth() - 10) / 2, player.getY() - 20);
                  player.shoot();
               }
				repaint();
			}
	
			public void keyReleased(KeyEvent e) {
			}
		});
				
      for (int i = 1; i < 5; i++) {
         Bubble bbl = new Bubble(i);
         bbls.add(bbl);
      }
      
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
                     bbls.removeAll(bbls);
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
               bbl.setX(bbl.getX() + bbl.getHorizontalSpeed());
               bbl.setY(bbl.getY() + bbl.getVerticalSpeed());
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
						rope.grow();
						Rectangle bounds = new Rectangle(rope.getX(), rope.getY(), rope.getWidth(), rope.getHeight());
               	for (int j = 0; j < bbls.size(); j++) {
                  	Bubble b = bbls.get(j);
                  	Ellipse2D.Double bubbleBounds = new Ellipse2D.Double(b.getX(), b.getY(), b.getWH(), b.getWH());
                  	if (bounds.getBounds2D().intersects(bubbleBounds.getBounds2D())){
								if (b.getWH() / 20 - 1 == 0) {
									rope = null;
									bbls.remove(j);
                           if (bbls.size() == 0) save();
                           usr.setScore(usr.getScore() + 4);
                           frame.setTitle(usr.getScore() + "");
									break;
								}
                        if (Math.min(Math.abs((rope.getX() + rope.getWidth()) - (b.getX())), Math.abs((rope.getX()) - (b.getX() + b.getWH()))) < Math.min(Math.abs((rope.getY() + rope.getHeight()) - (b.getY())), Math.abs((rope.getY()) - (b.getY() + b.getWH())))) {
                           //x collision
								   if (b.getWH() / 20 - 1 != 0) {
									   Bubble bb1 = new Bubble((b.getWH() / 20) - 1, b.getX(), b.getY(), -b.getXDirection(), -b.getYDirection());
									   Bubble bb2 = new Bubble((b.getWH() / 20) - 1, b.getX(), b.getY(), -b.getXDirection(), b.getYDirection());
									   bbls.add(bb1);
									   bbls.add(bb2);
									   rope = null;
									   bbls.remove(j);
                              //System.out.println((5 - (b.getWH() / 20)));
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
		g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
      
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
}
 
