package model;
//třída využívaná k algoritmu ScanLine
public class Edge {
    private int x1,x2,y1,y2;
    //konstruktory
    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public Edge(Point p1, Point p2){
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }
   //getters
    public int getX1() {
        return x1;
    }
    public int getY2() {
        return y2;
    }
    public int getY1() {
        return y1;
    }
    public int getX2() {
        return x2;
    }
    public boolean isHorizontal(){
        return y1==y2;
    }
    //seřazení 2 bodů mezi sebou, pokud je y2 menší než y1
    public void oriantate(){
        if(y2<y1) {
            int tmpY = y1;
            int tmpX = x1;
            this.x1 = x2;
            this.x2 = tmpX;
            this.y1 = y2;
            this.y2 = tmpY;
        }
    }
    //zjištění, zda linka prochází mezi body a má tak tedy mezi nimi průnik s přímkou
    public boolean isIntersection(int y){
        return y1<=y&&y2>y;
    }
    //vypočítání x souřadnice průniku
    public int getIntersection(int y){
        float k = (x2 - x1) / (float)(y2 - y1);
        float q = x1 - k * y1;
        return Math.round(k*y + q);
    }
}
