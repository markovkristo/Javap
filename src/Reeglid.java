import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Reeglid {
    public static void display(String title){
        Stage aken = new Stage();
        aken.initModality(Modality.APPLICATION_MODAL);
        aken.setTitle(title);
        aken.setMinWidth(600);
        String tekst=
                "Mängib kaks mängijat, alustaja on esimene nime sisestaja (X). \n" +
                        "Vajutage ruudule, kuhu soovite oma käiku teha. \n" +
                        "Kui X mängija on oma käigu teinud, on järg O mängijal. \n"+
                        "Kui kumbki mängija saab järjestikku kolm ruutu täis (horisontaalselt, vertikaalselt või diagonaalis, \n" +
                        " siis on ta mängu võitnud.";
        String reeglid = "Reeglid";

        Label label = new Label();
        label.setText(tekst);

        label.setStyle("-fx-font-family: 'DejaVu Sans', Arial, Helvetica, sans-serif");
        Button closeButton = new Button("Mängi!");

        closeButton.setOnAction(e -> aken.close());

        Label pealkiri = new Label();
        pealkiri.setText(reeglid);
        pealkiri.setStyle("-fx-font-family: 'DejaVu Sans', Arial, Helvetica, sans-serif");

        VBox uus = new VBox();
        uus.setAlignment(Pos.CENTER);
        uus.setSpacing(30);

        uus.getChildren().addAll(pealkiri,label,closeButton);

        Scene scene = new Scene(uus, 600, 600);
        aken.setScene(scene);
        aken.showAndWait();
    }

}
