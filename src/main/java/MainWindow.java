import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keef.Keef;
import keef.ui.Ui;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Keef keef;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /** Initializes Keef's welcome message */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        Ui ui = new Ui();
        dialogContainer.getChildren().addAll(
                DialogBox.getKeefDialog(ui.showWelcome(), dukeImage)
        );
    }

    /** Injects the Keef instance */
    public void setKeef(Keef k) {
        keef = k;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Keef's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.isEmpty()) {
            return;
        }

        Stage stage = (Stage) scrollPane.getScene().getWindow();

        String response = keef.getResponse(input, stage);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKeefDialog(response, dukeImage)
        );

        userInput.clear();
    };
}
