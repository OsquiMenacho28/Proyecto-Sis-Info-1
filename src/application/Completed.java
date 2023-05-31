package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

public class Completed extends PromptWindow implements Initializable{

	@FXML
	private TableView<AddedProduct> CartList;
	
    @FXML
    private Label NITLabel;

    @FXML
    private Button CloseButton;

    @FXML
    private Button BackButton;

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
    
	public Completed(Sale sale,
			Sesion ses,  CashPayment origin) throws IOException {
		super(ses, "Completed.fxml", origin);
		
		this.sale = sale;
		this.name = sale.getName();
		this.NIT = sale.getNIT();
		this.cart = sale.getCart();
		load();
		show();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		BackButton.setOnAction(e -> {back();});
		CloseButton.setOnAction(e -> {
			dispose();});
		
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