package render;

import model.Line;
import rasterize.LineRasterizer;
import solid.Solid;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.List;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private int width, height;
    public Renderer(LineRasterizer lineRasterizer, int width, int height) {
        this.lineRasterizer = lineRasterizer;
        this.width = width;
        this.height = height;
    }
    public void renderSolid(Solid solid){
        for (int i = 0; i < solid.getIb().size(); i+=2) {
            lineRasterizer.drawLine(new Line(transformToScreen(new Vec3D(solid.getVb().get(solid.getIb().get(i)))),transformToScreen(new Vec3D(solid.getVb().get(solid.getIb().get(i+1))))));
        }
    }
    private Vec3D transformToScreen(Vec3D point){
        return point.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width-1)/2., (height-1)/2., 1));
    }
    public void renderSolids(List<Solid> solids){
        for (int i = 0; i < solids.size(); i++) {
            renderSolid(solids.get(i));
        }
    }
    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
}
