import java.util.ArrayList;

public class Level { 
   
   //private int lvl;
   private ArrayList<Bubble> bbls;
   
   public Level() {
      bbls = new ArrayList<Bubble>();
   }
   
   public void addBubble(Bubble bbl) {
      bbls.add(bbl);
   }
   
   public ArrayList<Bubble> getBubbles() {
      return bbls;
   }
   
}