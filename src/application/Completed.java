package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Completed extends PromptWindow implements Initializable {

	@FXML
	private TableView<AddedProduct> CartList;
	
    @FXML
    private Label NITLabel;

    @FXML
    private Button Confirm_B;

    @FXML
    private Button Back_B;

    @FXML
    private Label NameLabel;
    
    @FXML
    private Label TotalLabel;

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
    
	public Completed(/*Sale sale,*/ SesionAtCl ses, POSOpen origin) throws IOException {
		super(ses, "Completed.fxml", origin);
		/*this.sale = sale;
		this.name = sale.getName();
		this.NIT = sale.getNIT();
		this.cart = sale.getCart();*/
        stage.setTitle("CONFIRMAR PAGO");
        load();
        stage.centerOnScreen();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Back_B.setOnAction(e -> back());
		Confirm_B.setOnAction(e -> dispose());
		
		NameLabel.setText("Nombre Cliente : " + name);
		NITLabel.setText("NIT : " + NIT);
		TotalLabel.setText("Total : " + sale.getMonto());
		
		CartList.setEditable(false);
		
		ProductColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, String>("name"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("price"));
		CantColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Integer>("cant"));
		TPriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("tprice"));
		CantColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	
		CartList.setItems(cart);
	}
}