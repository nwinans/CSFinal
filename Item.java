import java.awt.Color;
public abstract class Item extends Coordinate{

	//width, height, and color (x and y are stored in coordinate class)
	private int height;
	private int width;
	private Color color;
	
   //default constructer
	public Item(int x, int y, int w, int h, Color c) {	
		super(x, y);
		height = h;
		width = w;
		color = c;
	}
	
   //getters and setters
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
		
	public void setHeight(int h) {	
		height = h;
	}
	
	public Color getColor() {
		return color;
	}
	
	//abstract move method that is implemented in ball player and rope (int handles whether object should move left or right (only player uses this)
	public abstract void move(int direction);
}
