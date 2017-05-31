import  java.awt.Color;
public class Player extends Item {	
	private int timeToShoot;
	
	private final int SPEED = 15;
	
	private Color color;
	
	public Player() {
		super(10, 800 - 100, 90, 100);
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
		setX(getX() + SPEED);
	}
	
	public void moveLeft() {
		setX(getX() - SPEED);
	}	
	
	public void move(int direction) {
		setX(getX() + (direction * SPEED));
	}
}
	
