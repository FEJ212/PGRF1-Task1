package model;
//vlastní datový typ pro zaznamenání všech potřebných hodnot k zaznamenání bodu
public class Point {

    private final int x,y;
    //konstruktor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //gettery
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}

