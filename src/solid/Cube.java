package solid;

import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid{
    public Cube() {
        vb.add(new Point3D(-0.5, -0.5, 0)); // 0
        vb.add(new Point3D(0.5, -0.5, 0)); // 1
        vb.add(new Point3D(0.5, 0.5, 0)); // 2
        vb.add(new Point3D(-0.5, 0.5, 0)); // 3
        vb.add(new Point3D(-0.5, -0.5, 1)); // 4
        vb.add(new Point3D(0.5, -0.5, 1)); // 5
        vb.add(new Point3D(0.5, 0.5, 1)); // 6
        vb.add(new Point3D(-0.5, 0.5, 1)); // 7
        addIndices(0, 1, 1, 2, 2, 3, 3, 0); // front face
        addIndices(4, 5, 5, 6, 6, 7, 7, 4); // back face
        addIndices(0, 4, 1, 5, 2, 6, 3, 7); // four side faces

        color = new Col(0xFFFFFF);
    }
    @Override
    public String getIdentifier() {
        return "CUBE";
    }
}
