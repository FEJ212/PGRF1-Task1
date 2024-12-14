package solid;

import transforms.*;

public class Curve extends Solid{
    public Curve(){
        int n = 100;
        for (int i = 0; i < n; i++){
            float x = (i/(float)n)*2-1;
            float z = (float) Math.sin(5*Math.PI*x);

            vb.add(new Point3D(x,0,z));
            if (i!=n){
                ib.add(i);
                ib.add(i+1);
            }
        }
        color = new Col(0xFFFFFF);
    }
    @Override
    public String getIdentifier() {
        return "CURVE";
    }
}
