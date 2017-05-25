import java.awt.Color;
public abstract class Item extends Coordinate{

	private int height;
	private int width;
	private Color color;
	
	public Item(int x, int y, int w, int h) {	
		super(x, y);
		height = h;
		width = w;
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
	
	public abstract void move(int direction);
}
