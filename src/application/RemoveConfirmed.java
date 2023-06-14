package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveConfirmed extends PromptWindow implements Initializable {

    @FXML
    private Label ProductCodeLabel;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label ProductQuantityLabel;

    @FXML
    private Button Remove_B;

    @FXML
    private Button Back_B;

    public RemoveConfirmed(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "RemoveConfirmed.fxml", origin);
        stage.setTitle("CONFIRMACIÓN DE RETIRO DE MATERIAL");
        //stage.setWidth();
        //stage.setHeight();
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> origin.stage.getScene().getRoot().setEffect(null));

        Back_B.setOnAction(actionEvent -> back());

        Remove_B.setOnAction(actionEvent -> {

        });
    }
}
