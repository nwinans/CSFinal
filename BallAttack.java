import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class BallAttack {
	//main constructor
   public BallAttack() {
      super(); 
		//file and filestreams throw java.io.ioexceptions, so we have to handle that exception through a try/catch block
		try {
			//create a new variable with the leaderboard file name - this doesn't do anything yet
			//all it does it hold a reference to where we want the eventual file to be created
			File file = new File("leaderboard.dat");

			//if the leaderboard doesnt exist, we want to create a default one
			if (!file.exists()) {
	    		if (file.createNewFile()){
	    			User user = new User("Nick");
					user.setScore(150);
					//add the user with score of 150 to the leaderboard as a default entry
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
   				out.writeObject(user);
               //flus makes sure we won't lose any data when we close the output stream. it flushes stuff out of the buffer
               //we probably dont need it, but just to be safe...
   				out.flush();
   				out.close();
				}
	    	}
		} catch (java.io.IOException ex) {
			//print out the exception's stack trace in the comments
			//the stack trace is essentially every method/call in the heirarchy leading up to the error, including the line numbers
			//this shows you exactly what went wrong, where
			ex.printStackTrace();
		}
   }
   
   public static void main(String[] args) {
		//call the ball attack class - this creates the leaderboard file if needed, as shown above
      new BallAttack();
		
		//create a new jFrame - this has to be declared final because we reference it in another method below
      final JFrame mainFrame = new JFrame("Ball Attack!");
      
		//create a JPanel that will be displayed in the center of the overall frame - this will hold the continue, leaderboard, and new game buttons
      JPanel centerPanel = new JPanel();
		
		//set the layout manager of the panel to a borderlayout
      centerPanel.setLayout(new BorderLayout());
      
		//create a new button that will start a new game on click
      JButton startNewBtn = new JButton("Start New Game");
		
		//add an actionlistnener to the start button that creates a new user and starts the game
		//we do this through an anonymous class because this action listener is only applicable to this
		//button and this makes the code easier to follow in my opinion
		startNewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try { 
					//create a new file variable that holds the location (the local directory) of where we want to save the file, and the name (save.dat)
					//again, this doesn't actually do anything yet, its just a reference
	            File file = new File("save.dat");
					//create the save.dat file if it doesn't already exist
					if (!file.exists()) {
	            	if (file.createNewFile()){
	              		System.out.println("File is created!");
	            	}
					}
					
					//show an input dialog where the user can enter their name - we store what the user enters in the variable "name"
					//--showInputDialog(parent, message, title, messageType)
               String name = JOptionPane.showInputDialog(mainFrame, "What's your name?", "What's your name?", JOptionPane.QUESTION_MESSAGE);
					//if the user actually entered a name into the box and didnt cancel or press enter with a blank name...
               if ((name != null) && (name.length() > 0)) {
						//create a new user with the given name. user takes a string as it's only argument
					   User user = new User(name);
						//create a new objectoutputstream - this is essentially the way to save objects to a file
					   ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
						//write the user to the file "save.dat"
   				   out.writeObject(user);
						//close the objectoutputstream-you need to do this every time you use an input or output stream after you're done using it
   				   out.flush();
   				   out.close();
						//close the current window without ending the program
					   mainFrame.dispose();
						//create a new instance of world - world is where the game is actually run
                  World world = new World(user);
               }
				} catch (Exception e) {
   				e.printStackTrace();
				}
			}
		});
		
		//create a new button that the user can click to continue a previously saved game
      JButton continueBtn = new JButton("Continue Game");
		//add the action listner so we can do something when it's clicked
      continueBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ev) {
            try {
					//create the inputstream to read information from the file
               ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"));
					//we only ever have one user at a time, so we load the first user
					//this will throw an exception if there isn't actually a user present in the file
					//this exception is handled below
               User user = (User) in.readObject();
					//close the input stream
               in.close();
					//close the current window without ending the program
               mainFrame.dispose();
               World world = new World(user);
            } catch (Exception e) {
					//any error that occurs will be because the file either doesnt exist or doesnt contain a user
					//either can be fixed by starting a new game which creates a file if necessary and inserts a user
					//as a result we don't use the Exception message and instead tell the user to start a new game
               JOptionPane.showMessageDialog(mainFrame, "There was an error loading the save file. This occurs when there is no save data. Try starting a new game!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
         }
      });
		
		//create a new button that will diplay the leaderboard on the leaderboard screen
		JButton leaderboardBtn = new JButton("Leaderboards");
		
		//add the action listener to go to the leaderboard class/screen
		leaderboardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//close the current screen without ending the program
				mainFrame.dispose();
				//create the leaderboarddisplay class which handles displaying the leaderboard, very original name
				LeaderboardDisplay ld = new LeaderboardDisplay();
			}
		});
            
		//"East" puts the start button on the right side of the screen
      centerPanel.add(startNewBtn, "East");
		//"West" puts the start button on the left side of the screen
      centerPanel.add(continueBtn, "West");
		//"Center" puts the button in the center of the screen
		centerPanel.add(leaderboardBtn, "Center");
      
		//create a new label that has the text "Ball Attack"
      JLabel title = new JLabel("Ball Attack");
		//center the label horizontally in the frame
      title.setHorizontalAlignment(JLabel.CENTER);
		//set the font to a bold, size 40, sans serif font
      title.setFont(new Font("SansSerif", Font.BOLD, 40));
      
		//setup the exit button that will end the program on tap
      JButton exit = new JButton("Exit");
		//add the action listener to actually exit the program
      exit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
				//end the program
            System.exit(0);
         }
      });
      
		//add the title to the top of the page, the buttons to the center, and the exit button to the bottom
      mainFrame.getContentPane().add(title, "North");
      mainFrame.getContentPane().add(centerPanel, "Center");
      mainFrame.getContentPane().add(exit, "South");
      
		//set the size of the frame to 500 width and 400 height
      mainFrame.setSize(500, 400);
		//make the frame visible (invisible by default)
      mainFrame.setVisible(true);
		//close when the close button is pressed
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//make the frame so the user cannot change the size
		mainFrame.setResizable(false);
    }
}
