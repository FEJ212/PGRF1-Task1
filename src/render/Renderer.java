package render;

import model.Line;
import model.Point;
import rasterize.LineRasterizer;
import solid.Solid;

import java.util.List;

public class Renderer {
    private LineRasterizer lineRasterizer;
    public Renderer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
    public void renderSolid(Solid solid){
        for (int i = 0; i < solid.getIb().size(); i+=2) {
            lineRasterizer.drawLine(new Line(solid.getVb().get(solid.getIb().get(i)),solid.getVb().get(solid.getIb().get(i+1))));
        }
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
