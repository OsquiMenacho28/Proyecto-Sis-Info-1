package application.Interface.LI;

import application.Interface.PromptWindow;
import application.FlowController.SesionAtCl;
import application.FlowController.User;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInAtCl extends LogIn {

    public  LogInAtCl(Stage stage, PromptWindow origin) throws IOException {
        super((SesionAtCl) null, stage, "LogInAtCl.fxml", origin, "FERRETERÍA DIMACO - ATENCIÓN AL CLIENTE");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FPassword_B.setOnAction(actionEvent -> {
            dispose();
            try {
                ForgottenPassword forgottenPassword = new ForgottenPassword( null, this);
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

    private User validate() {
        return User.searchDB(User_F.getText(), Password_F.getText());
    }

    private void neglected() {
        User_F.clear();
        Password_F.clear();
    }

    public void open() {
        User inputUser = validate();
        if(inputUser != null) {
            try {
                new SesionAtCl(inputUser);
                System.out.println("josd");
                dispose();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            neglected();
            System.out.println("josdefa");
        }
    }
}
