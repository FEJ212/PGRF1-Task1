package rasterize;

import model.Line;
import raster.Raster;
//abstraktní třída pro různé typy line rasterizeru
public abstract class LineRasterizer {
    protected final Raster raster;
    protected int color;
    //konstruktor bez specifikování barvy
    public LineRasterizer(Raster raster) {
        this.raster = raster;
        this.color = 0xffffff;
    }
    //konstruktor se specifikovanou barvou
    public LineRasterizer(Raster raster, int color) {
        this.raster = raster;
        this.color = color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    //metoda pro vykreslení čáry
    public void drawLine(Line line){

    }
}
