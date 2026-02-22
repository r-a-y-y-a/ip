import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * DialogBox represents a single message in a chat-like dialog interface.
 * It consists of an ImageView representing the speaker's avatar/face and a label
 * containing the speaker's message text.
 *
 * DialogBox components are typically added to a VBox to display a sequence of
 * messages in a chat-like conversation.
 *
 * The layout can be flipped using the flip() method to display bot responses
 * with the avatar on the left and the message on the right, while user messages
 * have the avatar on the right.
 *
 * This class uses FXML for layout definition and supports both user and bot
 * message styling via static factory methods.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box layout such that the ImageView is on the left
     * and the text label is on the right.
     *
     * This method is typically used for bot response messages to visually
     * distinguish them from user messages. It reverses the child node order,
     * adjusts alignment, and applies a reply-specific CSS style.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a DialogBox for displaying user input messages.
     *
     * This static factory method constructs a new DialogBox with the given text
     * and image, maintaining the standard layout where the avatar is on the right.
     *
     * @param text the user's message text to display
     * @param img the image representing the user's avatar
     * @return a new DialogBox configured for displaying a user message
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a DialogBox for displaying Fishball bot response messages.
     *
     * This static factory method constructs a new DialogBox with the given text
     * and image, then flips the layout so the avatar appears on the left and
     * the message text appears on the right. This provides visual distinction
     * from user messages.
     *
     * @param text the Fishball bot's response message text to display
     * @param img the image representing the Fishball bot's avatar
     * @return a new DialogBox configured for displaying a bot response
     */
    public static DialogBox getFishballDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
