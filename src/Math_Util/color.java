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
    public static vec3 tonemapAndGammaCorrect(vec3 x){
        return pow( clamp( divide(multiply(x,add(multiply(x,2.51),0.03)),add(multiply(x,add(multiply(x,2.43), 0.59) ),0.14 )), 0.0,1.0),new vec3(1.0/2.2));

    }
}
