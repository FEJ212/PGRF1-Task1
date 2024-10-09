package rasterize;

import model.Line;
import java.awt.image.BufferedImage;
import raster.Raster;

public abstract class LineRasterizer {
    protected final Raster raster;
    protected int color;

    public LineRasterizer(Raster raster) {
        this.raster = raster;
        this.color = 0xffffff;
    }

    public LineRasterizer(Raster raster, int color) {
        this.raster = raster;
        this.color = color;
    }

    public void drawLine(Line line){

    }
}
