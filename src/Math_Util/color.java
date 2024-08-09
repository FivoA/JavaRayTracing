package Math_Util;

import java.io.BufferedWriter;
import java.io.IOException;

public class color extends vec3{
    public color(double x, double y, double z) {
        super(x, y, z);
    }
    public static color add(color a, color b) {
        return new color(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
    }
    public static color multiply(color a, double factor) {
        return new color(a.getX()*factor, a.getY()*factor, a.getZ()*factor);
    }
    public static color divide(color a, double factor) {
        return new color(a.getX() / factor, a.getY() / factor, a.getZ() / factor);
    }
    public String toString(){
        return this.getX() + " " + this.getY() + " " + this.getZ();
    }
    public static vec3 tonemapAndGammaCorrect(vec3 x){
        vec3 a = new vec3(2.51);
        vec3 b = new vec3(0.03);
        vec3 c = new vec3(2.43);
        vec3 d = new vec3(0.59);
        vec3 e = new vec3(0.14);
        double gamma = 1.0 / 2.2;
        // Apply tone mapping
        vec3 numerator = multiply(x, add(multiply(x, a), b));
        vec3 denominator = add(multiply(x, add(multiply(x, c), d)), e);
        vec3 toneMapped = clamp(divide(numerator, denominator), 0.0, 1.0);
        // Apply gamma correction
        vec3 gammaCorrected = pow(toneMapped, new vec3(gamma));
        return gammaCorrected;
    }
}
