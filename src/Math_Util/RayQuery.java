package Math_Util;

public class RayQuery {
    public double t;
    public vec3 normal;
    public vec3 color;
    public RayQuery(double t , vec3 normal, vec3 color) {
        this.t = t;
        this.normal = normal;
        this.color = color;
    }
}
