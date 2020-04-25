
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Traps extends Application {
    private char praeguneMängija = 'X';
    private Laud[][] ruut = new Laud[3][3];
    private Label staatus = new Label( "Käik on mängijal: " + esimene);
    private Label võitja = new Label();
    static String esimene = JOptionPane.showInputDialog("Esimese mängija nimi (X): "); //Laseb sisestada mängijate nimed,
    static String teine = JOptionPane.showInputDialog("Teise mängija nimi (O): ");//mis hiljem logisse kirja lähevad.

    @Override
    public void start(Stage pealava) throws Exception {
        GridPane pane = new GridPane();  // Kasutan Gridpane, et väljastada ruudud.

        for (int i = 0; i < 3; i++) {    // Fikseerin ruutude positsioonin. (i on rea indeks, j on veeru indeks)
            for (int j = 0; j < 3; j++) {
                ruut[i][j] = new Laud();
                pane.add(ruut[i][j], j, i);
            }
        }

        BorderPane border = new BorderPane(); // n-ö Main konteiner.
        border.setCenter(pane);
        border.setBottom(staatus);
        Scene stseen = new Scene(border, 600, 600);
        pealava.setTitle("Trips-Traps-Trull");
        pealava.setScene(stseen);
        pealava.show();
    }
    public void logitamine(String mängija, int staatus) {
        File logi = new File("logi.txt");
        try {
            if(staatus == 0){
            if (!logi.exists()) {
                logi.createNewFile();
            }
            PrintWriter kirjuta = new PrintWriter(new FileWriter(logi, true));
            kirjuta.append("Mängija " + mängija + " sisestas käigu ruudule " + "||" + new java.sql.Timestamp(System.currentTimeMillis()) + "\n");
            kirjuta.close();}
            else{
                PrintWriter kirjuta = new PrintWriter(new FileWriter(logi, true));
                kirjuta.append("Mängija " + mängija + " võitis." + "||" + new java.sql.Timestamp(System.currentTimeMillis()) + "\n");
                kirjuta.close();
            }

        } catch (IOException e) {
            System.out.println("Logi error");
        }
    }

    // Meetod, mis käib kõik ruudud läbi ning vaatab, kas mingi koht on veel tühi ja kui ei ole, siis tagastab true.
    public boolean kasLaudOnTäis() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ruut[i][j].getMängija() == ' ')
                    return false;
            }
        }
        return true;
    }

    public boolean kasVõitis(char mängija) {
        // Käib läbi kõik vastava rea elemendid ning kontrollib, kas need on samasugused, kui on, siis tagastab true ja teine meetod väljastab vastava teksti ekraanile.
        for (int i = 0; i < 3; i++) {
            if (ruut[i][0].getMängija() == mängija && ruut[i][1].getMängija() == mängija && ruut[i][2].getMängija() == mängija)
                return true;
        }
        // Käib läbi kõik vastava veeru elemendid ning kontrollib, kas need on samasugused, kui on, siis tagastab true ja teine meetod väljastab vastava teksti ekraanile.
        for (int i = 0; i < 3; i++) {
            if (ruut[0][i].getMängija() == mängija && ruut[1][i].getMängija() == mängija && ruut[2][i].getMängija() == mängija)
                return true;
        }
        // Käib läbi kõik diagonaalide elemendid ning kontrollib, kas need on samasugused, kui on, siis tagastab true ja teine meetod väljastab vastava teksti ekraanile.
        if (ruut[0][0].getMängija() == mängija && ruut[1][1].getMängija() == mängija && ruut[2][2].getMängija() == mängija)
            return true;
        if (ruut[0][2].getMängija() == mängija && ruut[1][1].getMängija() == mängija && ruut[2][0].getMängija() == mängija)
            return true;
        return false;
    }

    public class Laud extends Pane {
        private char mängija = ' ';

        // Konstruktor
        public Laud() {
            setStyle("-fx-border-color: black");
            DropShadow vari = new DropShadow(20, Color.DARKRED);
            this.setPrefSize(600,600);
            this.setOnMouseClicked(e -> NupuVajutus());
            this.setOnKeyPressed(e -> {
                if(e.getCode() == KeyCode.DOWN)
                    NupuVajutus();
                else if(e.getCode() == KeyCode.ENTER)
                    this.setEffect(vari);

            });
        }

        // Meetod, mis tegutseb siis kui toimub nupuvajutus.
        private void NupuVajutus() {
            if(mängija == ' ' && praeguneMängija != ' '){ // Kui mängija on tühi, aga praegune mängija ei ole tühi, siis ütleb, et on praeguse mängija kord.
                setMängija(praeguneMängija);
                if(kasVõitis(praeguneMängija)){ // Kontrollib, kas praegu mängija oma kõiguga võitis või ei
                    if(Character.valueOf(praeguneMängija)=='X') {//Kui võitja on esimene mängija, salvestab selle logisse.
                        staatus.setText(esimene + " võitis!");
                        logitamine(esimene, 1);
                    }
                    else{//Logitab teise mängija võidu korral.
                        staatus.setText(teine + " võitis!");
                        logitamine(teine, 1);
                    }

                }
                else if(kasLaudOnTäis()){ // Kontrollib, kas mängulaud on täis.
                    staatus.setText("Viik!");
                    praeguneMängija = ' ';
                }
                else { // Kui mängulaud pole täis ning kumbki pole võitnud, siis on järgmise inimese kord
                    praeguneMängija = (praeguneMängija == 'X') ? 'O': 'X'; // Kui mäng peaks jätkama, siis vaatab kelle kord on ning kuvab selle ekraanile.
                    if(praeguneMängija=='X') staatus.setText(esimene + " peab käima");
                    else staatus.setText(teine + " peab käima");
                }
            }
        }

        public char getMängija() {
            return mängija;
        }

        public void  setMängija(char x) { // Meetod, mis joonistab vastava mängija käigu mängulauale.
            mängija = x;
            if (mängija == 'X') {
                Line joon1 = new Line(10,10,this.getWidth() - 10, this.getHeight() - 10);
                joon1.endXProperty().bind(this.widthProperty().subtract(10));
                joon1.endYProperty().bind(this.heightProperty().subtract(10));
                joon1.setStrokeWidth(10.3);
                joon1.setStroke(Color.HOTPINK);
                Line joon2 = new Line(10, this.getHeight()-10,this.getWidth()-10,10);
                joon2.endXProperty().bind(this.widthProperty().subtract(10));
                joon2.startYProperty().bind(this.heightProperty().subtract(10));
                joon2.setStrokeWidth(10.3);
                joon2.setStroke(Color.DODGERBLUE);
                logitamine(esimene, 0);
                getChildren().addAll(joon1,joon2);
            } else if (mängija == 'O') {
                Ellipse ring = new Ellipse(this. getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2-10, this.getHeight() / 2 - 10);
                ring.centerXProperty().bind(this.widthProperty().divide(2));
                ring.centerYProperty().bind(this.heightProperty().divide(2));
                ring.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ring.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ring.setStroke(Color.DODGERBLUE);
                ring.setStrokeWidth(5.0);
                ring.setFill(Color.HOTPINK);
                logitamine(teine, 0);
                getChildren().add(ring);
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}