package Math_Util;

import static Math_Util.vec3.dot;
import static Math_Util.vec3.subtract;

public class Sphere {
    public double radius;
    public vec3 center;

    public Sphere(double radius, vec3 center) {
        this.radius = radius;
        this.center = center;
    }
    public static double hit_sphere(Sphere sphere, Ray ray) {
        vec3 ro = subtract(ray.getOrigin(),sphere.getCenter());
        double b = dot(ro,ray.getDirection());
        double c = dot(ro,ro) - sphere.getRadius()*sphere.getRadius();
        double d = b*b - c;
        if(d < 0.0) return -1.; // No intersection
        d = Math.sqrt(d);
        double t1 = -b - d;
        double t2 = -b + d;
        if(t1 < 0.0) return t2; // Potentially inside
        return t1;
    }


    public double getRadius() {
        return this.radius;
    }

    public vec3 getCenter() {
        return this.center;
    }

}
