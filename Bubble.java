import  java.util.Random;
import  javax.swing.JComponent;
import  java.awt.Color;
import  java.awt.Graphics;

public class Bubble extends Item{ 
   private final int HORIZONTAL_MULTIPLIER = 2;
   private final int VERTICAL_MULTIPLIER = 1;
   private final int HEIGHT_MULTIPLIER = 3;
   private final int MAX_SPEED = 5;

   private int xDirection = 1;
   private int yDirection = 1;

   private Color color;
      
   public Bubble(int s) {
		super ((int) (Math.random() * 1000.0), (int) (Math.random() * 600.0), s * 20, s * 20);

      Random rand = new Random();
      
      float r = rand.nextFloat();
      float g = rand.nextFloat();
      float b = rand.nextFloat();
      
      color = new Color(r,g,b);
   }
	
	public Bubble(int s, int x, int y, int xDir, int yDir) {
		super(x, y, s * 20, s * 20);
		
		xDirection = xDir;
		yDirection = yDir;
		Random rand = new Random();
      
      float r = rand.nextFloat() / 2f;
      float g = rand.nextFloat() / 2f;
      float b = rand.nextFloat() / 2f;
      
      color = new Color(r,g,b);
	}
		
   
   public Bubble(int s, Color c) {
      super ((int) (Math.random() * 1000.0), (int) (Math.random() * 800.0), s * 20, s * 20);
      color = c;
   }
      
   public int getWH() {
      return getHeight();
   }
   
   public Color getColor() {
      return color;
   }
   
   public int getHorizontalSpeed() {
      return (MAX_SPEED - (getWidth() / 20)) * HORIZONTAL_MULTIPLIER * xDirection;
   }
   
   public int getVerticalSpeed() {
      return (MAX_SPEED - (getWidth() / 20)) * VERTICAL_MULTIPLIER * yDirection;
   }
	
	public void move(int direction) {
		setX(getX() + getHorizontalSpeed());
		setY(getY() + getVerticalSpeed());
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
