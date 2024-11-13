package fill;

import model.Point;
import raster.Raster;

public class SeedFiller implements Filler{

    private Raster raster;
    private int fillColor;
    private int backgroundColor;
    private int x,y;

    public SeedFiller(Raster raster, Point point, int fillColor){
        this.raster = raster;
        this.x = point.getX();
        this.y = point.getY();
        this.fillColor = fillColor;
        this.backgroundColor = raster.getPixel(x,y);
    }

    @Override
    public void fill() {
        seedFill(x,y);
    }

    private void seedFill(int x, int y){
        int pixelColor = raster.getPixel(x,y);
        if(pixelColor == backgroundColor && pixelColor != fillColor){
            raster.setPixel(x,y,fillColor);
            seedFill(x+1,y);
            seedFill(x-1,y);
            seedFill(x,y+1);
            seedFill(x,y-1);
        }
    }
}
