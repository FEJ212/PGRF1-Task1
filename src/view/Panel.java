package view;

import raster.Raster;
import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel{

    private final Raster raster;
    //konstruktor panelu
    public Panel(int width, int height){
        setPreferredSize(new Dimension(width, height));

        raster = new RasterBufferedImage(width, height);
    }
    //metoda pro funkčnost java.awt vykreslování (lineGraphics)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((RasterBufferedImage)raster).paint(g);
    }
    //čištění rasteru
    public void clear(){
        raster.clear();
    }
    //getter rasteru
    public Raster getRaster() {
        return raster;
    }

}
