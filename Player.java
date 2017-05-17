import  java.awt.Color;
public class Player extends Item {	
	private int timeToShoot;
	
	private final int SPEED = 8;
	
	private Color color;
	
	public Player() {
		super(10, 800 - 100 - 10, 50, 100);
		timeToShoot = 0;
	}
		
	public boolean canShoot() {	
		return timeToShoot == 0;
	}
	
	public void cooldown() {
		if (timeToShoot > 0) timeToShoot--;
	}
	
	public void shoot() {
		timeToShoot = 67;
	}
	
	public void moveRight() {
		super.setX(getX() + SPEED);
	}
	
	public void moveLeft() {
		super.setX(getX() - SPEED);
	}	
}
	
