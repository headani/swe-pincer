package hu.unideb.inf.swe.pincer;

import hu.unideb.inf.swe.pincer.model.Item;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.util.List;

public class EntryPoint extends Application {

    public void launcher(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/pincer", "root", "root");
        List<Item> items = jdbi.withHandle(handle -> {
            handle.execute("create table if not exists item (id int auto_increment not null, name varchar(255) not null, price int not null, primary key(id))");

            return handle.createQuery("SELECT * FROM item ORDER BY name")
                    .mapToBean(Item.class)
                    .list();
        });

        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("ui/ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pincér");
        stage.minWidthProperty().set(1280);
        stage.minHeightProperty().set(720);
        stage.resizableProperty().setValue(false);
        stage.onCloseRequestProperty().set(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Biztos ki akarsz lépni?");
            var resultObj = alert.showAndWait();
            if (resultObj.isPresent() && resultObj.get() == ButtonType.OK) {
                Platform.exit();
            }
            event.consume();
        });
        stage.setScene(scene);
        stage.show();
    }
}
