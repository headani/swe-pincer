package hu.unideb.inf.swe.pincer.controller;

import hu.unideb.inf.swe.pincer.bla.ItemBla;
import hu.unideb.inf.swe.pincer.bla.ItemOccurrenceBla;
import hu.unideb.inf.swe.pincer.service.ItemService;
import hu.unideb.inf.swe.pincer.service.TableService;
import hu.unideb.inf.swe.pincer.util.AutocompletionTextField;
import hu.unideb.inf.swe.pincer.util.ExceptionAlert;
import hu.unideb.inf.swe.pincer.util.TableIdQueue;
import hu.unideb.inf.swe.pincer.util.ex.ItemDoesNotExistException;
import hu.unideb.inf.swe.pincer.util.ex.TableDoesNotExistException;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class TableController implements Initializable {

    private final TableService tableService = new TableService();
    private final ItemService itemService = new ItemService();

    @FXML
    public GridPane gridPane;

    @FXML
    public TableView<ItemOccurrenceBla> tableView;

    private Integer tableId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.tableId = TableIdQueue.getInstance().remove();
        } catch (NoSuchElementException ex) {
            ExceptionAlert alert = new ExceptionAlert(ex);
            alert.showAndWait();
        }
        List<ItemOccurrenceBla> occurrenceList = new ArrayList<>();
        try {
            List<String> itemList = tableService.getTableItemList(this.tableId);
            Set<String> distinct = new HashSet<>(itemList);
            occurrenceList = distinct.stream().map(d -> new ItemOccurrenceBla(d, Collections.frequency(itemList, d))).collect(Collectors.toUnmodifiableList());
        } catch (TableDoesNotExistException ex) {
            ExceptionAlert alert = new ExceptionAlert(ex);
            alert.showAndWait();
        }

        TableColumn<ItemOccurrenceBla, String> nameColumn = new TableColumn<>("Terméknév");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setResizable(false);
        nameColumn.setEditable(false);
        nameColumn.setReorderable(false);

        TableColumn<ItemOccurrenceBla, String> occurrenceColumn = new TableColumn<>("Darabszám");
        occurrenceColumn.setCellValueFactory(new PropertyValueFactory<>("occurrence"));
        occurrenceColumn.setResizable(false);
        occurrenceColumn.setEditable(false);
        occurrenceColumn.setReorderable(false);

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(occurrenceColumn);

        List<ItemOccurrenceBla> finalOccurrenceList = new ArrayList<>(occurrenceList);
        finalOccurrenceList.forEach(o -> tableView.getItems().add(o));

        tableView.setRowFactory(f -> {
            TableRow<ItemOccurrenceBla> row = new TableRow<>();

            ContextMenu rowMenu = new ContextMenu();
            ContextMenu alreadyExistsMenu = new ContextMenu();

            MenuItem newItemItem = new MenuItem("Termék számlához adása");
            MenuItem itemAlreadyExistsItem = new MenuItem("Ezen termékből még egy");

            newItemItem.setOnAction(e -> {
                Dialog<String> dialog = new Dialog<>();
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

                GridPane dialogPane = new GridPane();
                AutocompletionTextField input = new AutocompletionTextField();
                Label inputLabel = new Label();

                input.getEntries().addAll(itemService.getAllItems().stream().map(ItemBla::getName).collect(Collectors.toUnmodifiableList()));
                inputLabel.setText("Terméknév");
                dialogPane.add(inputLabel, 0, 0);
                dialogPane.add(input, 1, 0);

                dialog.setOnShown(ose -> {
                    Platform.runLater(input::requestFocus);
                    ose.consume();
                });

                dialog.getDialogPane().setContent(dialogPane);
                dialog.setResultConverter(p -> {
                    if (p == ButtonType.OK) {
                        return input.getText();
                    }
                    return null;
                });

                Optional<String> item = dialog.showAndWait();
                if (item.isPresent()) {
                    try {
                        tableService.addItemToTable(this.tableId, item.get());
                        if (finalOccurrenceList.stream().map(ItemOccurrenceBla::getName).collect(Collectors.toUnmodifiableList()).contains(item.get())) {
                            finalOccurrenceList.stream().filter(name -> name.getName().equals(item.get())).findFirst().get().setOccurrence(finalOccurrenceList.stream().filter(name -> name.getName().equals(item.get())).findFirst().get().getOccurrence() + 1);
                        } else {
                            ItemOccurrenceBla newEntry = new ItemOccurrenceBla(item.get(), 1);
                            finalOccurrenceList.add(newEntry);
                            tableView.getItems().add(newEntry);
                        }
                    } catch (TableDoesNotExistException | ItemDoesNotExistException ex) {
                        ExceptionAlert alert = new ExceptionAlert(ex);
                        alert.showAndWait();
                    }
                }
            });

            itemAlreadyExistsItem.setOnAction(e -> {
                try {
                    tableService.addItemToTable(this.tableId, row.getItem().getName());
                    finalOccurrenceList.stream().filter(name -> name.getName().equals(row.getItem().getName())).findFirst().get().setOccurrence(finalOccurrenceList.stream().filter(name -> name.getName().equals(row.getItem().getName())).findFirst().get().getOccurrence() + 1);
                } catch (TableDoesNotExistException | ItemDoesNotExistException ex) {
                    ExceptionAlert alert = new ExceptionAlert(ex);
                    alert.showAndWait();
                }
            });

            rowMenu.getItems().add(newItemItem);
            alreadyExistsMenu.getItems().add(itemAlreadyExistsItem);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then(rowMenu)
                            .otherwise(alreadyExistsMenu)
            );

            return row;
        });

    }
}
