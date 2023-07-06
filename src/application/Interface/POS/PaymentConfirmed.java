package application.Interface.POS;

import application.FlowController.SesionAtCl;
import application.Interface.PromptWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentConfirmed extends PromptWindow implements Initializable {

    @FXML
    private Button CheckInventory_B;

    @FXML
    private Button Continue_B;
    private SesionAtCl sesion;

    public PaymentConfirmed(SesionAtCl ses2, PromptWindow origin) throws IOException {
        super(ses2, "PaymentConfirmed.fxml", origin);
        stage.setTitle("PAGO CONFIRMADO");
        stage.setWidth(834);
        stage.setHeight(468);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stage.setOnCloseRequest(windowEvent -> {
            //origin.ses2.POSOpen.cart.clear();
            //origin.stage.getScene().getRoot().setEffect(null);
        });

        CheckInventory_B.setOnAction(actionEvent -> {
            try {
                sesion.inventoryRequest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Continue_B.setOnAction(actionEvent -> {
            dispose();
            sesion.onGenerateInvoiceRequest();
        });
    }
}
