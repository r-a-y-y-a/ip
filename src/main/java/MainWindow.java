import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * MainWindow is the FXML controller for the main GUI window of the Fishball chatbot.
 * It manages user interactions with the GUI, processes user input through the Fishball
 * chatbot instance, and displays both user messages and bot responses in a dialog format.
 *
 * This class handles:
 * - Initialization of UI components and their bindings
 * - Injection of the Fishball instance
 * - Processing user input and displaying responses
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class MainWindow extends AnchorPane {
    /** ScrollPane that provides scrollable view of the dialog container. */
    @FXML
    private ScrollPane scrollPane;

    /** VBox container that holds all DialogBox components. */
    @FXML
    private VBox dialogContainer;

    /** TextField where users enter their input messages. */
    @FXML
    private TextField userInput;

    /** Button that triggers the handleUserInput() method when clicked. */
    @FXML
    private Button sendButton;

    /** Reference to the Fishball chatbot instance. */
    private Fishball fishball;

    /** Reference to the primary stage for closing the window. */
    private Stage stage;

    /** Image displayed for user messages in the dialog. */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));

    /** Image displayed for Fishball bot messages in the dialog. */
    private Image fishballImage = new Image(this.getClass().getResourceAsStream("/images/fishball.jpeg"));

    /**
     * Initializes the MainWindow after its FXML components have been loaded.
     * Binds the scroll pane's vertical value to track the dialog container's height
     * automatically, allowing the view to scroll to the latest message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Fishball chatbot instance into this controller.
     * Must be called before handleUserInput() is used.
     *
     * @param f the Fishball instance to use for processing user input
     */
    public void setFishball(Fishball f) {
        fishball = f;
    }

    /**
     * Sets the primary stage reference for the GUI.
     * Used to close the window when the bye command is executed.
     *
     * @param s the primary Stage
     */
    public void setStage(Stage s) {
        stage = s;
    }

    /**
     * Handles user input by processing it through the Fishball chatbot
     * and displaying both the user message and the bot response as DialogBox components.
     *
     * This method:
     * - Retrieves text from the userInput TextField
     * - Sends it to Fishball for processing
     * - Creates dialog boxes for both user input and bot response
     * - Appends them to the dialog container
     * - Clears the input field
     * - Checks if the application should close (bye command) and closes the stage if needed
     *
     * This method is triggered by the FXML sendButton click event.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = fishball.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, fishballImage)
        );
        userInput.clear();

        // Close the window if the bye command was executed
        if (!fishball.isRunning()) {
            Platform.exit();
        }
    }
}
