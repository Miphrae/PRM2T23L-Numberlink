package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Victory extends JFrame {

    public Victory() {
        super("Victory!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("Congratulations, you won!");
        titleLabel.setFont(new Font("Helvetica", Font.ITALIC, 20));
        add(titleLabel);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(closeButton);
        setSize(300, 100);
        setLocationRelativeTo(null);
    }

    public static void activate(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Victory victoryWindow = new Victory();
                victoryWindow.setVisible(true);
            }
        });
    }
}