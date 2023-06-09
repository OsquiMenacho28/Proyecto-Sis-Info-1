package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Inventory extends PromptWindow implements Initializable {

    @FXML
    private Button OK_B;

    public Inventory(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "Inventory.fxml", origin);
        stage.setTitle("FERRETERÍA DIMACO - INVENTARIO");
        this.load();
    }

    public Inventory(SesionAtCl ses2, PromptWindow origin) throws IOException {
        super(ses2, "Inventory.fxml", origin);
        stage.setTitle("FERRETERÍA DIMACO - INVENTARIO");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OK_B.setOnAction(actionEvent -> back());
    }
}
