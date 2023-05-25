package hu.unideb.inf.swe.pincer.controller;

import hu.unideb.inf.swe.pincer.Main;
import hu.unideb.inf.swe.pincer.service.TableService;
import hu.unideb.inf.swe.pincer.util.Coordinates;
import hu.unideb.inf.swe.pincer.util.ExceptionAlert;
import hu.unideb.inf.swe.pincer.util.TableStack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    private final TableService tableService = new TableService();

    @FXML
    public Button addButton;

    @FXML
    public Button menuButton;

    @FXML
    public GridPane gridPane;

    @FXML
    public Pane box;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableService.getAllTables().forEach(t -> box.getChildren().add(new TableStack(t)));
    }

    @FXML
    public void addTableButtonEvent() {
        Coordinates coordinates = new Coordinates(100, 100);
        TableStack tableStack = new TableStack(tableService.addNewTable(coordinates), coordinates);

        box.getChildren().add(tableStack);
    }

    @FXML
    public void menuButtonEvent() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu/menu.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            ExceptionAlert alert = new ExceptionAlert(ex);
            alert.showAndWait();
        }
        stage.setTitle("Menü");
        stage.minWidthProperty().set(480);
        stage.minHeightProperty().set(640);
        stage.maxWidthProperty().set(480);
        stage.maxHeightProperty().set(640);
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();
    }
}
