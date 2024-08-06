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
    public vec3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
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

    public vec3 operatorMinus(){
        return new vec3(-x,-y,-z);
    }
    public vec3 plus(vec3 other){
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }
    public vec3 minus(vec3 other){
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }
    public vec3 times(double scalar){
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }
    public vec3 divide(double scalar){
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
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
    public static vec3 multiply(vec3 v1, double scalar){
        return new vec3(v1.x*scalar, v1.y*scalar, v1.z*scalar);
    }
    public static vec3 divide(vec3 v1, double scalar){
        return new vec3(v1.x/scalar, v1.y/scalar, v1.z/scalar);
    }
    public static double dot(vec3 v1, vec3 v2){
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    public static vec3 cross(vec3 v1, vec3 v2){
        return new vec3(v1.y * v2.z- v1.z * v2.y, v1.z * v2.x - v1.x -v2.z, v1.x * v2.y - v1.y * v2.x );
    }
    public static vec3 unitVector(vec3 v1){
        return divide(v1,v1.length());
    }

    public double length_squared() {
        return this.length() * this.length();
    }
    public String toString(){
        return this.x + ", " + this.y + ", " + this.z;
    }

}
