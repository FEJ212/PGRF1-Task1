package model;

public class Pentagon extends Polygon{
    public void calculate(Point start, Point end) {
        //výpočet vzdálenosti ujeté myší - vzdálenost od středu po vrchol
        float rToEdge = (float) Math.sqrt(Math.pow((start.getX() - end.getX()),2) + (Math.pow((start.getY() - end.getY()),2)));
        //výpočet délky strany
        float sideLenght = (float) (rToEdge / ((Math.sqrt(50 + 10 * (Math.sqrt(5)))) / 10));
        //výpočet úhlu na základě toho, kolikúhelník zpracováváme
        int angle = (int) ((start.getY() - end.getY()) / (float) (start.getX() - end.getX()));;
        //výpočet pozice bodů
        for (int i = 0; i < 5; i++) {
            super.addPoint(new Point((int) (rToEdge * Math.sin(angle + (i * Math.PI / 2.5f))) + start.getX(),
                    (int) (rToEdge * Math.cos(angle + (i * Math.PI / 2.5f))) + start.getY()));
        }
    }
}
