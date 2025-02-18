package view;


import java.io.IOException;

import hokmah.Hokmah;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Hokmah using FXML.
 */
public class Main extends Application {

    private final Hokmah hokmah = new Hokmah();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setTitle("Hokmah");
            stage.getIcons().add(new Image("/images/icon.png"));
            stage.setMinHeight(220);
            stage.setMinWidth(700);

            assert stage.getMinHeight() == 220;
            assert stage.getMinWidth() == 700;

            fxmlLoader.<MainWindowController>getController().setHokmah(hokmah);
            fxmlLoader.<MainWindowController>getController().showWelcomeMessage();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
