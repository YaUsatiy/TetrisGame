package service;

import model.Coord;
import model.Figures;
import ui.Config;

public class FlyFigure {

    private Figures figure;
    private Coord coord;
    private boolean landed;
    Mapable map;

    public FlyFigure(Mapable map){
        this.map = map;
        figure = Figures.getRandom();
        coord = new Coord(Config.WIDTH/2 -1,-1);
        landed = false;
    }

    public Figures getFigure() {
        return figure;
    }

    public Coord getCoord() {
        return coord;
    }

    public boolean isLanded(){
        return landed;
    }

    @Deprecated
    public String toString(){
        return "y = " + (figure.top.y) +" - " + (figure.bot.y) +" -1";
    }

    public boolean canPlaceFigure(){
        return canMoveFigure(figure,0, 0);
    }

    private boolean canMoveFigure(Figures figure, int sx, int sy){
        if (coord.x + sx + figure.top.x < 0) return false;//!!!!!!!!!!!!!!!!HZ!!!HZ!!HZ!!
        if (coord.x + sx + figure.bot.x >= Config.WIDTH) return false;
        //if (coord.y + sy + figure.top.y < 0) return false;
        if (coord.y + sy + figure.bot.y >= Config.HEIGHT) return false;

        //после проверки координат
        for (Coord dot : figure.dots)
            if (map.getBoxColor(coord.x + dot.x + sx, coord.y + dot.y + sy ) > 0)
                return false;
        return true;
    }

    public void moveFigure(int sx, int sy){
        if (canMoveFigure(figure, sx, sy))
            coord = coord.plus(sx, sy);
        else {
            if (sy == 1)
                landed = true;
        }
    }

    public void turnFigure(int direction){
        Figures rotated = direction == 1 ? figure.turnRight(): figure.turnLeft();
        if (!canMoveFigure(rotated, 0, 0)) return;
        figure = rotated;
    }
}
