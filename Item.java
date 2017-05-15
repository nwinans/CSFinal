public class Item {
	private int x;
	private int y;
	
	private int height;
	private int width;
	
	public Item(int xCor, int yCor, int w, int h) {	
		x = xCor;
		y = yCor;
		height = h;
		width = w;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int xCor) {
		x = xCor;
	}
	
	public void setY(int yCor) {
		y = yCor;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void incrementX(int a) {
		x += a;
	}
	
	public void incrementY(int a) {
		y += a;
	}
	
	public void setHeight(int h) {	
		height = h;
	}
}
