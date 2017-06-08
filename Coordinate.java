public class Coordinate {
   
   private int x;
   private int y;
   
   //constructer takes an x and y value
   public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   //default constructer
   public Coordinate() {
      x = 0;
      y = 0;
   }
   
   //getters and setters
   
   
   public int getX() {
      return x;
   }
   
   public int getY() {
      return y;
   }
   
   public void setX(int x) {
      this.x = x;
   }
   
   public void setY(int y) {
      this.y = y;
   }
}