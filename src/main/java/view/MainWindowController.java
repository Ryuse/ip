package view;


import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import hokmah.Hokmah;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for the main GUI.
 */
public class MainWindowController extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Hokmah hokmah;

    /**
     * Initializes the scroll pane.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Hokmah instance
     */
    public void setHokmah(Hokmah h) {
        this.hokmah = h;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws InterruptedException {
        String input = userInput.getText().trim();
        String[] responses = hokmah.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBoxController.getUserDialog(input)
        );

        for (String response : responses) {
            dialogContainer.getChildren().add(DialogBoxController.getHokmahDialog(response));
        }
        userInput.clear();


        if (Arrays.asList(Hokmah.EXIT_COMMANDS).contains(input)) {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    System.exit(0);
                }
            }, 1000);
        }

        if (input.equals("help")) {
            if (HelpWindowController.isShown()) {
                dialogContainer.getChildren().add(
                        DialogBoxController.getHokmahDialog("""
                                Wait... You already have the help window open!
                                Go look for it!""")
                );
                return;
            }

            HelpWindowController.showHelpWindow();
        }
    }

    /**
     * Shows a welcome message when the application starts.
     */
    public void showWelcomeMessage() {
        for (String response : hokmah.getWelcomeMessage()) {
            dialogContainer.getChildren().add(DialogBoxController.getHokmahDialog(response));
        }
        ;
    }
}
