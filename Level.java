import java.util.ArrayList;

public class Level { 
   
   private ArrayList<Item> bbls;
   
   public Level() {
      bbls = new ArrayList<Item>();
   }
   
	//add bubble to the array (used by classes that extend level)
   public void addBubble(Bubble bbl) {
      bbls.add(bbl);
   }
   
	//get the bubbles (used by World when loading level)
   public ArrayList<Item> getBubbles() {
      return bbls;
   }
   
}