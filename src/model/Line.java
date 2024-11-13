package model;
//vlastní datový typ pro zaznamenání všech potřebných hodnot k zaznamenání úsečky
public class Line {
    private final int x1, y1, x2, y2;
    //konstruktor pro 4 zadané body
    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    //konstruktor pro 2 zadané body
    public Line(Point a, Point b){
        this.x1 = a.getX();
        this.y1 = a.getY();
        this.x2 = b.getX();
        this.y2 = b.getY();
    }
    //gettery
    public int getX1() {
        return x1;
    }
    public int getY2() {
        return y2;
    }
    public int getX2() {
        return x2;
    }
    public int getY1() {
        return y1;
    }
}
