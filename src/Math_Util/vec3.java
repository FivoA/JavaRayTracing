package Math_Util;

public class vec3 {

    private double x;
    private double y;
    private double z;

    public vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double getX() {
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public double length(){
        return Math.sqrt(x*x + y*y + z*z);
    }


    public static vec3 add(vec3 v1, vec3 v2){
        return new vec3(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);
    }
    public static vec3 subtract(vec3 v1, vec3 v2){
        return new vec3(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);
    }
    public static vec3 multiply(vec3 v1, vec3 v2){
        return new vec3(v1.x*v2.x, v1.y*v2.y, v1.z*v2.z);
    }
    public static color multiply(color col, vec3 v2){
        return new color(col.getX()*v2.x, col.getY()*v2.y, col.getZ()*v2.z);
    }
    public static vec3 multiply(vec3 v1, double scalar){
        return new vec3(v1.x*scalar, v1.y*scalar, v1.z*scalar);
    }
    public static vec3 divide(vec3 v1, double scalar){
        return new vec3(v1.x/scalar, v1.y/scalar, v1.z/scalar);
    }
    public static double dot(vec3 v1, vec3 v2){
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    public static vec3 unitVector(vec3 v1){
        return divide(v1,v1.length());
    }
    @Override
    public String toString(){
        return this.x + ", " + this.y + ", " + this.z;
    }

}
