import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class BubbleTrouble extends JFrame {
   private JButton btnStart;
   
   public BubbleTrouble() {
      super();
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
    }
}