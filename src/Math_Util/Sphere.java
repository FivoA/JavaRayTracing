package Math_Util;

import static Math_Util.vec3.dot;

public class Sphere {
    private double radius;
    private vec3 center;
    public Sphere(double radius, vec3 center) {
        this.radius = radius;
        this.center = center;
    }
    public static double intersectSphere(Sphere sphere1, Ray ray) {
        ray.getOrigin().minus(sphere1.center);
        double b = dot(ray.getOrigin(),ray.getDirection());
        double c = dot(ray.getOrigin(),ray.getOrigin()) - sphere1.radius*sphere1.radius;
        double d = b*b - c;
        if(d < 0.0) return -1.; // No intersection
        d = Math.sqrt(d);
        double t1 = -b - d;
        double t2 = -b + d;
        if(t1 < 0.0) return t2; // Potentially inside
        return t1;
    }
}
