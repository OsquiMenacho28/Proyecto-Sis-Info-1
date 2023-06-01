package application;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagement extends PromptWindow implements Initializable {

    public InventoryManagement(SesionAdmin ses, PromptWindow origin) throws IOException {
        super(ses, "InventoryManagement.fxml", origin);
        super.stage.setMaximized(true);
        super.stage.setTitle("GESTIÓN DE INVENTARIO");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
