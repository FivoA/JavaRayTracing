package Math_Util;

public class Ray {
    private point3 origin;
    private vec3 direction;
    public Ray(point3 origin, vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }
    public Ray(){}
    public point3 getOrigin() {
        return origin;
    }
    public vec3 getDirection() {
        return direction;
    }
    public point3 pointAt(double t) {
        return (point3) origin.plus(direction.times(t));
    }

}
