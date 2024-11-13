package rasterize;

import model.Line;
import raster.Raster;
import raster.RasterBufferedImage;
import java.awt.*;
//lineRasterizer využivající již vytvořený algoritmus z knihovny java.awt
public class LineRasterizerGraphics extends LineRasterizer{
    //konstruktor bez specifikované barvy
    public LineRasterizerGraphics(Raster raster) {
        super(raster);
    }
    //konstruktor se specifikovanou barvou
    public LineRasterizerGraphics(Raster raster, int color) {
        super(raster, color);
    }
    //vykreslování čáry
    @Override
    public void drawLine(Line line) {
        Graphics g = ((RasterBufferedImage)raster).getGraphics();
        g.setColor(new Color(color)); //upravení barvy dle výběru
        g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2()); //nakreslení čáry
    }
}
