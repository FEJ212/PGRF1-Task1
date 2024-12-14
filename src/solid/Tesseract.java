package solid;

import transforms.Col;
import transforms.Point3D;

public class Tesseract extends Solid{
    public Tesseract() {
        vb.add(new Point3D(-1, -1, -1)); // 0
        vb.add(new Point3D(1, -1, -1)); // 1
        vb.add(new Point3D(1, 1, -1)); // 2
        vb.add(new Point3D(-1, 1, -1)); // 3
        vb.add(new Point3D(-1, -1, 1)); // 4
        vb.add(new Point3D(1, -1, 1)); // 5
        vb.add(new Point3D(1, 1, 1)); // 6
        vb.add(new Point3D(-1, 1, 1)); // 7

        vb.add(new Point3D(-0.5, -0.5, -0.5)); // 8
        vb.add(new Point3D(0.5, -0.5, -0.5)); // 9
        vb.add(new Point3D(0.5, 0.5, -0.5)); // 10
        vb.add(new Point3D(-0.5, 0.5, -0.5)); // 11
        vb.add(new Point3D(-0.5, -0.5, 0.5)); // 12
        vb.add(new Point3D(0.5, -0.5, 0.5)); // 13
        vb.add(new Point3D(0.5, 0.5, 0.5)); // 14
        vb.add(new Point3D(-0.5, 0.5, 0.5)); // 15

        addIndices(0, 1, 1, 2, 2, 3, 3, 0); // front outer face
        addIndices(4, 5, 5, 6, 6, 7, 7, 4); // back outer face
        addIndices(0, 4, 1, 5, 2, 6, 3, 7); // four outer side faces

        addIndices(8, 9, 9, 10, 10, 11, 11, 8); // front inner face
        addIndices(12, 13, 13, 14, 14, 15, 15, 12); // back inner face
        addIndices(8, 12, 9, 13, 10, 14, 11, 15); // four inner side faces

        addIndices(0, 8, 1, 9, 2, 10, 3, 11, 4, 12, 5, 13, 6, 14, 7, 15); // inner and outer faces link

        color = new Col(0xFFFFFF);
    }
    @Override
    public String getIdentifier() {
        return "TESSERACT";
    }
}
