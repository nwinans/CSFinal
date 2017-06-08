import java.util.ArrayList;

public abstract class Level { 
   
   public Level() {
   }
   
	//get the bubbles (used by World when loading level)
   public abstract ArrayList<Bubble> getBubbles();
   
}