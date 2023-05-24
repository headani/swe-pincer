package hu.unideb.inf.swe.pincer.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ExceptionAlert extends Alert {

    public ExceptionAlert(Throwable e) {
        super(AlertType.ERROR, e.getClass().getName() + ": " + e.getMessage(), ButtonType.OK);
        this.setHeaderText("Váratlan hiba történt");
    }
}
