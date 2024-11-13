package rasterize;

import model.Line;
import model.Point;
import model.Polygon;
import raster.RasterBufferedImage;
//Algoritmus pro vlastní rasterizaci polygonu
public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;
    //konstruktor
    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
    //vykreslování polygonu
    public void rasterize(Polygon polygon) {
        //pokud má polygon délku pod 2, tak se vykresluje jako line, ne jako polygon
        if(polygon.size()<2)
            return;
        //vykreslování všech čar přes cyklus
        for (int i = 0; i < polygon.size(); i++) {
            //výběr 2 bodů mezi kterými vykreslit čáru
            int indexA = i;
            int indexB = i+1;
            //zajištění, že se poslední bod spojí s prvním
            if (indexB==polygon.size()){
                indexB=0;
            }
            //získání informací o vybraných bodech
            Point pointA = polygon.getPoint(indexA);
            Point pointB = polygon.getPoint(indexB);
            //vykreslení čáry
            lineRasterizer.drawLine(new Line(pointA, pointB));
        }
    }
    //setter nastavení linerasterizeru (využito pro tučný polygon)
    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
}
