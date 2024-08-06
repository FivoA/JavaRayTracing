package Math_Util;

import java.io.BufferedWriter;
import java.io.IOException;

public class color extends vec3{
    public color(double x, double y, double z) {
        super(x, y, z);
    }
    public color(){
        super();
    }
    public static void writeColor(BufferedWriter bw, vec3 color) throws IOException {
        double r = color.getX();
        double g = color.getY();
        double b = color.getZ();
        int rbyte = (int)(r*255.999);
        int gbyte = (int)(g*255.999);
        int bbyte = (int)(b*255.999);
        bw.write(rbyte+" ");
        bw.write(gbyte+" ");
        bw.write(bbyte+"\n");
    }
}
