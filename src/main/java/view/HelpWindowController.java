package view;


import static hokmah.Hokmah.DATETIME_INPUT_FORMAT;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Controller for the Help GUI.
 */
public class HelpWindowController extends AnchorPane {
    private static boolean isShown = false;

    @FXML
    private Text helpLabel;

    @FXML
    private GridPane commandGrid;

    @FXML
    private Button exitButton;


    public static boolean isShown() {
        return isShown;
    }


    /**
     * initializes the HelpWindow with respective help texts.
     *
     */
    @FXML
    public void initialize() {
        helpLabel.setText("Here's what I can do. You better be grateful.");

        String[][] helpTexts = {
                {"list", "Shows all the tasks in the list"},
                {"todo [name]", "Adds a todo task to the task list"},
                {"deadline [name] /by ["
                        + DATETIME_INPUT_FORMAT + "]", "Adds a deadline task to the task list"},
                {"event [name] /from ["
                        + DATETIME_INPUT_FORMAT + "] /to ["
                        + DATETIME_INPUT_FORMAT + "]", "Adds an event task to the task list"},
                {"mark [task index]", "Marks the task at [task index] in the task list as completed"},
                {"unmark [task index]", "Marks the task at [task index] in the task list as incomplete"},
                {"delete [task index]", "Deletes the task at [task index] in the task list"},
                {"upcoming /on ["
                        + DATETIME_INPUT_FORMAT + "]", "Shows all the tasks that are happening on the given date"},
                {"find [keyword]", "Finds tasks containing the specified keyword"},
                {"bye", "Only if you want to leave. It's not like I wanted you to be here."}
        };

        // Add the help text to the GridPane
        for (int i = 0; i < helpTexts.length; i++) {

            Label commandText = new Label(helpTexts[i][0]);
            Label descriptionText = new Label(helpTexts[i][1]);

            commandText.getStyleClass().add("text-table-row");
            descriptionText.getStyleClass().add("text-table-row");

            commandGrid.addRow(i + 1,
                    commandText,
                    descriptionText);
        }
    }

    /**
     * Closes the help window.
     *
     */
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        isShown = false;
        stage.close();

    }

    /**
     * Shows the help window.
     *
     * @throws IOException if the fxml file for help window cannot be found.
     */
    public static void showHelpWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/HelpWindow.fxml"));
            AnchorPane helpWindow = fxmlLoader.load();
            Stage helpStage = new Stage();

            helpStage.setTitle("Help");
            helpStage.getIcons().add(new Image("/images/icon.png"));
            helpStage.setResizable(false);
            helpStage.setScene(new Scene(helpWindow));

            isShown = true;

            helpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    isShown = false;
                }
            });

            helpStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
