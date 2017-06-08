import java.util.ArrayList;
public class Level2 extends Level {
      
   public Level2() {
      super();
      //add 4 bubbles that all start in the same place roughly and have x and y directions of 1 and 1
         }
   
    public ArrayList<Bubble> getBubbles() {
      ArrayList<Bubble> bbls = new ArrayList<Bubble>();
      bbls.add(new Bubble(1, 80, 80, 1, 1));
		bbls.add(new Bubble(2, 90, 90, 1, 1));
		bbls.add(new Bubble(3, 100, 100, 1, 1));
      bbls.add(new Bubble(4, 110, 110, 1, 1));
      return bbls;
   }
   
}