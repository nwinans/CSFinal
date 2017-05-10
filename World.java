import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
          
      //remove this later, its just annoying me
      frame.setAlwaysOnTop( false );
      
      bbls = new ArrayList<Bubble>(4);
      
      for (int i = 1; i < 5; i++) {
         Bubble bbl = new Bubble(i);
         bbls.add(bbl);
      }
      
      Timer tmr = new Timer(10, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for (Bubble bbl : bbls) {
            bbl.setX(bbl.getX() + bbl.getHorizontalSpeed());
            bbl.setY(bbl.getY() + bbl.getVerticalSpeed());
            if (bbl.getX() + bbl.getWH() > getWidth() || bbl.getX() < 0)
               bbl.flipXDirection();
            if (bbl.getY() + bbl.getWH() > getHeight() || bbl.getY() < 0)
               bbl.flipYDirection();
               }
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
      for (Bubble bbl : bbls) {
      g.setColor(bbl.getColor());
      g.fillOval(bbl.getX(), bbl.getY(), bbl.getWH(), bbl.getWH());
      }
   }
}
 