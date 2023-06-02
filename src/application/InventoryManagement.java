package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagement extends PromptWindow implements Initializable {

    @FXML
    private ScrollPane ListScroll;

    @FXML
    private Button Options_B;

    @FXML
    private Button Back_B;

    public InventoryManagement(SesionAdmin ses, PromptWindow origin) throws IOException {
        super(ses, "InventoryManagement.fxml", origin);
        super.stage.setMaximized(true);
        super.stage.setTitle("GESTIÓN DE INVENTARIO");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Options_B.setOnAction(e -> {try {
            POSClosure();
        } catch (IOException e1) {
            e1.printStackTrace();
        }});*/

        Back_B.setOnAction(e -> back());

        ListScroll.setFitToWidth(true);
        //ListScroll.setFitToHeight(true);
    }
}
