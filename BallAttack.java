import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class BallAttack {
   private JButton btnStart;
   
   public BallAttack() {
      super();
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
					User user = new User("Nick","Drew");
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
   				out.writeObject(user);
   				out.flush();
   				out.close();
					mainFrame.dispose();
               World world = new World(user);
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
               e.printStackTrace();
            }
         }
      });
            
      centerPanel.add(startNewBtn, "East");
      centerPanel.add(continueBtn, "West");
      
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
      
      mainFrame.setSize(400, 300);
      mainFrame.setVisible(true);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
