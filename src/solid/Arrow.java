package solid;

import transforms.Point3D;

public class Arrow extends Solid{
    public Arrow(){
        vb.add(new Point3D(0,0,0));
        vb.add(new Point3D(0.8,0,0));
        vb.add(new Point3D(0.8,-0.2,0));
        vb.add(new Point3D(1,0,0));
        vb.add(new Point3D(0.8,0.2,0));
        addIndices(
                0,1,
                4,2,
                2,3,
                3,4
        );
    }
}
