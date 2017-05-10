import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class BubbleTrouble extends JFrame {
   private JButton btnStart;
   
   public BubbleTrouble() {
      super();
      
      /*btnStart = new JButton("Start");
      
      this.setTitle("BubbleTouble");
      this.setSize(800,600);
      
      this.add(btnStart);
      
      this.setVisible(true);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);*/
   }
   
   public static void main(String[] args) {
      new BubbleTrouble();
      JFrame mainFrame = new JFrame("Bubble Trouble");
      
      JPanel centerPanel = new JPanel();
      centerPanel.setLayout(new BorderLayout());
      
      JButton startNewBtn = new JButton("Start New Game");
      JButton continueBtn = new JButton("Continue Game");
      
      centerPanel.add(startNewBtn, "East");
      centerPanel.add(continueBtn, "West");
      
      JLabel title = new JLabel("Bubble Trouble");
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
      mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      /*User usr = new User("Nick", "Drew");
      usr.nextLevel();
      System.out.println(usr.getLevel());
      try {
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"));
         out.writeObject(usr);
         out.flush();
         out.close();
      } catch (Exception e) {
         e.printStackTrace();
      }*/
      
      try {
         ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"));
         User user = (User) in.readObject();
         in.close();
         user.nextLevel();
         System.out.println(user.getLevel());
      } catch (Exception e) {
         e.printStackTrace();
      }
    }
}