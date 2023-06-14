package application;

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
    private TextField User_F;

    public ForgottenPassword(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "ForgottenPassword.fxml", origin);
        stage.setTitle("OLVIDÓ SU CONTRASEÑA? - FERRETERÍA DIMACO");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        origin.dispose();
        try {
            ChangePassword changePassword = new ChangePassword(null, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
