import  java.util.Random;
import  javax.swing.JComponent;
import  java.awt.Color;
import  java.awt.Graphics;

public class Bubble extends Item{ 
	//these values never change and we want to make it easer to adjust these values to balance the game, so they are constants
   private final int HORIZONTAL_MULTIPLIER = 2;
   private final int VERTICAL_MULTIPLIER = 1;
   private final int HEIGHT_MULTIPLIER = 3;
   private final int MAX_SPEED = 5;

   private int xDirection = 1;
   private int yDirection = 1;
	
	/* ball size, x coordinate, y coordinate, x direction (pos or neg), y direction (pos or neg)
		
		no color is supplied, so we create a new color with new Random().nextFloat() / 2f
		
		/ 2f divides the generated float by 2. this creates a darker color, preventing the ball
		from blending in with the background (a float of 1 is white whereas 0 is black)
	*/
	public Bubble(int s, int x, int y, int xDir, int yDir) {
		super(x, y, s * 20, s * 20, new Color(new Random().nextFloat()/2f, new Random().nextFloat()/2f, new Random().nextFloat()/2f));
		
		xDirection = xDir;
		yDirection = yDir;
	}
      
	//the width and the height are the same, so this method's name is shorter to type than getHeight or getWidth
   public int getWH() {
      return getHeight();
   }
      
	/*this returns how much the ball should move when the move method is called
		the smaller balls should travel faster, so we subtract the ball's level from max speed to create the inverse relationship
		we don't store the ball's size other than it's width and height so we divide the width by the same amount we multiplied the size
		by to get the width, in this case 20. 
		the horizontal multiplier is a contstant defined above and changing it allows us to speed up or slow down the balls for testing 
		and balancing the game. x Direction can be either positive or negative. A positive x direction moves the ball to the right and a 
		negative number moves the ball to the left	
	*/
   public int getHorizontalSpeed() {
      return (MAX_SPEED - (getWidth() / 20)) * HORIZONTAL_MULTIPLIER * xDirection;
   }
   
   public int getVerticalSpeed() {
      return (MAX_SPEED - (getWidth() / 20)) * VERTICAL_MULTIPLIER * yDirection;
   }
	
	//the main move method - we dont use direction becuase we have independent values for the x and y direction, not an overarching one
	public void move(int direction) {
		setX(getX() + getHorizontalSpeed());
		setY(getY() + getVerticalSpeed());
	}
         
	//returns x direction - this is used when the ball is hit by the rope and we need to know what way the ball was originally headed to
	//determine which way the 2 resulting balls should travel
   public int getXDirection() {
      return xDirection;
   }
   
	//flip the x direction - used when the ball hits the player, the rope, or the walls
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
