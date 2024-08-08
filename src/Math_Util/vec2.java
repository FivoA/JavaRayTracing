package Math_Util;

public class vec2 {
    private double x;
    private double y;
    public vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getY() {
        return y;
    }
    public double getX() {
        return x;
    }

    public static vec2 divide(vec2 a, double d) {
        return new vec2(a.getX() / d, a.getY() / d);
    }
    public static vec2 add(vec2 a, double d){
        return new vec2(a.getX() + d, a.getY());
    }
    public static vec2 floor(vec2 a){
        return new vec2(Math.floor(a.getX()) , Math.floor(a.getY()));
    }
}
