package hu.unideb.inf.swe.pincer;

import hu.unideb.inf.swe.pincer.jdbi.InitializeDatabase;
import hu.unideb.inf.swe.pincer.util.ExceptionAlert;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class EntryPoint extends Application {

    public void launcher(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        InitializeDatabase.Initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ui/ui.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            ExceptionAlert alert = new ExceptionAlert(ex);
            alert.showAndWait();
        }
        stage.setTitle("Pincér");
        stage.minWidthProperty().set(1280);
        stage.minHeightProperty().set(720);
        stage.maxWidthProperty().set(1280);
        stage.maxHeightProperty().set(720);
        stage.resizableProperty().setValue(false);
        stage.onCloseRequestProperty().set(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Biztos ki akarsz lépni?");
            var res = alert.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
                Platform.exit();
            }
            event.consume();
        });
        stage.setScene(scene);
        stage.show();
    }
}
