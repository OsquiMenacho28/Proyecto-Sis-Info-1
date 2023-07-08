package application.Interface.LI;

import application.FlowController.Sesion;
import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import application.FlowController.SesionAtCl;
import application.FlowController.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class LogIn extends PromptWindow implements Initializable {
    @FXML
    Button LogIn_B;
    @FXML
    Button Back_B;
    @FXML
    TextField User_F;
    @FXML
    PasswordField Password_F;

    @FXML
    Button FPassword_B;

    public LogIn(Sesion ses, Stage stage, String FXMLname, PromptWindow origin, String title) throws IOException {
        super(ses, FXMLname, origin, title);
        this.load();
        this.show();
    }

    public LogIn(Sesion ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, FXMLname, origin);
        this.load();
        this.show();
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
                openWindow(inputUser);
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

    public abstract void openWindow(User user) throws Exception;
}
