package hu.unideb.inf.swe.pincer.controller;

import hu.unideb.inf.swe.pincer.bla.ItemBla;
import hu.unideb.inf.swe.pincer.service.ItemService;
import hu.unideb.inf.swe.pincer.util.ExceptionAlert;
import hu.unideb.inf.swe.pincer.util.ex.ItemAlreadyExistsException;
import hu.unideb.inf.swe.pincer.util.ex.ItemDoesNotExistException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final ItemService itemService = new ItemService();

    @FXML
    public GridPane gridPane;

    @FXML
    public TableView<ItemBla> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableColumn<ItemBla, String> nameColumn = new TableColumn<>("Terméknév");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setResizable(false);
        nameColumn.setEditable(false);
        nameColumn.setReorderable(false);

        TableColumn<ItemBla, String> priceColumn = new TableColumn<>("Termékár");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setResizable(false);
        priceColumn.setEditable(false);
        priceColumn.setReorderable(false);

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(priceColumn);

        itemService.getAllItems().forEach(i -> tableView.getItems().add(i));

        tableView.setRowFactory(f -> {
            TableRow<ItemBla> row = new TableRow<>();

            ContextMenu rowMenu = new ContextMenu();
            ContextMenu newItemRowMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Termékár változtatás");
            MenuItem removeItem = new MenuItem("Termék eltávolítás");
            MenuItem newItemItem = new MenuItem("Új termék");

            removeItem.setOnAction(e -> {
                try {
                    itemService.deleteItemByName(row.getItem().getName());
                    tableView.getItems().remove(row.getItem());
                } catch (ItemDoesNotExistException ex) {
                    ExceptionAlert alert = new ExceptionAlert(ex);
                    alert.showAndWait();
                }
            });

            editItem.setOnAction(e -> {
                Dialog<Integer> dialog = new Dialog<>();
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

                GridPane dialogPane = new GridPane();
                TextField input = new TextField();
                Label inputLabel = new Label();

                inputLabel.setText("Új ár");
                dialogPane.add(inputLabel, 0, 0);
                dialogPane.add(input, 1, 0);

                dialog.setOnShown(ose -> {
                    Platform.runLater(input::requestFocus);
                    ose.consume();
                });

                dialog.getDialogPane().setContent(dialogPane);
                dialog.setResultConverter(p -> {
                    if (p == ButtonType.OK) {
                        try {
                            return Integer.parseInt(input.getText());
                        } catch (NumberFormatException ex) {
                            return null;
                        }
                    }
                    return null;
                });

                Optional<Integer> newPrice = dialog.showAndWait();
                if (newPrice.isPresent()) {
                    row.getItem().setPrice(newPrice.get());
                    try {
                        itemService.updateItemPriceByName(row.getItem().getName(), newPrice.get());
                    } catch (ItemDoesNotExistException ex) {
                        ExceptionAlert alert = new ExceptionAlert(ex);
                        alert.showAndWait();
                    }
                }
            });

            newItemItem.setOnAction(e -> {
                Dialog<ItemBla> dialog = new Dialog<>();
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

                GridPane dialogPane = new GridPane();
                TextField inputName = new TextField();
                Label inputLabelName = new Label();
                TextField inputPrice = new TextField();
                Label inputLabelPrice = new Label();

                inputLabelName.setText("Terméknév");
                inputLabelPrice.setText("Termékár");

                dialogPane.add(inputLabelName, 0, 0);
                dialogPane.add(inputName, 1, 0);
                dialogPane.add(inputLabelPrice, 0, 1);
                dialogPane.add(inputPrice, 1, 1);

                dialog.setOnShown(ose -> {
                    Platform.runLater(inputName::requestFocus);
                    ose.consume();
                });

                dialog.getDialogPane().setContent(dialogPane);
                dialog.setResultConverter(p -> {
                    if (p == ButtonType.OK) {
                        try {
                            return new ItemBla(inputName.getText(), Integer.parseInt(inputPrice.getText()));
                        } catch (NumberFormatException ex) {
                            return null;
                        }
                    }
                    return null;
                });

                Optional<ItemBla> newItem = dialog.showAndWait();
                if (newItem.isPresent()) {
                    try {
                        itemService.addNewItem(newItem.get());
                        tableView.getItems().add(newItem.get());
                    } catch (ItemAlreadyExistsException ex) {
                        ExceptionAlert alert = new ExceptionAlert(ex);
                        alert.showAndWait();
                    }
                }
            });

            rowMenu.getItems().addAll(editItem, removeItem);
            newItemRowMenu.getItems().add(newItemItem);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then(newItemRowMenu)
                            .otherwise(rowMenu)
            );

            return row;
        });
    }
}
