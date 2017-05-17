public class Rope extends Item {
	
	public Rope(int x, int y) {	
		super (x, y, 10, 20);
	}
	
	public void grow() {
		super.setHeight(getHeight() + 10);
		super.setY(getY() - 10);
	}
}
