public class Item extends Coordinate{

	private int height;
	private int width;
	
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
}
