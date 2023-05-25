package hu.unideb.inf.swe.pincer.util;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;

public class TableStateProperty {

    private Integer id;

    private final ObjectProperty<Paint> fill;

    public TableStateProperty(Integer id, ObjectProperty<Paint> fill) {
        this.id = id;
        this.fill = fill;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paint getFill() {
        return fill.get();
    }

    public ObjectProperty<Paint> fillProperty() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill.set(fill);
    }
}
