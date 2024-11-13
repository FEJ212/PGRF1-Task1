package rasterize;

import model.Point;
import raster.Raster;

//Algoritmus pro vlastní rasterizaci bodu
public class PointRasterizer {
    protected final Raster raster;
    protected int color;
    protected int size;
    //konstruktor bez specifikování barvy
    public PointRasterizer(Raster raster, boolean thick) {
        this.raster = raster;
        this.color = 0xff0000;
        if (thick){
            this.size = 5;
        }else {
            this.size = 3;
        }
    }
    //konstruktor se specifikovanou barvou
    public PointRasterizer(Raster raster, int color, boolean thick) {
        this.raster = raster;
        this.color = color;
        if (thick){
            this.size = 5;
        }else {
            this.size = 3;
        }
    }

    //vykreslování bodu
    public void drawPoint(Point point) {
        int x = point.getX();
        int y = point.getY();
        raster.setPixel(x,y,color);
        for(int i = 1; i < size; i++){
            raster.setPixel(x+i,y+i,color);
            raster.setPixel(x-i,y-i,color);
            raster.setPixel(x-i,y+i,color);
            raster.setPixel(x+i,y-i,color);
        }
    }
}
