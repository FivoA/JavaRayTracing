package Math_Util;

public class RayQuery {
    public double t;
    public vec3 normal;
    public vec3 color;
    public boolean fullyDiffuse;
    public RayQuery(double t , vec3 normal, vec3 color, boolean fullyDiffuse) {
        this.t = t;
        this.normal = normal;
        this.color = color;
        this.fullyDiffuse = fullyDiffuse;
    }
}
