public class Rope extends Item {
	
	public Rope(int x, int y) {	
		super (x, y, 10, 20);
	}
	
	public void move(int direction) {
		setHeight(getHeight() + 15);
		setY(getY() - 15);
	}
}
