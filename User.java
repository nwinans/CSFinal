import java.io.Serializable;
public class User implements Serializable {

   private String player1;
   private String player2;
   
   private int totalScore;
   private int level;
   
   public User(String p1, String p2) {
      player1 = p1;
      player2 = p2;
      totalScore = 0;
      level = 0;
   }
   
   public int getLevel() {
      return level;
   }
   
   public void nextLevel() {
      level++;
   }
  
}

/*to save

ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
out.writeObject(user);
out.flush();
out.close();

to load

ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"));
User user = (User) in.readObject();
in.close();
*/
   