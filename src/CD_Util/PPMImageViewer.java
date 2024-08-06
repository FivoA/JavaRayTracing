package CD_Util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class PPMImageViewer extends Application {

    private static final String IMAGE_PATH = "C:\\Users\\felly\\IdeaProjects\\RayTracing\\image.ppm";
    private static final int REFRESH_INTERVAL_MS = 100; // Refresh every 100 milliseconds

    private ImageView imageView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageView();
        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 800, 600); // Set initial size, will be adjusted later

        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(REFRESH_INTERVAL_MS), event -> {
            try {
                WritableImage image = readPPM(new File(IMAGE_PATH));
                imageView.setImage(image);
                primaryStage.setWidth(image.getWidth());
                primaryStage.setHeight(image.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private WritableImage readPPM(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             Scanner scanner = new Scanner(fis)) {

            String format = scanner.next();
            if (!format.equals("P3")) {
                throw new IOException("Unsupported PPM format: " + format);
            }

            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int maxColorValue = scanner.nextInt();

            WritableImage image = new WritableImage(width, height);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int red = scanner.nextInt();
                    int green = scanner.nextInt();
                    int blue = scanner.nextInt();
                    int argb = (0xFF << 24) | (red << 16) | (green << 8) | blue;
                    image.getPixelWriter().setArgb(x, y, argb);
                }
            }

            return image;
        }
    }
}
