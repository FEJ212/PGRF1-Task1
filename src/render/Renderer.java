package render;

import model.Line;
import raster.Raster;
import rasterize.LineRasterizer;
import solid.*;
import transforms.*;

import java.util.List;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private final Raster raster;
    private Mat4 view, proj;
    public Renderer(LineRasterizer lineRasterizer, Raster raster) {
        this.lineRasterizer = lineRasterizer;
        this.raster = raster;
        this.view = new Mat4Identity();
        this.proj = new Mat4Identity();
    }
    public void renderSolid(Solid solid){
        for (int i = 0; i < solid.getIb().size(); i+=2) {
            int indexA = solid.getIb().get(i);
            int indexB = solid.getIb().get(i+1);

            Point3D pointA = solid.getVb().get(indexA);
            Point3D pointB = solid.getVb().get(indexB);

            pointA = pointA.mul(solid.getModel()).mul(view).mul(proj);
            pointB = pointB.mul(solid.getModel()).mul(view).mul(proj);

            if(!checkPoint(pointA)){
                continue;
            }
            if(!checkPoint(pointB)){
                continue;
            }

            Point3D dehomA = pointA.mul(1/pointA.getW());
            Point3D dehomB = pointB.mul(1/pointB.getW());

            Vec3D pointAInScreen = transformToScreen(new Vec3D(dehomA));
            Vec3D pointBInScreen = transformToScreen(new Vec3D(dehomB));

            lineRasterizer.setColor(solid.getColor().getRGB());
            lineRasterizer.drawLine(new Line(pointAInScreen,pointBInScreen));
        }
    }
    private Vec3D transformToScreen(Vec3D point){
        return point.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((raster.getWidth()-1)/2., (raster.getHeight()-1)/2., 1));
    }
    public void renderSolids(List<Solid> solids){
        for (Solid solid : solids) {
            renderSolid(solid);
        }
    }
    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }
    public void renderAxis(Axis x, Axis y, Axis z){
        renderSolid(x);
        renderSolid(y);
        renderSolid(z);
    }

    public boolean checkPoint(Point3D point){
        double x = point.getX();
        double y = point.getY();
        double z = point.getZ();
        double w = point.getW();
        return -w <= x && x <= w && -w <= y && y <= w && 0 <= z && z <= w;
    }

}
