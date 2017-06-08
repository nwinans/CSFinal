import java.io.Serializable;
//serializable allows us to save this object in a file (enables leaderboards and saving/continuing the game)
public class User implements Serializable {

	//initial amount of lives the user has
   private final int MAX_LIVES = 5;

	//player name
   private String player;
   
   private int totalScore;
   private int level;
   
   private int lives;
   
   //constructer
   public User(String p) {
      player = p;
      totalScore = 0;
      level = 1;
      lives = MAX_LIVES;
   }
   
   //returns level
   public int getLevel() {
      return level;
   }
   
   //increments level
   public void nextLevel() {
      level++;
   }
   
   //return the amount of lives the player has
   public int getLives() {
      return lives;
   }
   
   //a ball hit the loser - remove a life
   public void loseLife() {
      lives--;
   }
   
   //the user completed a level, add a life
   public void addLife() {
      lives++;
   }
   
   //return the score
   public int getScore() {
      return totalScore;
   }
   
   //set the score to a specific value
   public void setScore(int score) {
      totalScore = score;
   }
	
   //add the given int to the score
	public void incrementScore(int score) {
		totalScore += score;
	}
	
   //to string used to create leaderboards
   //formatted like:
   // Name - Score:000 - Level: 000
	public String toString() {
		return player + " - Score: " + getScore() + " - Level: " + getLevel();
	}
}
