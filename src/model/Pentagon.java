package model;

public class Pentagon extends Polygon{
    private Polygon polygon = new Polygon();
    public void calculate(Point start, Point end) {
        int kolikaUhelnik = 5, secondAngle = (180*(kolikaUhelnik-2))/kolikaUhelnik, firstAngle = secondAngle/2;
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
        for (int i = 0; i < kolikaUhelnik-1; i++) {
            c = (float) (Math.sin(secondAngle)*a);
            b = (float) ((c/Math.sin(secondAngle))*Math.sin(secondAngle));
            int secondX = (int) (tmpX+b);
            int secondY = (int) (tmpY+c);
            polygon.addPoint(new Point(secondX, secondY));
            tmpX = secondX;
            tmpY = secondY;
        }
    }
    public Polygon getPolygon() {
        return polygon;
    }
}
