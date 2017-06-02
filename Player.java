import  java.awt.Color;

public class Player extends Item {	
	//time to shoot is essentially a cooldown
	private int timeToShoot;
	
	private final int SPEED = 15;
		
	public Player() {
		//x coordinate, y coordinate, width, height, color
		super(10, 800 - 100, 90, 100, Color.red);
		//cooldown of 0 means the player can shoot the rope
		timeToShoot = 0;
	}
		
	//check to see if the cooldown is 0 meaning the rope can be shot
	public boolean canShoot() {	
		return timeToShoot == 0;
	}
	
	//this method is run every cycle of the "ropeTimer" timer in the world class. we handle whether the cooldown actually needs to be decremented in this class
	public void cooldown() {
		if (timeToShoot > 0) timeToShoot--;
	}
	
	//the rope has been shot, so a cooldown is created
	public void shoot() {
		timeToShoot = 60;
	}
	
	//direction will either be positive or negative, so we move right when direction is positive
	public void move(int direction) {
		setX(getX() + (direction * SPEED));
	}
}
	
