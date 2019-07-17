package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RepeatFrame extends JFrame {

    public static boolean userAnswer;

    public RepeatFrame(){
        setTitle("Game over!!Would you like to repeat game?");
        setSize(400,200);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton yesBtn = new JButton("Of course I want!!!");
        yesBtn.setBounds(50,30,180,100);
        JButton noBtn = new JButton("NO");
        noBtn.setBounds(250,30,100,100);
        panel.add(yesBtn);
        panel.add(noBtn);

        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(this);
        setVisible(true);

        yesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userAnswer = true;
                dispose();
                AuxiliaryWindow.window.dispose();
                AuxiliaryWindow auxiliaryWindow = new AuxiliaryWindow();
            }
        });

        noBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                dispose();
            }
        });
    }
}
