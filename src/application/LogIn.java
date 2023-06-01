package application;

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

    final User userManager = new User(null, null, null);
    public LogIn(SesionAtCl ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, FXMLname, origin);
        this.load();
        this.show();
    }

    public LogIn(SesionAdmin ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, FXMLname, origin);
        this.load();
        this.show();
    }
}
