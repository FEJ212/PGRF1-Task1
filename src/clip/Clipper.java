package clip;

import model.Point;

import java.util.ArrayList;
import java.util.List;

public class Clipper {
    public List<Point> clip(List<Point> clipperPoints, List<Point> pointsToClip){
        List<Point> resultPoints = new ArrayList<>();

        Point cP1 = clipperPoints.get(0);
        Point cP2 = clipperPoints.get(1);

        //Spočítat vekktor k bodu, skalární součin
        Point t = new Point(cP2.getX()-cP1.getX(), cP2.getY()-cP1.getY());
        Point n = new Point(t.getY(),(-t.getX()));
        Point v = new Point(0-cP1.getX(), 0-cP1.getY());
        //Point ss = new Point(Math.cos(v.getX()*n.getX()+v.getY()*n.getY()));

        for(int i = 0; i < clipperPoints.size(); i++){
            Point p1 = pointsToClip.get(i);
            //pro každý point se ptá zda je např. vlevo(POZOR NA ORIENTACI POLYGONŮ)
        }

        return resultPoints;
    }
}
