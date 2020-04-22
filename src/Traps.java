import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
        for (int i = 0; i < 3; i++) {
            if (ruut[i][0].getMängija() == mängija && ruut[i][1].getMängija() == mängija && ruut[i][2].getMängija() == mängija)
                return true;
        }
        for (int i = 0; i < 3; i++) {
            if (ruut[0][i].getMängija() == mängija && ruut[1][i].getMängija() == mängija && ruut[2][i].getMängija() == mängija)
                return true;
        }
        if (ruut[0][0].getMängija() == mängija && ruut[1][1].getMängija() == mängija && ruut[2][2].getMängija() == mängija)
            return true;
        if (ruut[0][2].getMängija() == mängija && ruut[1][1].getMängija() == mängija && ruut[2][0].getMängija() == mängija)
            return true;
        return false;
    }

    public class Laud extends Pane {
        private char mängija = ' ';
        private Text kujund = new Text();

        public Laud() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(200, 200);
            this.setOnMouseClicked(e -> NupuVajutus());
        }

        private void NupuVajutus() {
            if(mängija == ' ' && praeguneMängija != ' '){
                setMängija(praeguneMängija);
                if(kasVõitis(praeguneMängija)){
                    staatus.setText(praeguneMängija + " võitis!");
                }
                else if(kasLaudOnTäis()){
                    staatus.setText("Viik!");
                    praeguneMängija = ' ';
                }
                else {
                    praeguneMängija = (praeguneMängija == 'X') ? 'O': 'X'; // Kui mäng peaks jätkama, siis vaatab kelle kord on ning kuvab selle ekraanile.
                    staatus.setText(praeguneMängija + " peab käima");
                }
            }

        }

        public char getMängija() {
            return mängija;
        }

        public void  setMängija(char x) {
            kujund.setFont(Font.font(75));
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