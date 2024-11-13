package raster;

import java.awt.*;
import java.awt.image.BufferedImage;
//Raster do kterého program zakresluje čáry
public class RasterBufferedImage implements Raster{

    private final BufferedImage image;
    //konstruktor
    public RasterBufferedImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    //změna barvy jednoho specifického pixelu
    @Override
    public void setPixel(int x, int y, int value){
        if(x<getWidth()&&y<getHeight()&&x>=0&&y>=0) {
            image.setRGB(x, y, value);
        }
    }
    //getter barvy jednoho specifického pixelu
    @Override
    public int getPixel(int x, int y) {
        return image.getRGB(x,y);
    }
    //getter šířky Rasteru
    @Override
    public int getWidth() {
        return image.getWidth();
    }
    //getter výšky Rasteru
    @Override
    public int getHeight() {
        return image.getHeight();
    }
    //vyčištění Rasteru
    @Override
    public void clear() {
        Graphics g = image.getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public void paint(Graphics g){
        g.drawImage(image, 0, 0, null);
    }

    public Graphics getGraphics() {
        return image.getGraphics();
    }

}
