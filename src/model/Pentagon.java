package model;

public class Pentagon extends Polygon{
    private Polygon polygon = new Polygon();
    public Polygon calculate(Point start, Point end) {
        int firstAngle = 54, secondAngle = 108, firstOppositeAngle = 180-90-firstAngle, secondOppositeAngle = 180-90-secondAngle;
        float r = (float) Math.sqrt((end.getX() - start.getX()) ^ 2 + (end.getY() - start.getY()) ^ 2);
        float a = (float) (r / ((Math.sqrt(50 + 10 * (Math.sqrt(5)))) / 10));
        float b,c,tmpX,tmpY;
        c = (float) (Math.sin(firstAngle)*a);
        b = (float) ((c/Math.sin(firstAngle))*Math.sin(firstAngle));
        int firstX = (int) (end.getX()+b);
        int firstY = (int) (end.getY()+c);
        polygon.addPoint(new Point(firstX, firstY));
        tmpX = end.getX();
        tmpY = end.getY();
        for (int i = 0; i < 5; i++) {
            c = (float) (Math.sin(firstAngle)*a);
            b = (float) ((c/Math.sin(firstAngle))*Math.sin(firstAngle));
            int secondX = (int) (tmpX+b);
            int secondY = (int) (tmpY+c);
            polygon.addPoint(new Point(secondX, secondY));
            tmpX = secondX;
            tmpY = secondY;
        }
        return polygon;
    }
}
