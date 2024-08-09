package Math_Util;

import static Math_Util.vec3.dot;
import static Math_Util.vec3.subtract;

public class Sphere {
    public double radius;
    public vec3 center;
    private color color;
    public boolean fullyDiffuse;
    public Sphere(double radius, vec3 center, color color, boolean fullyDiffuse) {
        this.radius = radius;
        this.center = center;
        this.color = color;
        this.fullyDiffuse = fullyDiffuse;
    }
    public static double hit_sphere(Sphere sphere, Ray ray) {
        vec3 oc = subtract(sphere.center, ray.getOrigin());
        double a = dot(ray.getDirection(), ray.getDirection());
        double h = dot(ray.getDirection(), oc);
        double c = oc.length() * oc.length() - sphere.radius * sphere.radius;
        double disc = h*h - a*c;

        if (disc < 0.0){
            return -1.0;
        }
        else{
            return (h- Math.sqrt(disc)) / a;
        }
    }
    public double getRadius() {
        return this.radius;
    }
    public vec3 getCenter() {
        return this.center;
    }
    public color getColor() {
        return this.color;
    }

}
