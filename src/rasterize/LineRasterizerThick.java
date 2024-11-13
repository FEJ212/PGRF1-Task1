package rasterize;

import model.Line;
import raster.Raster;
//Algoritmus pro vlastní rasterizaci tučné čáry
public class LineRasterizerThick extends LineRasterizer{
    //konstruktor bez specifikování barvy
    public LineRasterizerThick(Raster raster) {
        super(raster);
    }
    //konstruktor se specifickou barvou
    public LineRasterizerThick(Raster raster, int color) {
        super(raster, color);
    }
    //rasterizace čáry
    @Override
    public void drawLine(Line line) {
        //získání jednotlivých hodnot bodů
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();
        //vykreslování pro veškeré čáry kromě svislých
        if(x1!=x2) {
            //vypočítání úhlu čáry
            float k = (y1 - y2) / (float) (x1 - x2);
            float q = y1 - k * x1;
            //vykreslování přes hodnotu x
            if (k < 1.0F && k > -1.0F) {
                //zajištění že x1 bude menší než x2, abychom mohli čáru vykreslovat skrz jeden for loop místo dvou
                if (x1 > x2) {
                    int tmp = x2;
                    x2 = x1;
                    x1 = tmp;
                }
                //vykreslovací cyklus
                for (int i = x1; i <= x2; i++) {
                    float y = k * i + q;
                    raster.setPixel(i, Math.round(y), color);
                    raster.setPixel(i, Math.round(y) + 2, color);
                    raster.setPixel(i, Math.round(y) + 1, color);
                    raster.setPixel(i, Math.round(y) - 1, color);
                    raster.setPixel(i, Math.round(y) - 2, color);
                }
            //vykreslování přes hodnotu y
            } else {
                //zajištění že y1 bude menší než y2, abychom mohli čáru vykreslovat skrz jeden for loop místo dvou
                if (y1 > y2) {
                    int tmp = y2;
                    y2 = y1;
                    y1 = tmp;
                }
                //vykreslovací cyklus
                for (int i = y1; i <= y2; i++) {
                    float x = (i - q) / k;
                    raster.setPixel(Math.round(x), i, color);
                    raster.setPixel(Math.round(x) + 1, i, color);
                    raster.setPixel(Math.round(x) + 2, i, color);
                    raster.setPixel(Math.round(x) - 1, i, color);
                    raster.setPixel(Math.round(x) - 2, i, color);
                }
            }
        //vykreslování svislých čar (zamezení dělení nulou)
        }else {
            //zajištění že y1 bude menší než y2, abychom mohli čáru vykreslovat skrz jeden for loop místo dvou
            if (y1 > y2) {
                int tmp = y2;
                y2 = y1;
                y1 = tmp;
            }
            //vykreslovací cyklus
            for (int i = y1; i <= y2; i++) {
                raster.setPixel(x1, i, color);
                raster.setPixel(x1+1, i, color);
                raster.setPixel(x1+2, i, color);
                raster.setPixel(x1-1, i, color);
                raster.setPixel(x1-2, i, color);
            }
        }
    }
}
