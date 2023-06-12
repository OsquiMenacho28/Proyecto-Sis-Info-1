package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Sales extends PromptWindow implements Initializable {

    @FXML
    Button OK_B;

    public Sales(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "Sales.fxml", origin);
        stage.setTitle("FERRETERÍA DIMACO - VENTAS");
        this.load();
    }

    public Sales(SesionAtCl ses2, PromptWindow origin) throws IOException {
        super(ses2, "Sales.fxml", origin);
        stage.setTitle("FERRETERÍA DIMACO - VENTAS");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OK_B.setOnAction(actionEvent -> {
            back();
        });

        stage.setOnCloseRequest(windowEvent -> origin.stage.getScene().getRoot().setEffect(null));
    }
}
