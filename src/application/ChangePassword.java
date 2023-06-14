package application;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePassword extends PromptWindow implements Initializable {
    public ChangePassword(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "ChangePassword.fxml", origin);
        stage.setTitle("OLVIDÓ SU CONTRASEÑA? - CAMBIAR CONTRASEÑA");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
