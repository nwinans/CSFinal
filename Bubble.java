import  java.util.Random;
import  javax.swing.JComponent;
import  java.awt.Color;
import  java.awt.Graphics;

public class Bubble extends JComponent{
   private final int HORIZONTAL_MULTIPLIER = 2;
   private final int VERTICAL_MULTIPLIER = 1;
   private final int HEIGHT_MULTIPLIER = 3;
   private final int MAX_SPEED = 5;

   private int xDirection = 1;
   private int yDirection = 1;

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
      
   public int getWH() {
      return size * 20;
   }
   
   public Color getColor() {
      return color;
   }
   
   public int getHorizontalSpeed() {
      return (MAX_SPEED - size) * HORIZONTAL_MULTIPLIER * xDirection;
   }
   
   public int getVerticalSpeed() {
      return (MAX_SPEED - size) * VERTICAL_MULTIPLIER * yDirection;
   }
   
   public int getMaxHeight() {
      return size * HEIGHT_MULTIPLIER;
   }
   
   public int getX() {
      return x;
   }
   
   public int getY() {
      return y;
   }
   
   public void setY(int y2) {
      y = y2;
   }
   
   public void setX(int x2) { 
     x = x2;
   }
   
   public int getXDirection() {
      return xDirection;
   }
   
   public void flipXDirection() {
      xDirection *= -1;
   }
   
   public int getYDirection() {
      return yDirection;
   }
   
   public void flipYDirection() {
      yDirection *= -1;
   }
}  