package application.Interface.LI;

import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectAccount extends PromptWindow implements Initializable {

    @FXML
    Button Admin_B;
    @FXML
    Button AtCl_B;

    public SelectAccount() throws IOException {
        super((SesionAdmin) null,"SelectAccount.fxml", null);
        stage.setTitle("FERRETERÍA DIMACO");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Admin_B.setOnAction((e) -> {
            try {
                LogInAdmin logInAdmin = new LogInAdmin(null, this);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        AtCl_B.setOnAction((e) -> {
            try {
                LogInAtCl logInAtCl = new LogInAtCl(null, this);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
