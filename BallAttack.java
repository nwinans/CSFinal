import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class BallAttack {
   private JButton btnStart;
   
   public BallAttack() {
      super(); 
		try {
			File file = new File("leaderboard.dat");

			if (!file.exists()) {
	    		if (file.createNewFile()){
	    			User user = new User("Nick");
					user.setScore(150);
				
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
   				out.writeObject(user);
   				out.flush();
   				out.close();
				}
	    	}
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
   }
   
   public static void main(String[] args) {
      new BallAttack();
      final JFrame mainFrame = new JFrame("Ball Attack!");
      
      JPanel centerPanel = new JPanel();
      centerPanel.setLayout(new BorderLayout());
      
      JButton startNewBtn = new JButton("Start New Game");
		startNewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try { 
	            File file = new File("save.dat");

	            if (file.createNewFile()){
	               System.out.println("File is created!");
	            }
               String name = JOptionPane.showInputDialog(mainFrame, "What's your name?", "What's your name?", JOptionPane.QUESTION_MESSAGE);
               if ((name != null) && (name.length() > 0)) {
					   User user = new User(name);
					   ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
   				   out.writeObject(user);
   				   out.flush();
   				   out.close();
					   mainFrame.dispose();
                  World world = new World(user);
               }
				} catch (Exception e) {
   				e.printStackTrace();
				}
			}
		});
      JButton continueBtn = new JButton("Continue Game");
      continueBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ev) {
            try {
               ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"));
               User user = (User) in.readObject();
               in.close();
               mainFrame.dispose();
               World world = new World(user);
            } catch (Exception e) {
					//any error that occurs will be because the file either doesnt exist or doesnt contain a user
					//either can be fixed by starting a new game which creates a file if necessary and inserts a user
					//as a result we don't use the Exception message and instead tell the user to start a new game
               JOptionPane.showMessageDialog(null, "There was an error loading the save file. This occurs when there is no save data. Try starting a new game!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
         }
      });
		
		JButton leaderboardBtn = new JButton("Leaderboards");
		leaderboardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				mainFrame.dispose();
				LeaderboardDisplay ld = new LeaderboardDisplay();
			}
		});
            
      centerPanel.add(startNewBtn, "East");
      centerPanel.add(continueBtn, "West");
		centerPanel.add(leaderboardBtn, "Center");
      
      JLabel title = new JLabel("Ball Attack");
      title.setHorizontalAlignment(JLabel.CENTER);
      title.setFont(new Font("Dialog", Font.PLAIN, 34));
      
      JButton exit = new JButton("Exit");
      exit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      
      mainFrame.getContentPane().add(title, "North");
      mainFrame.getContentPane().add(centerPanel, "Center");
      mainFrame.getContentPane().add(exit, "South");
      
		//set the size of the frame to 400 width and 300 height
      mainFrame.setSize(400, 300);
		//make the frame visible (invisible by default)
      mainFrame.setVisible(true);
		//close when the close button is pressed
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
