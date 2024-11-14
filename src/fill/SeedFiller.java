package fill;

import model.Point;
import raster.Raster;
import java.util.ArrayList;

public class SeedFiller implements Filler{

    private Raster raster;
    private int fillColor = 0xb00b69;
    private int backgroundColor;
    private int x,y;
    private ArrayList<Point> seedFillQueue = new ArrayList<>();

    public SeedFiller(Raster raster, Point point){
        this.raster = raster;
        this.x = point.getX();
        this.y = point.getY();
        this.backgroundColor = raster.getPixel(x,y);
    }

    @Override
    public void fill() {
        seedFill(x,y);
    }

    private void seedFill(int x, int y){
        if(backgroundColor!=fillColor) { //nutnost upravit, protože Java je kokot a 2 stejné barvy registruje jako 2 ruzná čísla, jelikož fuck you thats why
            seedFillQueue.addLast(new Point(x, y));
            while (!seedFillQueue.isEmpty()) {
                x = seedFillQueue.getFirst().getX();
                y = seedFillQueue.getFirst().getY();
                int pixelColor = raster.getPixel(x, y);
                if (pixelColor == backgroundColor && pixelColor != fillColor) {
                    raster.setPixel(x, y, fillColor);
                    if (x < raster.getWidth() - 1) seedFillQueue.addLast(new Point(x + 1, y));
                    if (x > 1) seedFillQueue.addLast(new Point(x - 1, y));
                    if (y < raster.getHeight() - 1) seedFillQueue.addLast(new Point(x, y + 1));
                    if (y > 1) seedFillQueue.addLast(new Point(x, y - 1));
                }
                seedFillQueue.removeFirst();
            }
        }
    }
}
