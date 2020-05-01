import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Klass, mis loob reeglite akna enne mängu algust.
public class Reeglid extends Application {

    public static void display(String title){
        Stage aken = new Stage();   //Ehitab uue akna, kuhu tulevad reeglid.
        aken.initModality(Modality.APPLICATION_MODAL); //Kui reegilte aken on veel lahti, ei saa mängu alustada.
        aken.setTitle(title);
        aken.setMinWidth(600);

        Label sisu = new Label();
        sisu.setText("Mängib kaks mängijat, alustaja on esimene nime sisestaja (X). \n" +
                "Vajutage ruudule, kuhu soovite oma käiku teha. \n" +
                "Kui X mängija on oma käigu teinud, on järg O mängijal. \n"+
                "Kui kumbki mängija saab järjestikku kolm ruutu täis (horisontaalselt, vertikaalselt või diagonaalis, \n" +
                " siis on ta mängu võitnud.");

        sisu.setStyle("-fx-font-family: 'DejaVu Sans', Verdana, Helvetica, sans-serif");
        Button closeButton = new Button("Mängi!");//Kui vajutada nuppu "Mängi!", alustab mängu.
        closeButton.setOnAction(e -> {aken.close();});


        Label pealkiri = new Label();
        pealkiri.setText("Reeglid");//Akna pealkiri.
        pealkiri.setStyle("-fx-font-family: 'DejaVu Sans', Arial, Helvetica, sans-serif");

        VBox uus = new VBox();
        uus.setAlignment(Pos.CENTER);
        uus.setSpacing(30);

        uus.getChildren().addAll(pealkiri,sisu,closeButton);

        Scene scene = new Scene(uus, 600, 600);
        aken.setScene(scene);
        aken.showAndWait();//Programm ei lähe edasi, kuni reeglite aken on suletud.
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
