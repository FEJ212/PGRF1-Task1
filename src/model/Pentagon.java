package model;

public class Pentagon extends Polygon{
    private Polygon polygon = new Polygon();
    public Polygon calculate(Point start, Point end) {
        float r = (float) Math.sqrt((end.getX() - start.getX()) ^ 2 + (end.getY() - start.getY()) ^ 2);
        float a = (float) (r / ((Math.sqrt(50 + 10 * (Math.sqrt(5)))) / 10));
        int firstAngle = 54, secondAngle = 108, firstOppositeAngle = 180-90-firstAngle, secondOppositeAngle = 180-90-secondAngle;

        int firstX = ;
        int firstY = ;
        polygon.addPoint(new Point(firstX, firstY));
        for (int i = 0; i < 5; i++) {
            int secondX = ;
            int secondY = ;
            polygon.addPoint(new Point(secondX, secondY));
        }
        return polygon;
    }
}
