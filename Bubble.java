import  java.util.Random;
import  javax.swing.JComponent;
import  java.awt.Color;
import  java.awt.Graphics;

public class Bubble extends JComponent{
   private Color color;
   private int size;
   
   private int x;
   private int y;
   
   public Bubble(int s) {
      size = s;
      x = 0;
      y = 0;
      Random rand = new Random();
      
      float r = rand.nextFloat();
      float g = rand.nextFloat();
      float b = rand.nextFloat();
      
      color = new Color(r,g,b);
   }
   
   public Bubble(int s, Color c) {
      size = s;
      color = c;
      
      x = 0;
      y = 0;
   }
   
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(color);
      g.fillOval(x, y, size * 20, size * 20);
   }
}  