import java.io.Serializable;
public class User implements Serializable {

   private final int MAX_LIVES = 5;

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
		return player + ", Score: " + getScore();
	}
}

/*to save
try {
   ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
   out.writeObject(user);
   out.flush();
   out.close();
} catch (Exception e) {
   e.printStackTrace();

}
to load
try {
   ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"));
   User user = (User) in.readObject();
   in.close();
} catch (Exception e) {
   e.printStackTrace();
}
*/
   