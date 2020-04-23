
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class Traps extends Application {
    private char praeguneMängija = 'X';
    private Laud[][] ruut = new Laud[3][3];
    private Label staatus = new Label("X- i kord on");

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

        // Meetod, mis teeb laua ning reageerib iga ruudu vajutusele.
        public Laud() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(200, 200);
            this.setOnMouseClicked(e -> NupuVajutus());
        }

        // Meetod, mis tegutseb siis kui toimub nupuvajutus.
        private void NupuVajutus() {
            if(mängija == ' ' && praeguneMängija != ' '){ // Kui mängija on tühi, aga praegune mängija ei ole tühi, siis ütleb, et on praeguse mängija kord.
                setMängija(praeguneMängija);
                if(kasVõitis(praeguneMängija)){ // Kontrollib, kas praegu mängija oma kõiguga võitis või ei
                    staatus.setText(praeguneMängija + " võitis!");
                }
                else if(kasLaudOnTäis()){ // Kontrollib, kas mängulaud on täis.
                    staatus.setText("Viik!");
                    praeguneMängija = ' ';
                }
                else { // Kui mängulaud pole täis ning kumbki pole võitnud, siis on järgmise inimese kord
                    praeguneMängija = (praeguneMängija == 'X') ? 'O': 'X'; // Kui mäng peaks jätkama, siis vaatab kelle kord on ning kuvab selle ekraanile.
                    staatus.setText(praeguneMängija + " peab käima");
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

                Line joon2 = new Line(10, this.getHeight()-10,this.getWidth()-10,10);
                joon2.endXProperty().bind(this.widthProperty().subtract(10));
                joon2.startYProperty().bind(this.heightProperty().subtract(10));

                getChildren().addAll(joon1,joon2);
            } else if (mängija == 'O') {
                Ellipse ring = new Ellipse(this. getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2-10, this.getHeight() / 2 - 10);
                ring.centerXProperty().bind(this.widthProperty().divide(2));
                ring.centerYProperty().bind(this.heightProperty().divide(2));
                ring.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ring.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ring.setStroke(Color.BLACK);
                ring.setFill(Color.PINK);
                getChildren().add(ring);
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}