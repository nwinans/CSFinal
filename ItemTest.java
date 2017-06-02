   import org.junit.Assert;
   import org.junit.Before;
   import org.junit.Test;
	import java.util.ArrayList;

   public class ItemTest {
	
		private ArrayList<Item> items;

      /** Fixture initialization (common initialization
       *  for all tests). **/
      @Before public void setUp() {
			items = new ArrayList<Item>();
			items.add(new Player());
			items.add(new Rope(30, 60));
			items.add(new Bubble(3, 30, 30, 1, 1));
      }
		
		@Test public void ropeTest() {
			Assert.assertEquals(items.get(1).getHeight(), 20);
			items.get(1).move(1);
			Assert.assertEquals(items.get(1).getHeight(), 35);
			Assert.assertEquals(items.get(1).getY(), 45);
		}
   }
