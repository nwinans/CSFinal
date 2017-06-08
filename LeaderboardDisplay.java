import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LeaderboardDisplay extends JPanel {
	
	//the frame that holds the panel
	private JFrame mainFrame;
   
	private Leaderboard leaderboard;
	
	//space between entries
   private final int SPACING = 40;
	//variable to change font size easily
   private final int FONT_SIZE = 20;
   
	public LeaderboardDisplay() {
		//create frame with title "Leaderboards"
		mainFrame = new JFrame("Leaderboards");
		//load the leaderboards into the leaderboard file
		leaderboard = new Leaderboard();
		
		//set the size of the frame to 400 width and 500 height
      mainFrame.setSize(400, 550);
		//use absolute layout
      setLayout(null);
      
		//make the frame visible (invisible by default)
      mainFrame.setVisible(true);
		//add this to the frame (this is essentially a jpanel because we extend from it 
		mainFrame.add(this);
		//we use an absolute layout, so we don't want the user to be able to resize the frame
      mainFrame.setResizable(false);
		//create the back button and add it to the panel
      JButton btn = new JButton("Back");
      add(btn);
		//set the x, y, width, height of the back button
      btn.setBounds(150, 450, 100, 50);
		//go back to BallAttack.java on click
      btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
            String[] args = {};
            BallAttack.main(args);
         }
      });
   
		//close when the close button is pressed
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//font is black
		g.setColor(Color.black);
		//set the font as bold and of family sanserif
      g.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
      
		//font metrics allows us to estimate how big a string will be with a given family, style and size (the width and height of the resulting label from a string)
      FontMetrics metrics = g.getFontMetrics(new Font("SansSerif", Font.BOLD, FONT_SIZE));
      
		//draw the "leaderboard" title centered horizontally
      g.drawString("Leaderboard", (getWidth() - metrics.stringWidth("Leaderboard"))  / 2, 30);
      
		//get the arraylist of records from the leaderboard class/file
		ArrayList<User> users = leaderboard.getLeaderboard();
      
		//loop through the users and draw them on the screen
		//the format will look like
		//1) Nick - Score: 20 - Level: 2
		//the x value is always 10
		//the y value is 70 (accounts for the leaderboard title) and then the spacing * the current value of i to account for subsequent entries
      for (int i = 0; i < users.size(); i ++) {
         User u = users.get(i);
         g.drawString(i+1 +  ") " + u.toString(), 10, 70 + (i * SPACING));
      }
	}
}