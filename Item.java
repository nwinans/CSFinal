import java.awt.Color;
public abstract class Item extends Coordinate{

	private int height;
	private int width;
	private Color color;
	
	public Item(int x, int y, int w, int h, Color c) {	
		super(x, y);
		height = h;
		width = w;
		color = c;
	}
	
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
	
	public abstract void move(int direction);
}
