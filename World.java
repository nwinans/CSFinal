import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;


public class World extends JPanel{

   private User usr;
   private JFrame frame;
   private Bubble bbl;
   
   public World(User user) {
      usr = user;
      
      frame = new JFrame("Level " + usr.getLevel());
      
      frame.setSize(400, 200);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
      frame.add(this);
          
      bbl = new Bubble(4);
      
      Timer tmr = new Timer(10, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            bbl.setX(bbl.getX() + bbl.getHorizontalSpeed());
            bbl.setY(bbl.getY() + bbl.getVerticalSpeed());
            if (bbl.getX() + bbl.getWH() > getWidth() || bbl.getX() < 0)
               bbl.flipXDirection();
            if (bbl.getY() + bbl.getWH() > getHeight() || bbl.getY() < 0)
               bbl.flipYDirection();
            repaint();
         }
      });
      
      tmr.start();
      
                 /*Timer tmr = new Timer();
      tmr.schedule(new TimerTask() {
         public void run() {
            , 1);*/
      frame.setVisible(true);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(bbl.getColor());
      g.fillOval(bbl.getX(), bbl.getY(), bbl.getWH(), bbl.getWH());
   }
}
 