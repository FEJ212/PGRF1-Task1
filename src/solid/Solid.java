package solid;

import transforms.Col;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Solid {
    protected List<Point3D> vb = new ArrayList<>();
    protected List<Integer> ib = new ArrayList<>();
    protected Mat4 model = new Mat4Identity();
    protected Col color = new Col(0xffffff);

    public List<Point3D> getVb() {
        return vb;
    }

    public List<Integer> getIb() {
        return ib;
    }

    protected void addIndices(Integer... indices){
        ib.addAll(Arrays.asList(indices));
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    public void setVb(List<Point3D> vb) {
        this.vb = vb;
    }

    public void setIb(List<Integer> ib) {
        this.ib = ib;
    }
}
