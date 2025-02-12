package view;


import static hokmah.Hokmah.DATE_TIME_FORMAT;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Controller for the Help GUI.
 */
public class HelpWindowController extends AnchorPane {
    @FXML
    private Text helpLabel;

    @FXML
    private GridPane commandGrid;

    @FXML
    private Button exitButton;


    private static boolean isShown = false;

    public static boolean isShown() {
        return isShown;
    }


    @FXML
    public void initialize() {
        helpLabel.setText("Here's what I can do. You better be grateful.");

        String[][] helpTexts = {
                {"list", "Shows all the tasks in the list"},
                {"todo [name]", "Adds a todo task to the task list"},
                {"deadline [name] /by [" + DATE_TIME_FORMAT + "]", "Adds a deadline task to the task list"},
                {"event [name] /from [" + DATE_TIME_FORMAT + "] /to [" + DATE_TIME_FORMAT + "]", "Adds an event task to the task list"},
                {"mark [task number]", "Marks the task at [task number] in the task list as completed"},
                {"unmark [task number]", "Marks the task at [task number] in the task list as incomplete"},
                {"delete [task number]", "Deletes the task at [task number] in the task list"},
                {"upcoming /on [" + DATE_TIME_FORMAT + "]", "Shows all the tasks that are happening on the given date"},
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

    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        isShown = false;
        stage.close();

    }

    public static void showHelpWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/HelpWindow.fxml"));
            AnchorPane helpWindow = fxmlLoader.load();
            Stage helpStage = new Stage();

            helpStage.setTitle("Help");
            helpStage.setResizable(false);
            helpStage.setScene(new Scene(helpWindow));

            isShown = true;
            helpStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
