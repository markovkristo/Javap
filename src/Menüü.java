import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Menüü  extends Application {
    private MänguMenüü mänguMenüü;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        root.setPrefSize(800,600);
        InputStream is = Files.newInputStream(Paths.get("C:\\Users\\Erik\\Desktop\\OOP_projekt_2\\src\\taust.png"));
        Image img = new Image(is);
        is.close();

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(800);
        imgView.setFitHeight(400);

        mänguMenüü = new MänguMenüü();
        root.getChildren().addAll(imgView, mänguMenüü);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private class MänguMenüü extends Parent {
        public MänguMenüü(){
            VBox menüü0 = new VBox(10);
            VBox menüü1 = new VBox(10);
            menüü0.setTranslateX(100);
            menüü0.setTranslateY(200);
            menüü1.setTranslateX(100);
            menüü1.setTranslateY(200);
            final int offset = 400;


            MenüüNupp mängi = new MenüüNupp("Mängi");
            mängi.setOnMouseClicked(event ->{
                //FadeTransition ft = new FadeTransition(Duration.seconds(0.5),this);
                //ft.setFromValue(1);
                //ft.setToValue(0);
                //ft.setOnFinished(evt -> this.setVisible(false));
                //ft.play();
            });
            MenüüNupp välju = new MenüüNupp("Välju");
            välju.setOnMouseClicked(event ->{
                System.exit(0);
            });
            menüü0.getChildren().addAll(mängi,välju);
            Rectangle taust = new Rectangle(800,400);
            taust.setFill(Color.GREY);
            taust.setOpacity(0.4);
            getChildren().addAll(taust, menüü0);

        }
    }
    public static class MenüüNupp extends StackPane{
        private Text tekst;

        public MenüüNupp(String nimi){
            tekst = new Text(nimi);
            tekst.setFont(tekst.getFont().font(20));

            Rectangle taust = new Rectangle(250, 30);
            taust.setOpacity(0.6);
            taust.setFill(Color.BLACK);
            taust.setEffect(new GaussianBlur(3.5));

            setAlignment(Pos.CENTER_LEFT);
            setRotate(-0.5);
            getChildren().addAll(taust,tekst);

            setOnMouseEntered(event ->{
                taust.setTranslateX(10);
                tekst.setTranslateX(10);
                taust.setFill(Color.WHITE);
                tekst.setFill(Color.BLACK);
            });
            setOnMouseExited(event ->{
                taust.setTranslateX(0);
                tekst.setTranslateX(0);
                taust.setFill(Color.BLACK);
                tekst.setFill(Color.WHITE);
            });

            DropShadow ds = new DropShadow(50, Color.WHITE);
            ds.setInput(new Glow());

            setOnMousePressed(event -> setEffect(ds));
            setOnMouseReleased(event -> setEffect(null));


        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
