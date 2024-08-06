package CD_Util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class PNGImageViewer extends Application {

    private static final String IMAGE_PATH = "C:\\Users\\felly\\IdeaProjects\\RayTracing\\image.png";
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

        primaryStage.setTitle("PNG Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(REFRESH_INTERVAL_MS), event -> {
            try {
                Image image = new Image("file:" + IMAGE_PATH);
                imageView.setImage(image);
                primaryStage.setWidth(image.getWidth());
                primaryStage.setHeight(image.getHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
