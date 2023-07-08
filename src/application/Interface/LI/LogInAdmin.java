package application.Interface.LI;

import application.FlowController.SesionAtCl;
import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import application.FlowController.User;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInAdmin extends LogIn {

    public  LogInAdmin(Stage stage, PromptWindow origin) throws IOException {
        super((SesionAdmin) null, stage, "LogInAdmin.fxml", origin, "FERRETERÍA DIMACO - ADMINISTRADOR");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FPassword_B.setOnAction(actionEvent -> {
            dispose();
            try {
                ForgottenPassword forgottenPassword = new ForgottenPassword((SesionAdmin) null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        LogIn_B.setOnAction((e) -> {
            open();
            dispose();
        });

        Back_B.setOnAction((e) -> back());

        User_F.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                Password_F.requestFocus();
            }
        });
        Password_F.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                User_F.requestFocus();
            }
        });

        this.setOnKeyReleased((e) -> {
            KeyCode pKey = e.getCode();
            if (pKey == KeyCode.ENTER) {
                open();
                dispose();
            }
        });
    }

    @Override
    public void openWindow(User user) throws Exception {
        new SesionAdmin(user);
    }
}
