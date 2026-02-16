import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import keef.Keef;
/**
 * A GUI for Keef using FXML.
 */
public class Main extends Application {

    private Keef keef = new Keef("./data/keef.txt");

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Keef ChatBot");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setKeef(keef); // inject the Keef instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
