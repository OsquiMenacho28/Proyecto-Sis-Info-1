package application.Interface.LI;

import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgottenPassword extends PromptWindow implements Initializable {

    @FXML
    private Button Next_B;

    @FXML
    private Button Back_B;

    @FXML
    TextField User_F;

    public ForgottenPassword(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "ForgottenPassword.fxml", origin);
        stage.setTitle("¿OLVIDASTE TU CONTRASEÑA?");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> back());

        Next_B.setOnAction((e) -> {
            openChangePassword();
        });

        Back_B.setOnAction((e) -> back());

        this.setOnKeyReleased((e) -> {
            KeyCode pKey = e.getCode();
            if (pKey == KeyCode.ENTER) {
                openChangePassword();
            }
        });
    }

    public void openChangePassword() {
        dispose();
        try {
            ChangePassword changePassword = new ChangePassword(null, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
