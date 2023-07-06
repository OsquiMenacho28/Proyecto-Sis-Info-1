package application.Interface.IM;

import DataBaseManager.Value;
import InventoryModel.Product;
import application.Interface.AdminPromptWindow;
import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EntryProduct extends AdminPromptWindow implements Initializable {

    @FXML
    private Label ProductCodeLabel;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private TextField ProductQuantity;

    @FXML
    private DatePicker ArriveDate;

    @FXML
    private TextField Description;

    @FXML
    private Button Entry_B;

    @FXML
    private Button Remove_B;

    @FXML
    private Button Back_B;

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    private Product product;

    public EntryProduct(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "EntryProduct.fxml", origin);
        stage.setTitle("REGISTRO O RETIRO DE MATERIAL");
        stage.setWidth(742);
        stage.setHeight(493);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProductQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    ProductQuantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        stage.setOnCloseRequest(windowEvent -> origin.setEffect(null));

        Back_B.setOnAction(actionEvent -> back());

        Entry_B.setOnAction(actionEvent -> {

            if(v(ProductQuantity.getText()) && v(Description.getText()) && ArriveDate.getValue() != null
            && this.product != null){

                try {
                    sesion.materialIntake(this.product, Integer.parseInt(ProductQuantity.getText()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                hide();
                try {
                    EntryConfirmed entryConfirmed = new EntryConfirmed(sesion, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Remove_B.setOnAction(actionEvent -> {
            if(v(ProductQuantity.getText()) && v(Description.getText()) && ArriveDate.getValue() != null
                    && this.product != null) {

                try {
                    sesion.materialWithdrawal(this.product, Integer.parseInt(ProductQuantity.getText()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                hide();
                try {
                    RemoveConfirmed removeConfirmed = new RemoveConfirmed(sesion, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void clear(){
        this.product = null;
    }

    private Boolean v(String x) {
        return (x != null) && !x.equals("");
    }

}
