import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LeaderboardDisplay extends JPanel {
	
	private JFrame mainFrame;
	private Leaderboard leaderboard;
	
   private final int SPACING = 40;
   private final int FONT_SIZE = 20;
   
	public LeaderboardDisplay() {
		mainFrame = new JFrame("Leaderboards");
		leaderboard = new Leaderboard();
		
		//set the size of the frame to 400 width and 500 height
      mainFrame.setSize(400, 550);
		//make the frame visible (invisible by default)
      mainFrame.setVisible(true);
		
		mainFrame.add(this);
		
		//close when the close button is pressed
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
      g.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
      
      FontMetrics metrics = g.getFontMetrics(new Font("SansSerif", Font.BOLD, FONT_SIZE));
      
      g.drawString("Leaderboard", (getWidth() - metrics.stringWidth("Leaderboard"))  / 2, 30);
      
		ArrayList<User> users = leaderboard.getLeaderboard();
      
      for (int i = 0; i < users.size(); i ++) {
         User u = users.get(i);
         g.drawString(i+1 +  ") " + u.toString(), 10, 70 + (i * SPACING));
      }
	}
}