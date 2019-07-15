package ui;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    Window window;
    public static JTextField scoreField;
    public static JTextField lineField;

    public ScorePanel(Window window){
        this.window = window;
        setBackground(Color.lightGray);
        setSize(Config.WIDTH * Config.SIZE, Config.SCORE_HEIGHT);
        setBounds(0,Config.HEIGHT * Config.SIZE +10, Config.WIDTH * Config.SIZE, Config.SCORE_HEIGHT);

        scoreField = new JTextField("СЧЁТ : 0");
        //scoreField.setBounds();
        lineField = new JTextField("РЯДЫ : 0");
        this.add(scoreField);
        this.add(lineField);
        setLayout(null);
        window.add(this);
    }
}
