package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

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
    private TableView<AddedProduct> CartList_T;

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

    Sale sale;
    ObservableList<AddedProduct> cart = FXCollections.observableArrayList();

    String name;
    int NIT;
    
	public PaymentRequest(ObservableList<AddedProduct> cart, SesionAtCl ses, POSOpen origin) throws IOException {
		super(ses, "PaymentRequest.fxml", origin);
		this.cart = cart;
        stage.setTitle("CONFIRMAR PAGO");
        load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        stage.setOnCloseRequest(windowEvent -> origin.stage.getScene().getRoot().setEffect(null));
		
		Back_B.setOnAction(e -> back());
		Confirm_B.setOnAction(e -> {
            dispose();
            try {
                PaymentConfirmed paymentConfirmed = new PaymentConfirmed(null, this.origin);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Name_F.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DOWN) {
                NIT_F.requestFocus();
            }
        });
        NIT_F.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                Name_F.requestFocus();
            }
        });

		CartList_T.setEditable(false);
		
		ProductColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, String>("name"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("price"));
		CantColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Integer>("cant"));
		TPriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("tprice"));
		CantColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	
		CartList_T.setItems(this.cart);
	}
}