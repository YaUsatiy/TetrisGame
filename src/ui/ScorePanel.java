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
        setBounds(0,Config.HEIGHT * Config.SIZE +10, Config.WIDTH * Config.SIZE, Config.SCORE_HEIGHT);
        scoreField = new JTextField("  СЧЁТ : 0 ");
        scoreField.setColumns(9);
        scoreField.setEditable(false);
        lineField = new JTextField("  РЯДЫ : 0 ");
        lineField.setColumns(7);
        lineField.setEditable(false);
        add(scoreField);
        add(lineField);
        window.add(this);
    }

}
