import java.io.Serializable;
//serializable allows us to save this object in a file (enables leaderboards and saving/continuing the game
public class User implements Serializable {

	//initial amount of lives the user has
   private final int MAX_LIVES = 5;

	//player name
   private String player;
   
   private int totalScore;
   private int level;
   
   private int lives;
   
   public User(String p) {
      player = p;
      totalScore = 0;
      level = 1;
      lives = MAX_LIVES;
   }
   
   public int getLevel() {
      return level;
   }
   
   public void nextLevel() {
      level++;
   }
   
   public int getLives() {
      return lives;
   }
   
   public void loseLife() {
      lives--;
   }
   
   public void addLife() {
      lives++;
   }

   public int getScore() {
      return totalScore;
   }
   
   public void setScore(int score) {
      totalScore = score;
   }
	
	public void incrementScore(int score) {
		totalScore += score;
	}
	
	public String toString() {
		return player + " - Score: " + getScore() + " - Level: " + getLevel();
	}
}
