import Math_Util.*;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;

import static Math_Util.Sphere.hit_sphere;
import static Math_Util.color.*;
import static Math_Util.vec3.*;

public class Main {
    private static final int NUM_CORES = Runtime.getRuntime().availableProcessors();
    private static BufferedImage image;
    //these values are chosen by pure arbitrariness
    private static final vec3 sunDirection = new vec3(-1,0.5,0.2);
    private static final vec3 sunColor = new vec3(1.64, 1.27, 0.99);
    private static final vec3 skyColorHigh = new vec3(0.14, 0.21, 0.49);

    private static final List<Sphere> sphereList = new ArrayList<Sphere>();

    public static color rayColor(Ray r){
        RayQuery query = sceneIntersect(r.getOrigin(),r.getDirection());
        if (query.t >= 0.0) {
            vec3 hitPoint = add(r.getOrigin(),multiply(r.getDirection(),query.t));
            vec3 light = lightAtPoint(hitPoint,query.normal);

            vec3 col =  multiply( query.color , light);
            col = tonemapAndGammaCorrect(col);
            return new color(col.getX(),col.getY(),col.getZ());
        }


        //gradient function for sky, aka ray has not hit sphere or plane
        vec3 unitDir = unitVector(r.getDirection());
        double a = 0.5 * (unitDir.getY() + 1.0);
        color col = new color(1.0, 1.0, 1.0);
        vec3 v = add(multiply(col,1.0-a),multiply(new color(0.5,0.7,1.0),a));
        v = tonemapAndGammaCorrect(v);
        col = new color(v.getX(), v.getY(), v.getZ());

        return col;
    }


    public static void main(String[] args) throws IOException {
        sphereList.add(new Sphere(0.25, new vec3(0, -0.25, -2),new color(0.3,0.8,0.3)));
        sphereList.add(new Sphere(0.5, new vec3(1, 0, -2.2), new color(0.8,0.8,0.3)));
        sphereList.add(new Sphere(0.5, new vec3(-2, 0, -7), new color(0.8,0.3,0.3)));

        double aspect_ratio = 16.0 / 9.0;
        int image_width = 400;
        int image_height = (int) (image_width / aspect_ratio);

        // FOV in degrees
        double vertical_fov_degrees = 45.0;
        double vertical_fov_radians = Math.toRadians(vertical_fov_degrees);

        //camera code
        double viewport_height = 2.0 * Math.tan(vertical_fov_radians / 2.0);
        double viewport_width = viewport_height * aspect_ratio;


        vec3 camera_center = new point3(0,0,0);
        vec3 viewRightVec = new vec3(viewport_width,0,0);
        vec3 viewDownVec = new vec3(0,-viewport_height,0);

        vec3 deltaRight = divide(viewRightVec,image_width-1);
        vec3 deltaDown = divide(viewDownVec,image_height-1);

        vec3 viewport_upper_left_point = subtract(camera_center,new vec3(0,0,1));
        viewport_upper_left_point = subtract(viewport_upper_left_point,divide(viewRightVec,2.0) );
        viewport_upper_left_point = subtract(viewport_upper_left_point, divide(viewDownVec,2.0));

        vec3 pixel0_location =  add(viewport_upper_left_point, multiply(add(deltaRight,deltaDown),0.5));

        image = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);

        //long startTime = System.nanoTime(); // Start time

        try {
            renderImage(image_width, image_height, pixel0_location, deltaRight, deltaDown, camera_center);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //long endTime = System.nanoTime(); // End time

        File outputfile = new File("image.png");
        ImageIO.write(image, "png", outputfile);

        //long duration = (endTime - startTime) / 1_000_000; // MS conversion
        //System.out.println("Rendering took: " + duration + " ms");

    }
    public static void renderImage(int imageWidth, int imageHeight, vec3 pixel0Location, vec3 deltaRight, vec3 deltaDown, vec3 cameraCenter) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CORES);

        for (int j = 0; j < imageHeight; j++) {
            final int row = j;
            executor.submit(() -> {renderRow(row, imageWidth, pixel0Location, deltaRight, deltaDown, cameraCenter);});
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private static void renderRow(int row, int imageWidth, vec3 pixel0Location, vec3 deltaRight, vec3 deltaDown, vec3 cameraCenter) {
        for (int i = 0; i < imageWidth; i++) {
            vec3 pixelCenter = add(pixel0Location, multiply(deltaRight, i));
            pixelCenter = add(pixelCenter, multiply(deltaDown, row));
            vec3 rayDir = subtract(pixelCenter, cameraCenter);
            rayDir = unitVector(rayDir);
            Ray ray = new Ray(cameraCenter, rayDir);
            color col = rayColor(ray);
            int r = (int) (col.getX() * 255.999);
            int g = (int) (col.getY() * 255.999);
            int b = (int) (col.getZ() * 255.999);
            int rgb = (r << 16) | (g << 8) | b;
            image.setRGB(i, row, rgb);
        }
    }
    public static vec3 lightAtPoint(vec3 point, vec3 N){
        vec3 L = unitVector(sunDirection);
        double NdL = Math.max(dot(N,L),0.0);
        double NdSky = Math.clamp(0.5*N.getY()+0.5, 0.0, 1.0 );
        double shadow = shadowRay(add(point,multiply(N,0.001)),L); // offset N to avoid self shadowing
        return add(multiply(multiply(sunColor,NdL),shadow), multiply(skyColorHigh , NdSky));
    }
    public static double shadowRay(vec3 ro, vec3 rd){
        double t = sceneIntersect(ro, rd).t;
        if (t < 0.0) {
            return 1.0;
        }
        return 0.0;
    }

    public static RayQuery sceneIntersect(vec3 ro, vec3 rd){
        double closestHit = Double.MAX_VALUE;
        // Query with t < 0 has not hit anything
        RayQuery query = new RayQuery(-1.0,new vec3(0),new vec3(0));
        //Spheres
        for (Sphere sphere : sphereList) {
            double t = hit_sphere(sphere, new Ray(ro, rd));
            if (t < 0.0 || t > closestHit) continue;
            closestHit = query.t = t;
            query.color = sphere.getColor();
            query.normal = unitVector(subtract(add(ro, multiply(rd, t)), sphere.getCenter()));
        }
        //Plane
        vec3 planeNormal = new vec3(0,1,0);
        vec3 planePoint = new vec3(0,-0.5,0);
        for(int i = 0; i < 1;i++){
            float t = planeIntersect(ro,rd,planeNormal,planePoint);
            if(t < 0.0 || t > closestHit) continue;
            closestHit = query.t = t;
            query.color = checkerboard(add(ro,multiply(rd,t)),3.0);
            query.normal = planeNormal;
        }
        return query;
    }


    public static float planeIntersect(vec3 ro, vec3 rd, vec3 n, vec3 p){
        return ((float) dot(subtract(p,ro),n)) / ((float) dot(rd,n));
    }
    // checkerboard pattern based on position of point with a center line
    public static vec3 checkerboard(vec3 point, double tileSize){
        vec2 p = vec2.divide(point.getXY() , tileSize);
        vec2 t = vec2.floor(vec2.add(p,0.5));
        if(t.getX() == 0.0 || t.getY() == 0.0) // middle line for better orientation
            return new vec3(1.0,0.8,0.4);
        return mix(new vec3(1.0),new vec3(0.1),(t.getX()+t.getY())%2.0 );
    }

}