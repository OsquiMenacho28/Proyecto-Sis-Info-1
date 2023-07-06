package application.Interface.POS;

import InventoryModel.Product;
import InventoryModel.Product.AddedProduct;
import SalesModel.Cart;
import application.FlowController.SesionAtCl;
import application.Interface.PromptWindow;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentRequest extends PromptWindow implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button Back_B;

    @FXML
    private Button Confirm_B;

    @FXML
    private TableView<Product.AddedProduct> CartList_T;

    @FXML
    private TextField Name_F;

    @FXML
    private TextField NIT_F;

    @FXML
    private TableColumn<AddedProduct, String> ProductColumn;

    @FXML
    private TableColumn<AddedProduct, Integer> CantColumn;

    @FXML
    private TableColumn<AddedProduct, Float> PriceColumn;

    @FXML
    private TableColumn<AddedProduct, Float> TPriceColumn;

    private Cart cart;
    private SesionAtCl sesion;
    
	public PaymentRequest(SesionAtCl ses, POSOpen origin) throws IOException {
		super(ses, "PaymentRequest.fxml", origin, "CONFIRMAR PAGO");
        this.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Back_B.setOnAction(e -> back());
		Confirm_B.setOnAction(e -> {
            dispose();
            try {
                sesion.paymentConfirmedRequest();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

		CartList_T.setEditable(false);

        ProductColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AddedProduct, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AddedProduct, String> addedProductStringCellDataFeatures) {
                return new SimpleStringProperty(addedProductStringCellDataFeatures.getValue().getProduct().getName());
            }
        });
        PriceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AddedProduct, Float>, ObservableValue<Float>>(){
            @Override
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<AddedProduct, Float> addedProductStringCellDataFeatures) {
                return new ReadOnlyObjectWrapper(addedProductStringCellDataFeatures.getValue().getProduct().getPrice());
            }
        });
        CantColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AddedProduct, Integer>, ObservableValue<Integer>>(){
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<AddedProduct, Integer> addedProductStringCellDataFeatures) {
                return new ReadOnlyObjectWrapper(addedProductStringCellDataFeatures.getValue().getCant());
            }
        });
        TPriceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AddedProduct, Float>, ObservableValue<Float>>(){
            @Override
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<AddedProduct, Float> addedProductStringCellDataFeatures) {
                return new ReadOnlyObjectWrapper(addedProductStringCellDataFeatures.getValue().getPrice());
            }
        });
	}

    public void setCart(Cart cart) {
        this.cart = cart;
        CartList_T.setItems(this.cart);
    }
}