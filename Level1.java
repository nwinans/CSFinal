import java.util.ArrayList;
public class Level1 extends Level {
      
   public Level1() {
      super();
   }
   
   public ArrayList<Bubble> getBubbles() {
      ArrayList<Bubble> bbls = new ArrayList<Bubble>();
      bbls.add(new Bubble(3, 20, 20, 1, 1));

      return bbls;
   }

   
}