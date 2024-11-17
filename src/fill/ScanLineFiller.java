package fill;

import model.Edge;
import model.Line;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.PolygonRasterizer;
import java.util.ArrayList;
import java.util.Comparator;
import static Utils.Sort.bubbleSort;

public class ScanLineFiller implements Filler{
    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private Polygon polygon;
    //konstruktor
    public ScanLineFiller(Polygon polygon, LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer) {
        this.polygon = polygon;
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        lineRasterizer.setColor(0x69b00b);
    }
    //algoritmus
    @Override
    public void fill() {
        //vytvoření listů pro zaznamenávání hran polygonu a bodů průniku
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Integer> intersections = new ArrayList<>();
        //cyklus přidávající veškeré hrany polygonu do jejich seznamu
        for (int i = 0; i < polygon.size(); i++){
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i+1) % polygon.size());
            Edge e = new Edge(p1, p2);
            //vynechávání vertikálních hran, pro správné fungování algoritmu
            if(!e.isHorizontal()) {
                //orientace přímky požadovaným směrem
                e.oriantate();
                //přidání do ArrayListu
                edges.add(e);
            }
        }
        //vytvoření proměných pro nejmenší a největší Y pozici, abychom algoritmus prováděli jen mezi nimi a ne zbytečně pro celý raster
        int yMin = edges.getFirst().getY1();
        int yMax = edges.getFirst().getY2();
        //for each procházející seznam hran a zaznamenávání nejmenší a největší Y hodnoty
        for (Edge edge : edges) {
            if (edge.getY1() < yMin) yMin = edge.getY1();
            if (edge.getY2() > yMax) yMax = edge.getY2();
        }
        //vyplňovací cyklus procházející všechny Y hodnoty mezi Y min a Y max
        for(int i = yMin; i <= yMax; i++){
            //vyčištění listu průniků
            intersections.clear();
            //for each procházející všechny hrany a hledající průniky
            for (Edge edge : edges) {
                //zjištění zda dochází k průniku
                if(edge.isIntersection(i)){
                    //výpočet hodnoty x průniku
                    intersections.add(edge.getIntersection(i));
                }
            }
            //seřadit průsečíky zleva doprava
            intersections = bubbleSort(intersections);
            //alternativa => intersections.sort(Comparator.naturalOrder());
            //vykreslit úsečku mezi každým sudým a lichým průsečíkem
            for(int j = 0; j < intersections.size(); j+=2){
                lineRasterizer.drawLine(new Line(intersections.get(j), i, intersections.get(j+1), i));
            }
        }
        //vykreslit hranici polygonu
        polygonRasterizer.rasterize(polygon);
    }
}