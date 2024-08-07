package Math_Util;

public class Ray {
    private vec3 origin;
    private vec3 direction;
    public Ray(vec3 origin, vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }
    public Ray(){}
    public vec3 getOrigin() {
        return origin;
    }
    public vec3 getDirection() {
        return direction;
    }
    public void setOrigin(vec3 origin) {
        this.origin = origin;
    }
    public vec3 pointAt(double t) {
        return origin.plus(direction.times(t));
    }
    public String toString(){
        return "origin: " + origin.toString() + " direction: " + direction.toString();
    }
}
