
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Traps extends Application {
    private char praeguneMängija = 'X'; // Kujund, kes alustab
    private Laud[][] ruut = new Laud[3][3];
    private Label staatus = new Label("Käik on mängijal: " + esimene); // Silt selleks, et stseeni all näidata kelle kord on.
    private List<Character> käigud = new ArrayList(); // Järejend kuhu salvestatakse tehtud käigud.
    static String esimene = JOptionPane.showInputDialog("Esimese mängija nimi (X): "); //Laseb sisestada mängijate nimed,
    static String teine = JOptionPane.showInputDialog("Teise mängija nimi (O): ");//mis hiljem logisse kirja lähevad.

    @Override
    public void start(Stage pealava) {
        reegel();
        GridPane pane = new GridPane();  // Kasutan Gridpane, et väljastada ruutude asendid.

        for (int i = 0; i < 3; i++) {    // Fikseerin ruutude positsioonin. (i on rea indeks, j on veeru indeks)
            for (int j = 0; j < 3; j++) {
                ruut[i][j] = new Laud();
                pane.add(ruut[i][j], j, i);
            }
        }
        BorderPane border = new BorderPane(); // n-ö põhikonteiner.
        border.setCenter(pane);
        border.setBottom(staatus);
        Scene stseen = new Scene(border, 600, 600);
        pealava.setTitle("Trips-Traps-Trull");
        pealava.setScene(stseen);
        pealava.show();
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
        
        public Laud() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(600, 600);
            this.setOnMouseClicked(e -> NupuVajutus()); // Hiireklahvi vajutusel läheb Nupuvajutus meetodisse.

        }


        // Meetod, mis tegutseb siis kui toimub hiireklahvi vajutus.
        private void NupuVajutus() {
            if (käigud.contains(mängija)) { // Tingimuslause, mis kontrollib, kas antud ruudule on juba käik tehtud või ei ole.
                staatus.setText("See koht on võetud käi mujale.");
                return;
            }
            setMängija(praeguneMängija);
            if (kasVõitis(praeguneMängija)) { // Kontrollib, kas praegu mängija oma kõiguga võitis või ei
                if (Character.valueOf(praeguneMängija) == 'X') { //Kui võitja on esimene mängija, salvestab selle logisse.
                    staatus.setText(esimene + " võitis!");
                    Logitamine.logitamine(esimene, 1);
                    Lõpuekraan.lõpp(praeguneMängija); // Lõpuekraani kuvamisega on väikene bug, sest viimane käik ilmub alles peale lõpuekraani ilmumist.
                } else {//Logitab teise mängija võidu korral.
                    staatus.setText(teine + " võitis!");
                    Logitamine.logitamine(teine, 1);
                    Lõpuekraan.lõpp(praeguneMängija);
                }

            } else if (kasLaudOnTäis()) { // Kontrollib, kas mängulaud on täis ja kui on, siis muudetakse praegune mängja ära ning kuvatakse vastav lõpuekraan.
                staatus.setText("Viik!");
                praeguneMängija = ' ';
                Lõpuekraan.lõpp(praeguneMängija);
            } else { // Kui mängulaud pole täis ning kumbki pole võitnud, siis on järgmise inimese kord
                praeguneMängija = (praeguneMängija == 'X') ? 'O' : 'X'; // Kui mäng jätkub, siis vahetab praeguse mängija ära.
                if (praeguneMängija == 'X') staatus.setText(esimene + " peab käima");
                else staatus.setText(teine + " peab käima");
            }

        }

        public char getMängija() {
            return mängija;
        }

        public void setMängija(char x) { // Meetod, mis joonistab vastava mängija käigu mängulauale ning lisab tehtud käigu käikude järjendisse.
            mängija = x;
            if (mängija == 'X') {
                Line joon1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10); // X-i üks joon
                joon1.endXProperty().bind(this.widthProperty().subtract(10));
                joon1.endYProperty().bind(this.heightProperty().subtract(10));
                joon1.setStrokeWidth(10.3); // Määran esimese joone laiuse ja värvi
                joon1.setStroke(Color.HOTPINK);
                Line joon2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10); // X-i teine joon
                joon2.endXProperty().bind(this.widthProperty().subtract(10));
                joon2.startYProperty().bind(this.heightProperty().subtract(10));
                joon2.setStrokeWidth(10.3); // Määran teise joone laiuse ja värvi
                joon2.setStroke(Color.DODGERBLUE);
                Logitamine.logitamine(esimene, 0); // Lisan nende joonistamise logifaili.
                getChildren().addAll(joon1, joon2);
                käigud.add(mängija); // Lisan tehtud käigu käikude järjendisse.
            } else if (mängija == 'O') {
                Ellipse ring = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                ring.centerXProperty().bind(this.widthProperty().divide(2));
                ring.centerYProperty().bind(this.heightProperty().divide(2));
                ring.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ring.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ring.setStroke(Color.DODGERBLUE); // Ringi värv.
                ring.setStrokeWidth(5.0); // Ringi välisjoone laius.
                ring.setFill(Color.HOTPINK); // Seespoolsema ringi värv.
                Logitamine.logitamine(teine, 0);  // Ringi joonistamise lisamine logifaili.
                getChildren().add(ring);
                käigud.add(mängija); // Lisan tehtud käigu käikude järjendisse.
            }
        }

    }

    public static void reegel(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Reeglid.display("");
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}

