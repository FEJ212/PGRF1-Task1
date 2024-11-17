package fill;

import model.Point;
import raster.Raster;
import java.util.ArrayList;

public class SeedFiller implements Filler{

    private Raster raster;
    private int fillColor = 0xb00b69; //barva kterou seedFill vyplňuje
    private int backgroundColor; //zjištění barvy kterou má seedFill překreslit
    private int x,y; //souřadnice zpracovávaného bodu
    private ArrayList<Point> seedFillQueue = new ArrayList<>(); //fronta využívaná k úspěšnému vyplnění oblasti bez přetečení paměti
    //konstruktor
    public SeedFiller(Raster raster, Point point){
        this.raster = raster;
        this.x = point.getX();
        this.y = point.getY();
        this.backgroundColor = raster.getPixel(x,y);
    }
    //zahájení algoritmu
    @Override
    public void fill() {
        seedFill(x,y);
    }
    //algoritmus
    private void seedFill(int x, int y){
        //Bugfix opravující cituaci že chceme překreslit oblast stejné barvy jakou vyplňujeme
        if(backgroundColor!=fillColor) {
            //přidání prvního bodu do fronty
            seedFillQueue.addLast(new Point(x, y));
            //zpracování fronty
            while (!seedFillQueue.isEmpty()) {
                //nastavení souřadnice prvním bodem z fronty
                x = seedFillQueue.getFirst().getX();
                y = seedFillQueue.getFirst().getY();
                //zjištění jaké barvy je pixel na dané souřadici
                int pixelColor = raster.getPixel(x, y);
                //podmínka rozhodující zda vyplnění proběhne nebo ne
                if (pixelColor == backgroundColor && pixelColor != fillColor) {
                    //přebarvení pixelu
                    raster.setPixel(x, y, fillColor);
                    //přidání okolních bodů do fronty, pokud nejsou mimo raster
                    if (x < raster.getWidth() - 1) seedFillQueue.addLast(new Point(x + 1, y));
                    if (x > 1) seedFillQueue.addLast(new Point(x - 1, y));
                    if (y < raster.getHeight() - 1) seedFillQueue.addLast(new Point(x, y + 1));
                    if (y > 1) seedFillQueue.addLast(new Point(x, y - 1));
                }
                //odstranění zpracovávaného bodu z fronty
                seedFillQueue.removeFirst();
            }
        }
    }
}
