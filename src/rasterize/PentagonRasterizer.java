package rasterize;

import model.Point;

public class PentagonRasterizer{
    private LineRasterizer lineRasterizer;
    public PentagonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
    public void rasterize(Point start, Point end){
        float r = (float) Math.sqrt((end.getX()- start.getX())^2+(end.getY()- start.getY())^2);
        float a = (float) (r/((Math.sqrt(50+10*(Math.sqrt(5))))/10));

    }
}
