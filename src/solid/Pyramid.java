package solid;

import transforms.Col;
import transforms.Point3D;

public class Pyramid extends Solid{
    public Pyramid() {
        vb.add(new Point3D(-2, 0, 0)); // 0
        vb.add(new Point3D(-1, 0, 0)); // 1
        vb.add(new Point3D(-1.5, 0.5, Math.sqrt(2)/2)); // 2
        vb.add(new Point3D(-2, 1, 0)); // 3
        vb.add(new Point3D(-1, 1, 0)); // 4
        addIndices(0, 1, 1, 2, 2, 0); // front face
        addIndices(3, 4, 4, 2, 2, 3); // back face
        addIndices(0, 3, 1, 4); // bottom face
        color = new Col(0xFFFFFF);
    }
    @Override
    public String getIdentifier() {
        return "PYRAMID";
    }
}
