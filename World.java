import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.TimerTask;
import java.util.ArrayList;

public class World extends JPanel{

   private User usr;
   private JFrame frame;
   private ArrayList<Bubble> bbls;
   
   public World(User user) {
      usr = user;
      
      frame = new JFrame("Level " + usr.getLevel());
      frame.setSize(1200, 800);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      frame.add(this);
      frame.setResizable(false);
               
      bbls = new ArrayList<Bubble>(4);
      
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
               for (int j = i + 1; j < bbls.size(); j++) {
                  Bubble b = bbls.get(j);
                  Ellipse2D.Double bubble2 = new Ellipse2D.Double(b.getX(), b.getY(), b.getWH(), b.getWH());
                  if (bubble1.getBounds2D().intersects(bubble2.getBounds2D())){
                     if (Math.min(Math.abs((bbl.getX() + bbl.getWH()) - (b.getX())), Math.abs((bbl.getX()) - (b.getX() + b.getWH()))) < Math.min(Math.abs((bbl.getY() + bbl.getWH()) - (b.getY())), Math.abs((bbl.getY()) - (b.getY() + b.getWH())))) {
                        //x collision
                        bbl.flipXDirection();
                        b.flipXDirection();
                     } else {
                        //y collision
                        bbl.flipYDirection();
                        b.flipYDirection();  
                     }
                  }
               }
               bbl.setX(bbl.getX() + bbl.getHorizontalSpeed());
               bbl.setY(bbl.getY() + bbl.getVerticalSpeed());
               }
            repaint();
         }
      });
      
      tmr.start();
      
      frame.setVisible(true);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      for (Bubble bbl : bbls) {
         g.setColor(bbl.getColor());
         g.fillOval(bbl.getX(), bbl.getY(), bbl.getWH(), bbl.getWH());
      }
   }
}
 