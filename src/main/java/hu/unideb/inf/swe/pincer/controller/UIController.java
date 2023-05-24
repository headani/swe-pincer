package hu.unideb.inf.swe.pincer.controller;

import hu.unideb.inf.swe.pincer.service.TableService;
import hu.unideb.inf.swe.pincer.util.Coordinates;
import hu.unideb.inf.swe.pincer.util.TableStack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    private final TableService tableService = new TableService();

    @FXML
    public Button addButton;

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
}
