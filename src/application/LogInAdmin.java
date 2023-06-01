package application;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInAdmin extends LogIn {

    public  LogInAdmin(Stage stage, PromptWindow origin) throws IOException {
        super((SesionAdmin) null, stage, "LogInAdmin.fxml", origin);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LogIn_B.setOnAction((e) -> {
            open();
            origin.dispose();
        });

        Back_B.setOnAction((e) -> backDispose());

        this.setOnKeyReleased((e) -> {
            KeyCode pKey = e.getCode();
            if (pKey == KeyCode.ENTER) {
                open();
                origin.dispose();
            }
        });
    }


    private User validate() {
        return userManager.searchDB(User_F.getText(), Password_F.getText());
    }

    private void neglected() {
        User_F.clear();
        Password_F.clear();
    }

    public void open() {
        User inputUser = validate();
        if(inputUser != null) {
            try {
                new SesionAdmin(inputUser, this);
                dispose();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            neglected();
        }
    }
}
