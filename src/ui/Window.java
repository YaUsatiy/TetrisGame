package ui;

import model.Coord;
import service.FlyFigure;
import service.Mapable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Window extends JFrame implements Runnable, Mapable{

    private Box[][] boxes;
    private FlyFigure flyFigure;
    private Timer timer;
    private static boolean paused = false;
    public int score = 0;
    public int dropLines = 0;

    public Window(){
        boxes = new Box[Config.WIDTH][Config.HEIGHT];
        initForm();
        initBoxes();
        addScore();
        addKeyListener(new FieldKeyListener());
        FieldTimeListener fieldTimeListener = new FieldTimeListener();
        if (AuxiliaryWindow.easy)
            timer = new Timer(750, fieldTimeListener);
        else if (AuxiliaryWindow.medium)
            timer = new Timer(300, fieldTimeListener);
        else
            timer = new Timer(150, fieldTimeListener);
        timer.start();
    }

    private void initForm(){
        setSize(Config.WIDTH*Config.SIZE + 13,
                Config.HEIGHT*Config.SIZE + 37 + Config.SCORE_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
        if (AuxiliaryWindow.easy)
            setTitle("THE Tetris : easy");
        else if (AuxiliaryWindow.medium)
            setTitle("THE Tetris : medium");
        else
            setTitle("THE Tetris : difficult");
        setLayout(null);
        setResizable(false);
    }

    private void initBoxes(){
        for (int x = 0; x < Config.WIDTH; x++) {
            for (int y = 0; y < Config.HEIGHT; y++){
                boxes[x][y]= new Box(x,y);
                add(boxes[x][y]);
            }
        }
        setVisible(true);
    }

    private void addScore(){
        ScorePanel scorePanel = new ScorePanel(this);
    }

    public void addFigure(){
        flyFigure = new FlyFigure(this);// т.к Window implements Mapable
        //System.out.println(flyFigure.toString());
        if (flyFigure.canPlaceFigure()) {
            showFigure();
        }
        else {
            timer.stop();
            timer = null;
            RepeatFrame repeatFrame = new RepeatFrame();//в поле класса вынести
        }
        showFigure();
    }

    private void showFigure(){
        showFigure(1);
    }

    private void hideFigure(){
        showFigure(0);
    }

    private void showFigure(int color){
        for (Coord dot : flyFigure.getFigure().dots)
            setBoxColor(flyFigure.getCoord().x + dot.x, flyFigure.getCoord().y + dot.y, color);
    }

    public int getBoxColor(int x, int y){
        if (x < 0 || x >= Config.WIDTH) return -1;
        if (y < 0 || y >= Config.HEIGHT) return -1;
        return boxes[x][y].getColor();
    }

    void setBoxColor(int x, int y, int color){
        if (x < 0 || x >= Config.WIDTH) return;
        if (y < 0 || y >= Config.HEIGHT) return;
        boxes[x][y].setColor(color);
    }

    private void moveFly(int sx, int sy){
        hideFigure();
        flyFigure.moveFigure(sx, sy);
        showFigure();
    }

    private void turnFly(int direction){
        hideFigure();
        flyFigure.turnFigure(direction);
        showFigure();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key){
                case (KeyEvent.VK_LEFT)  : moveFly(-1, 0); break;
                case (KeyEvent.VK_RIGHT) : moveFly(+1, 0); break;
                case (KeyEvent.VK_S)     : moveFly(0, +1); break;
                case (KeyEvent.VK_W)     : moveFly(0, -1); break;
                case (KeyEvent.VK_SPACE) : {
                    if (!paused){
                        timer.stop();
                        paused = true;
                    }
                    else {
                        timer.start();
                        paused = false;
                    }
                    break;
                }
                case (KeyEvent.VK_UP)    : turnFly(2);
                case (KeyEvent.VK_DOWN)  : moveFly(0, +1); break;
            }
        }
    }

    private void stripLines(){
        for (int y = Config.HEIGHT - 1; y >= 0; y--) {
            while (isFullLine(y)){
                dropLine(y);
            }
        }
    }

    private void dropLine(int y){
        for (int my = y - 1; my >= 0; my--)
            for (int x = 0; x < Config.WIDTH; x++)
                setBoxColor(x, my+1, getBoxColor(x, my));
        for (int x = 0; x < Config.WIDTH; x++)
            setBoxColor(x, 0, 0);
    }

    private boolean isFullLine(int y){
        for (int x = 0; x < Config.WIDTH; x++)
            if (getBoxColor(x,y) != 2)
                return false;
            score += Config.LINE_SCORE;
            dropLines++;
            ScorePanel.scoreField.setText("  СЧЁТ : " + score + "  ");
            ScorePanel.lineField.setText("  РЯДЫ : " + dropLines + "  ");
            return true;
    }

    @Override
    public void run() {
        repaint();
        revalidate();
    }

    class FieldTimeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            moveFly(0, 1);
            if (flyFigure.isLanded()){
                showFigure(2);

                score += Config.FIGURE_SCORE;
                ScorePanel.scoreField.setText("  CЧЁТ : " + score + "  ");

                stripLines();
                addFigure();
            }
        }
    }
}
