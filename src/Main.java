import Math_Util.*;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import static Math_Util.Sphere.intersectSphere;
import static Math_Util.color.*;
import static Math_Util.vec3.*;

public class Main {
    private static final int NUM_CORES = Runtime.getRuntime().availableProcessors();
    private static BufferedImage image;

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
    public static void renderImage(int imageWidth, int imageHeight, vec3 pixel0Location, vec3 deltaRight, vec3 deltaDown, point3 cameraCenter) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CORES);

        for (int j = 0; j < imageHeight; j++) {
            final int row = j;
            executor.submit(() -> {renderRow(row, imageWidth, pixel0Location, deltaRight, deltaDown, cameraCenter);});
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private static void renderRow(int row, int imageWidth, vec3 pixel0Location, vec3 deltaRight, vec3 deltaDown, point3 cameraCenter) {
        for (int i = 0; i < imageWidth; i++) {
            vec3 pixelCenter = add(pixel0Location, multiply(deltaRight, i));
            pixelCenter = add(pixelCenter, multiply(deltaDown, row));
            vec3 rayDir = subtract(pixelCenter, cameraCenter);

            Ray ray = new Ray(cameraCenter, rayDir);
            color col = rayColor(ray);
            int r = (int) (col.getX() * 255.999);
            int g = (int) (col.getY() * 255.999);
            int b = (int) (col.getZ() * 255.999);
            int rgb = (r << 16) | (g << 8) | b;
            image.setRGB(i, row, rgb);
        }
    }
}