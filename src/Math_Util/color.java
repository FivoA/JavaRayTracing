package Math_Util;

import java.io.BufferedWriter;
import java.io.IOException;

public class color extends vec3{
    public color(double x, double y, double z) {
        super(x, y, z);
    }
    public static color multiply(color a, double factor) {
        return new color(a.getX()*factor, a.getY()*factor, a.getZ()*factor);
    }
    public String toString(){
        return this.getX() + " " + this.getY() + " " + this.getZ();
    }
}
