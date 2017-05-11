import  java.awt.Color;
public class Player implements Item {
	private int x;
	private int y;
	
	private int timeToShoot;
	
	private final int SPEED = 8;
	private final int WINDOW_HEIGHT = 800;
	private final int HEIGHT = 100;
	private final int WIDTH = 50;
	
	private Color color;
	
	public Player() {
		x = 10;
		y = WINDOW_HEIGHT - HEIGHT - 10;
		timeToShoot = 0;
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
		return HEIGHT;
	}
	
	public int getWidth() {	
		return WIDTH;
	}
	
	public boolean canShoot() {	
		return timeToShoot == 0;
	}
	
	public void cooldown() {
		if (timeToShoot > 0) timeToShoot--;
	}
	
	public void shoot() {
		timeToShoot = 100;
	}
	
	public void moveRight() {
		x += SPEED;
	}
	
	public void moveLeft() {
		x -= SPEED;
	}	
}
	
