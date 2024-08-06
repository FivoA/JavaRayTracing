import Math_Util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static Math_Util.Sphere.intersectSphere;
import static Math_Util.color.*;
import static Math_Util.vec3.*;

public class Main {
    public static color rayColor(Ray r){
        //gradient function for sky
        vec3 unitDir = unitVector(r.getDirection());
        double a = 0.5 * (unitDir.getY() + 1.0);
        color col = new color(1.0, 1.0, 1.0);
        vec3 v = add(multiply(col,1.0-a),multiply(new color(0.5,0.7,1.0),a));
        col = new color(v.getX(), v.getY(), v.getZ());
        return col;
    }


    public static void main(String[] args) throws IOException {
        double aspect_ratio = 16.0 / 9.0;
        int image_width = 400;
        int image_height = (int) (image_width / aspect_ratio);

        //camera code
        double distanceToViewport = 1.0;
        double viewport_height = 2.0;
        double viewport_width = viewport_height * ((double) image_width / image_height);
        point3 camera_center = new point3(0,0,0);
        // viewport vectors
        vec3 viewRightVec = new vec3(viewport_width,0,0);
        vec3 viewDownVec = new vec3(0,-viewport_height,0);

        vec3 deltaRight = divide(viewRightVec,image_width);
        vec3 deltaDown = divide(viewDownVec,image_width);

        vec3 viewport_upper_left_point = subtract(camera_center,new vec3(0,0,1));
        viewport_upper_left_point = subtract(viewport_upper_left_point,divide(viewRightVec,2.0) );
        viewport_upper_left_point = subtract(viewport_upper_left_point, divide(viewDownVec,2.0));

        vec3 pixel0_location =  add(viewport_upper_left_point, multiply(add(deltaRight,deltaDown),0.5));
        BufferedWriter writer = new BufferedWriter(new FileWriter("image.ppm"));

        writer.write("P3"+ "\n" + image_width + " " + image_height + "\n255\n");

        //long startTime = System.nanoTime(); // Start time
        renderImage(image_width,image_height,pixel0_location,deltaRight,deltaDown,camera_center,writer);
        //long endTime = System.nanoTime(); // End time

        writer.close();
        //long duration = (endTime - startTime) / 1_000_000; // MS conversion
        //System.out.println("Rendering took: " + duration + " ms");

    }

    private static final int NUM_CORES = Runtime.getRuntime().availableProcessors();
    public static void renderImage(int imageWidth, int imageHeight, vec3 pixel0Location, vec3 deltaRight, vec3 deltaDown, point3 cameraCenter, BufferedWriter writer) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CORES);
        for (int j = 0; j < imageHeight; j++) {
            final int row = j;
            executor.submit(() -> {
                try {
                    renderRow(row, imageWidth, pixel0Location, deltaRight, deltaDown, cameraCenter, writer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
    }
    private static void renderRow(int row, int imageWidth, vec3 pixel0Location, vec3 deltaRight, vec3 deltaDown, point3 cameraCenter, BufferedWriter writer) throws IOException {
        for (int i = 0; i < imageWidth; i++) {
            vec3 pixelCenter = add(pixel0Location, multiply(deltaRight, i));
            pixelCenter = add(pixelCenter, multiply(deltaDown, row));
            vec3 rayDir = subtract(pixelCenter, cameraCenter);

            Ray ray = new Ray(cameraCenter, rayDir);
            color col = rayColor(ray);
            writeColor(writer, col);
        }
    }
}