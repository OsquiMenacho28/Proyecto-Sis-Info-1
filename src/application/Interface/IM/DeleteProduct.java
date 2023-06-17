package application.Interface.IM;

import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
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

public class DeleteProduct extends PromptWindow implements Initializable {

    @FXML
    private Button Back_B;

    @FXML
    private Button Delete_B;

    @FXML
    private Label ProductCodeLabel;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label ProductDescriptionLabel;

    @FXML
    private Label ProductPriceLabel;

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public DeleteProduct(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "DeleteProduct.fxml", origin);
        stage.setTitle("ELIMINAR PRODUCTO");
        stage.setWidth(726);
        stage.setHeight(441);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> origin.stage.getScene().getRoot().setEffect(null));

        Back_B.setOnAction(actionEvent -> back());

        Delete_B.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ELIMINAR PRODUCTO");
            alert.setHeaderText("¡EXITO!");
            alert.setContentText("El producto " + ProductNameLabel.getText() + " se eliminó correctamente");
            alert.initStyle(StageStyle.DECORATED);
            stage.getScene().getRoot().setEffect(blurEffect);
            alert.showAndWait();
            back();
        });
    }
}
