package Math_Util;

import static Math_Util.vec3.dot;
import static Math_Util.vec3.subtract;

public class Sphere {
    public double radius;
    public vec3 center;
    private color color;
    public Sphere(double radius, vec3 center, color color) {
        this.radius = radius;
        this.center = center;
        this.color = color;
    }
    public static double hit_sphere(Sphere sphere, Ray ray) {
        ray.setOrigin(subtract(ray.getOrigin(), sphere.center));
        double b = dot(ray.getOrigin(), ray.getDirection());
        double c = dot(ray.getOrigin(), ray.getOrigin()) - sphere.radius*sphere.radius;
        double d = b*b - c;
        if (d < 0.0) {
            return -1.0;
        }
        d = Math.sqrt(d);
        double t1 = -b-d;
        double t2 = -b+d;
        if (t1 < 0.0) {
            return t2;
        }
        return t1;
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
