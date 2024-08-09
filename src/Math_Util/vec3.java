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
    public vec3(double x){
        this.x = x;
        this.y = x;
        this.z = x;
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
    public vec2 getXY(){
        return new vec2(x,y);
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
    public static vec3 divide(vec3 v1, vec3 v2){
        return new vec3(v1.x/v2.x, v1.y/v2.y, v1.z/v2.z);
    }
    public static double dot(vec3 v1, vec3 v2){
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    public static vec3 unitVector(vec3 v1){
        return divide(v1,v1.length());
    }
    public static vec3 mix(vec3 one, vec3 other, double factor) {
        return new vec3(
                one.getX() * (1.0 - factor) + other.getX() * factor,
                one.getY() * (1.0 - factor) + other.getY() * factor,
                one.getZ() * (1.0 - factor) + other.getZ() * factor
        );
    }
    public static vec3 add(vec3 v, double d){
        return new vec3(v.getX()+d, v.getY()+d, v.getZ()+d);
    }
    public static vec3 pow(vec3 v1, vec3 v2){
        return new vec3(Math.pow(v1.getX(),v2.getX()), Math.pow(v1.getY(),v2.getY()), Math.pow(v1.getZ(),v2.getZ()));
    }
    public static vec3 clamp(vec3 v1, double low, double high){
        return new vec3(Math.clamp(v1.getX(),low,high), Math.clamp(v1.getY(),low,high), Math.clamp(v1.getZ(),low,high));
    }
    public static vec3 reflect(vec3 v, vec3 normal){
        return subtract(v, multiply(normal, 2.0 * dot(normal, v)));
    }
    @Override
    public String toString(){
        return this.x + ", " + this.y + ", " + this.z;
    }

}
