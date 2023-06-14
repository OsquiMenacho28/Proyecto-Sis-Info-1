package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.stage.StageStyle;

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

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public RemoveConfirmed(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "RemoveConfirmed.fxml", origin);
        stage.setTitle("CONFIRMACIÓN DE RETIRO DE MATERIAL");
        stage.setWidth(621);
        stage.setHeight(367);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> {
            origin.stage.getScene().getRoot().setEffect(null);
            back();
        });

        Back_B.setOnAction(actionEvent -> back());

        Remove_B.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("RETIRO DE MATERIAL CONFIRMADO");
            alert.setHeaderText("¡EXITO!");
            alert.setContentText("El producto " + ProductNameLabel.getText() + " se retiró correctamente del inventario");
            alert.initStyle(StageStyle.DECORATED);
            stage.getScene().getRoot().setEffect(blurEffect);
            alert.showAndWait();
            dispose();
            origin.back();
        });
    }
}
