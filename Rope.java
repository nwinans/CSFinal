import java.awt.Color;

public class Rope extends Item {
	
	public Rope(int x, int y) {	
		//x coordinate, y coordinate, width, height, color
		super (x, y, 10, 20, Color.blue);
	}
	
	public void move(int direction) {
		//rope extends up by 15 pixels at at time. if we just set the height to be higher, 
		//it would extend downwards, so we adjust the y position by the same amount
		//this makes the rope appear to extend upwards
		setHeight(getHeight() + 15);
		setY(getY() - 15);
	}
}
