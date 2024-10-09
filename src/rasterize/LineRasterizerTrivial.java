package rasterize;

import model.Line;
import raster.Raster;

import java.awt.image.BufferedImage;

public class LineRasterizerTrivial extends LineRasterizer{
    public LineRasterizerTrivial(Raster raster) {
        super(raster);
    }

    public LineRasterizerTrivial(Raster raster, int color) {
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        float k = (y1-y2)/(float)(x1-x2);
        float q = y1-k*x1;
        if (k<1.0F&&k>-1.0F) {
            if (x1>x2) {
                int tmp = x2;
                x2 = x1;
                x1 = tmp;
            }
            for (int i = x1; i <= x2; i++) {
                float y = k * i + q;
                raster.setPixel(i, Math.round(y), color);
            }
        } else{
            if (y1>y2) {
                int tmp = y2;
                y2 = y1;
                y1 = tmp;
            }
            for (int i = y1; i <= y2; i++) {
                float x = (i-q)/k;
                raster.setPixel(Math.round(x), i, color);
            }
        }

    }
}
