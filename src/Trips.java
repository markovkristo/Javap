import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


    public class Trips extends Application {

        private boolean Xkord = true; // lipp
        private boolean mängitav = true;
        private Ruut[][] laud = new Ruut[3][3];
        private List<Võiduvõimalused> võiduVõimalused = new ArrayList<>();
        private Pane juur = new Pane();

        public void logitamine(String mängija) {
            File logi = new File("logi.txt");
            try {
                if (!logi.exists()) {
                    logi.createNewFile();
                }
                PrintWriter kirjuta = new PrintWriter(new FileWriter(logi, true));
                kirjuta.append("Mängija " + mängija + " sisestas käigu ruudule " + "||" + new java.sql.Timestamp(System.currentTimeMillis()) + "\n");
                kirjuta.close();
            } catch (IOException e) {
                System.out.println("Logi error");
            }
        }


        public Trips() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        }


        private Parent joonista(){
            juur.setPrefSize(600,600);
            for (int i = 0; i < 3 ; i++) {
                for (int j = 0; j < 3; j++) {
                    Ruut ruut = new Ruut();
                    ruut.setTranslateX(j * 200);
                    ruut.setTranslateY(i * 200);
                    juur.getChildren().add(ruut);

                    laud[j][i] = ruut;
                }
            }
            // Käin read for-tsükliga läbi ja lisan need võiduvõimalustesse.
            for (int m = 0; m < 3 ; m++) {
                võiduVõimalused.add(new Võiduvõimalused(laud[0][m], laud[1][m], laud[2][m]));
            }


            // Käin read for-tsükliga läbi ja lisan need võiduvõimalustesse.
            for (int i = 0; i < 3 ; i++) {
                võiduVõimalused.add(new Võiduvõimalused(laud[i][0],laud[i][1],laud[i][2]));
            }

            // Lisan diagonaalid võiduvõimalustesse.
            võiduVõimalused.add(new Võiduvõimalused(laud[0][0],laud[1][1],laud[2][2]));
            võiduVõimalused.add(new Võiduvõimalused(laud[2][0],laud[1][1],laud[0][2]));
            return juur;
        }
        @Override
        public void start(Stage pealava) throws Exception {
            pealava.setScene(new Scene(joonista()));
            pealava.show();
        }


        private void mänguSeis(){
            for (Võiduvõimalused  võimalus: võiduVõimalused) {
                if(võimalus.võitis()){
                    mängitav = false;
                    võiduAnimatsioon(võimalus);
                    break;
                }
            }
        }
        private void võiduAnimatsioon(Võiduvõimalused võimalus){
            Line joon = new Line();
            joon.setStartX(võimalus.ruudud[0].xKeskkoht()); // Joone x kordinaadi alguspunkt.
            joon.setStartY(võimalus.ruudud[0].yKeskkoht()); // Joone y kordinaadi alguspunkt.
            joon.setEndX(võimalus.ruudud[0].xKeskkoht());   // Joone x kordinaadi lõpppunkt.
            joon.setEndY(võimalus.ruudud[0].yKeskkoht());   // Joone y kordinaadi lõpppunkt.

            joon.setStroke(Color.RED);
            joon.setStrokeWidth(12.5);
            juur.getChildren().add(joon);

            Timeline animatsioon = new Timeline();
            animatsioon.getKeyFrames().add(new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(joon.endXProperty(), võimalus.ruudud[2].xKeskkoht()),
                    new KeyValue(joon.endYProperty(), võimalus.ruudud[2].yKeskkoht())));
            animatsioon.play();
        }

        private class Võiduvõimalused{
            private Ruut[] ruudud;

            public Võiduvõimalused(Ruut... ruudud) {  // Konstruktor, mis lubab võtta täpselt kolm elementi massiivi.
                this.ruudud = ruudud;
            }
            public boolean võitis(){ // Meetod otsustab kas read või veerus on tekkinud võiduolukord
                if(ruudud[0].getVäärtus().isEmpty()) // Alustan esimese väärtuse vaatamisega ning kui see tühi on pole vaja teisi edasi vaadata
                    return false;
                return ruudud[0].getVäärtus().equals(ruudud[1].getVäärtus()) && ruudud[0].getVäärtus().equals(ruudud[2].getVäärtus());// Kui eelmine tingimuslause ei tagastanud false, tagastan kontrolli, kus vaatab kas esimene on võrdne teise ja kolmandaga väärtusega.

            }

        }

        private class Ruut extends StackPane {
            private Text kujund = new Text();
            public Ruut(){
                Rectangle piirjoon = new Rectangle(200,200);
                piirjoon.setFill(null);
                piirjoon.setStroke(Color.BLACK);

                kujund.setFont(Font.font(75));
                setAlignment(Pos.CENTER);
                getChildren().addAll(piirjoon, kujund);

                setOnMouseClicked(event ->{
                    if(!mängitav)
                        return;
                    if (event.getButton() == MouseButton.PRIMARY){// Nata kahtlane koht, et kui tahad X joonistada on vasak hiireklahv ja kui O-d ss parem.
                        if(!Xkord)
                            return;
                        logitamine("X");
                        joonistaX();
                        Xkord = false;
                        mänguSeis();
                    }
                    else if(event.getButton() == MouseButton.SECONDARY){
                        if(Xkord)
                            return;
                        logitamine("O");
                        joonistaO();
                        Xkord = true;
                        mänguSeis();
                    }
                } );
            }

            public double xKeskkoht(){
                return getTranslateX() + 100;
            }
            public double yKeskkoht(){
                return getTranslateY() + 100;
            }

            public String getVäärtus() {
                return kujund.getText();
            }

            private void joonistaX(){
                kujund.setText("X");
            }
            private void joonistaO(){
                kujund.setText("O");
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }


