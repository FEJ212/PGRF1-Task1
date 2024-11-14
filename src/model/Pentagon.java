package model;

public class Pentagon extends Polygon{
    private Polygon polygon = new Polygon();
    public void calculate(Point start, Point end) {
        //přidání počátečního bodu
        polygon.addPoint(new Point(end.getX(), end.getY()));
        //výpočet vzdálenosti ujeté myší - vzdálenost od středu po vrchol
        float rToEdge = (float) Math.sqrt((end.getX() - start.getX()) ^ 2 + (end.getY() - start.getY()) ^ 2);
        //výpočet délky strany
        float a = (float) (rToEdge / ((Math.sqrt(50 + 10 * (Math.sqrt(5)))) / 10));
        //výpočet délky mezi 2 nejvzdálenějšími vrcholy
        float d = (float) (((1+Math.sqrt(5))/2)*a);
        //výpočet vzdálenosti od středu po střed strany
        float rToSide = (float) ((Math.sqrt(25 + 10 * (Math.sqrt(5))) / 10)*a);
        //výpočet vzdálenosti od vrcholu po střed protilehlé strany
        float h = rToSide + rToEdge;
        //výpočet vzdálenosti vrcholu od středu úsečky d
        float v = (float) Math.sqrt((a*a)-((d/2)*(d/2)));
        //výpočet vzdálenosti středu strany od středu úsečky d
    }
    public Polygon getPolygon() {
        return polygon;
    }
}
