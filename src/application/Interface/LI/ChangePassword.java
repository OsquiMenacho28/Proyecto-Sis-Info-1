package application.Interface.LI;

import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePassword extends PromptWindow implements Initializable {

    @FXML
    private Button Next_B;

    @FXML
    private Button Back_B;

    @FXML
    private Label UserNameLabel;

    @FXML
    private PasswordField NewPassword1;

    @FXML
    private PasswordField NewPassword2;

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public ChangePassword(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "ChangePassword.fxml", origin);
        stage.setTitle("¿OLVIDASTE TU CONTRASEÑA? - CAMBIAR CONTRASEÑA");
        stage.setHeight(603);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> back());

        NewPassword1.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DOWN) {
                NewPassword2.requestFocus();
            }
        });
        NewPassword2.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                NewPassword1.requestFocus();
            }
        });

        Next_B.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CAMBIAR CONTRASEÑA");
            alert.setHeaderText("¡EXITO!");
            alert.setContentText("Tu contraseña fue actualizada correctamente");
            alert.initStyle(StageStyle.DECORATED);
            stage.getScene().getRoot().setEffect(blurEffect);
            alert.showAndWait();
            dispose();
        });

        Back_B.setOnAction(actionEvent -> back());
    }
}
