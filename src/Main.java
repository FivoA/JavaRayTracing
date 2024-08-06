import Math_Util.Ray;
import Math_Util.color;
import Math_Util.point3;
import Math_Util.vec3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static Math_Util.vec3.*;

public class Main {
    public static color rayColor(Ray r){
        //gradient function for sky
        vec3 unitDir = unitVector(r.getDirection());
        double a = 0.5 * (unitDir.getY() + 1.0);
        color col = new color(1.0, 1.0, 1.0);
        vec3 v = addVectors(multiplyScalar(col,1.0-a),multiplyScalar(new color(0.5,0.7,1.0),a));
        col = new color(v.getX(), v.getY(), v.getZ());
        return col;
    }


    public static void main(String[] args) throws IOException {
        double aspect_ratio = 16.0 / 9.0;
        int image_width = 400;
        int image_height = (int) (image_width / aspect_ratio);
        image_height = (image_height) < 1 ? 1 : image_height;

        //camera code
        double distanceToViewport = 1.0;
        double viewport_height = 2.0;
        double viewport_width = viewport_height * ((double) image_width / image_height);
        point3 camera_center = new point3(0,0,0);
        // viewport vectors
        vec3 viewRightVec = new vec3(viewport_width,0,0);
        vec3 viewDownVec = new vec3(0,-viewport_height,0);

        vec3 deltaRight = divideScalar(viewRightVec,image_width);
        vec3 deltaDown = divideScalar(viewDownVec,image_width);

        vec3 viewport_upper_left_point = subtractVectors(camera_center,new vec3(0,0,distanceToViewport));
        viewport_upper_left_point = subtractVectors(viewport_upper_left_point,divideScalar(viewRightVec,2.0) );
        viewport_upper_left_point = subtractVectors(viewport_upper_left_point, divideScalar(viewDownVec,2.0));

        vec3 pixel0_location =  addVectors(viewport_upper_left_point, multiplyScalar(addVectors(deltaRight,deltaDown),0.5));

        System.out.printf("Starting render...");
        BufferedWriter writer = new BufferedWriter(new FileWriter("image.ppm"));

        writer.write("P3"+ "\n" + image_width + " " + image_height + "\n255\n");
        for (int j = 0; j < image_height; j++) {
            System.out.println("Scanlines remaining: " + (image_height-j));
            for (int i = 0; i < image_width; i++) {
                vec3 pixelCenter = addVectors(pixel0_location,multiplyScalar(deltaRight,i));
                pixelCenter =  addVectors(pixelCenter, multiplyScalar(deltaDown,j));
                vec3 rayDir = subtractVectors(pixelCenter,camera_center);
                Ray ray = new Ray(camera_center,rayDir);
                color col = rayColor(ray);
                color.writeColor(writer,col);
            }
        }
        System.out.println("Render done.");
        writer.close();
    }
}