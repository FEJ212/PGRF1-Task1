package model;

import java.util.ArrayList;
import java.util.List;

//vlastní datový typ pro zaznamenání všech potřebných hodnot k zaznamenání polygonu
public class Polygon {
    //array list zaznamenávající vrcholy polygonu
    private final List<Point> points;
    //konstruktor
    public Polygon() {
        points = new ArrayList<>();
    }
    //konstruktor pro clipper
    public Polygon(List<Point> points) {
        this.points = points;
    }
    //možnost přidání vrcholu na konec ArrayListu (přidání posledního bodu)
    public void addPoint(Point p){
        points.add(p);
    }
    //možnost smazat jakýkoliv vrchol polygonu (nepoužito)
    public void deletePoint(int i){
        points.remove(i);
    }
    //getter specifikovaných vrcholů
    public Point getPoint(int i){
        return points.get(i);
    }
    //získání počtu vrcholů v polygonu
    public int size(){
        return points.size();
    }
    public List<Point> getPoints(){
        return points;
    }
}
