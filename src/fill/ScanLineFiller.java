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


    public ScanLineFiller(Polygon polygon, LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer) {
        this.polygon = polygon;
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        lineRasterizer.setColor(0x69b00b);
    }

    @Override
    public void fill() {
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Integer> intersections = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++){
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i+1) % polygon.size());
            Edge e = new Edge(p1, p2);
            if(!e.isHorizontal()) {
                e.oriantate();
                edges.add(e);
            }
        }
        int yMin = edges.getFirst().getY1();
        int yMax = edges.getFirst().getY2();
        for (Edge edge : edges) {
            if (edge.getY1() < yMin) yMin = edge.getY1();
            if (edge.getY2() > yMax) yMax = edge.getY2();
        }
        for(int i = yMin; i <= yMax; i++){
            intersections.clear();
            for (Edge edge : edges) {
                if(edge.isIntersection(i)){
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