import Math_Util.color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {


    public static void main(String[] args) throws IOException {
        System.out.printf("Starting render...");
        BufferedWriter writer = new BufferedWriter(new FileWriter("image.ppm"));
        int image_width = 40;
        int image_height = 40;
        writer.write("P3"+ "\n" + image_width + " " + image_height + "\n255\n");
        for (int j = 0; j < image_height; j++) {
            System.out.println("Scanlines remaining: " + (image_height-j));
            for (int i = 0; i < image_width; i++) {
                color col = new color((double) i / (image_width-1), (double) j / (image_height-1), 0.0);
                color.writeColor(writer,col);
            }
        }
        System.out.println("Render done.");
        writer.close();
    }
}