package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Notifications extends PromptWindow implements Initializable {

    @FXML
    private Button OK_B;

    public Notifications(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "Notifications.fxml", origin);
        stage.setTitle("NOTIFICACIONES");
        stage.setWidth(517);
        stage.setHeight(457);
        this.load();
    }

    public Notifications(SesionAtCl ses2, PromptWindow origin) throws IOException {
        super(ses2, "Notifications.fxml", origin);
        stage.setTitle("NOTIFICACIONES");
        stage.setWidth(517);
        stage.setHeight(457);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OK_B.setOnAction(actionEvent -> {
            back();
        });
    }
}
