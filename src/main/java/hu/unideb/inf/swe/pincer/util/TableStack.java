package hu.unideb.inf.swe.pincer.util;

import hu.unideb.inf.swe.pincer.Main;
import hu.unideb.inf.swe.pincer.bla.TableBla;
import hu.unideb.inf.swe.pincer.service.TableService;
import hu.unideb.inf.swe.pincer.util.ex.TableDoesNotExistException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class TableStack extends StackPane {

    private final TableService tableService = new TableService();

    private double sceneX, sceneY, layoutX, layoutY;

    private Integer tableId;

    private final Rectangle rectangle = new Rectangle();
    private final Text number = new Text();

    private final ContextMenu contextMenu = new ContextMenu();

    private void initialize() {
        number.setText(this.tableId.toString());
        number.setFont(Font.font(24));

        rectangle.setWidth(75);
        rectangle.setHeight(75);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setStroke(Color.BLACK);

        this.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                TableIdQueue.getInstance().add(tableId);
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("table/table.fxml"));
                Stage stage = new Stage();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException ex) {
                    ExceptionAlert alert = new ExceptionAlert(ex);
                    alert.showAndWait();
                }
                stage.setTitle("Asztal " + this.tableId);
                stage.minWidthProperty().set(480);
                stage.minHeightProperty().set(640);
                stage.maxWidthProperty().set(480);
                stage.maxHeightProperty().set(640);
                stage.resizableProperty().setValue(false);
                stage.setScene(scene);
                stage.show();
            } else if (e.getButton() == MouseButton.PRIMARY) {
                sceneX = e.getSceneX();
                sceneY = e.getSceneY();
                layoutX = this.getLayoutX();
                layoutY = this.getLayoutY();
            }
        });

        this.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (e.getSceneX() < 1230 && e.getSceneX() > 50) {
                    double offsetX = e.getSceneX() - sceneX;
                    this.setTranslateX(offsetX);
                }
                if (e.getSceneY() < 670 && e.getSceneY() > 50) {
                    double offsetY = e.getSceneY() - sceneY;
                    this.setTranslateY(offsetY);
                }
            }
        });

        this.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double landedX = layoutX + this.getTranslateX();
                double landedY = layoutY + this.getTranslateY();

                try {
                    tableService.updateTablePosition(this.tableId, new Coordinates((int) landedX, (int) landedY));
                } catch (TableDoesNotExistException ex) {
                    ExceptionAlert alert = new ExceptionAlert(ex);
                    alert.showAndWait();
                }

                this.setLayoutX(landedX);
                this.setLayoutY(landedY);

                this.setTranslateX(0);
                this.setTranslateY(0);
            }
        });

        MenuItem cmDelete = new MenuItem("Asztal törlése");
        cmDelete.setOnAction(e -> {
            try {
                if (tableService.getTableById(this.tableId).getItems().isEmpty()) {
                   tableService.deleteTable(this.tableId);
                   this.setDisabled(true);
                   this.setVisible(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                    alert.setHeaderText("Az asztal nem üres");
                    alert.showAndWait();
                }
            } catch (TableDoesNotExistException ex) {
                ExceptionAlert alert = new ExceptionAlert(ex);
                alert.showAndWait();
            }
        });

        MenuItem cmPay = new MenuItem("Kifizetés");
        cmPay.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ki lett fizetve a számla?", ButtonType.YES, ButtonType.NO);
            try {
                alert.setHeaderText(DiscountCalculator.calculate(tableService.getSubtotalForTable(this.tableId)) + " Ft");
                Optional<ButtonType> res = alert.showAndWait();
                if (res.isPresent() && res.get() == ButtonType.YES) {
                    tableService.emptyTable(this.tableId);
                    this.setFreeState();
                }
            } catch (TableDoesNotExistException | ArithmeticException ex) {
                ExceptionAlert exceptionAlert = new ExceptionAlert(ex);
                exceptionAlert.showAndWait();
            }
        });

        contextMenu.getItems().addAll(cmDelete, cmPay);

        this.setOnContextMenuRequested(e -> contextMenu.show(this, e.getScreenX(), e.getScreenY()));

        this.getChildren().addAll(rectangle, number);

        TableStatePropertyList.getInstance().add(new TableStateProperty(this.tableId, this.rectangle.fillProperty()));
    }
    
    public TableStack(TableBla tableBla) {
        this.tableId = tableBla.getId();

        this.setLayoutX(tableBla.getX());
        this.setLayoutY(tableBla.getY());

        if (tableBla.getState()) {
            setFreeState();
        } else {
            setOccupiedState();
        }

        initialize();
    }

    public TableStack(Integer tableId, Coordinates coordinates) {
        this.tableId = tableId;

        this.setLayoutX(coordinates.getX());
        this.setLayoutY(coordinates.getY());

        setFreeState();

        initialize();
    }

    public void setFreeState() {
        rectangle.setFill(Color.GREENYELLOW);
    }

    public void setOccupiedState() {
        rectangle.setFill(Color.INDIANRED);
    }


    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
}
