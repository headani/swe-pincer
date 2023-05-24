package hu.unideb.inf.swe.pincer.util;

import hu.unideb.inf.swe.pincer.bla.TableBla;
import hu.unideb.inf.swe.pincer.service.TableService;
import hu.unideb.inf.swe.pincer.util.ex.TableDoesNotExistException;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TableStack extends StackPane {

    private final TableService tableService = new TableService();

    private double sceneX, sceneY, layoutX, layoutY;

    private Integer tableId;

    private final Rectangle rectangle = new Rectangle();
    private final Text number = new Text();

    private void initialize() {
        number.setText(this.tableId.toString());
        number.setFont(Font.font(24));

        rectangle.setWidth(75);
        rectangle.setHeight(75);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setStroke(Color.BLACK);

        this.setOnMousePressed(e -> {
            sceneX = e.getSceneX();
            sceneY = e.getSceneY();
            layoutX = this.getLayoutX();
            layoutY = this.getLayoutY();
        });

        this.setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - sceneX;
            double offsetY = e.getSceneY() - sceneY;
            this.setTranslateX(offsetX);
            this.setTranslateY(offsetY);
        });

        this.setOnMouseReleased(e -> {
            double landedX = layoutX + this.getTranslateX();
            double landedY = layoutY + this.getTranslateY();

            try {
                tableService.updateTablePosition(this.tableId, new Coordinates((int) landedX, (int) landedY));
            } catch (TableDoesNotExistException ex) {
                throw new RuntimeException(ex);
            }

            this.setLayoutX(landedX);
            this.setLayoutY(landedY);

            this.setTranslateX(0);
            this.setTranslateY(0);
        });

        this.getChildren().addAll(rectangle, number);
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
