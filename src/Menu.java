import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Menu  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        root.setPrefSize(800,600);
        InputStream is = Files.newInputStream(Paths.get("taust.png"));
        Image img = new Image(is);
        is.close();

        ImageView imgView = new ImageView();

        root.getChildren().addAll(imgView);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
