package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.FloatStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SalesHist extends PromptWindow implements Initializable{
	
	@FXML
	Button BackButton;
	
	@FXML
	TextField NameField;
	
	@FXML
	TextField NITField;
	
	@FXML
	DatePicker DateField;
	
	@FXML
	TextField PriceField;
	
	@FXML
	TableView<Sale> Table;
	
	@FXML
	TableColumn<Sale, String> NameColumn;
	
	@FXML
	TableColumn<Sale, Float> PriceColumn;
	
	ObservableList<Sale> sales = FXCollections.observableArrayList();
	
	public SalesHist(ObservableList<Sale> sales, SesionAtCl ses, PromptWindow origin) throws IOException {
		super(ses, "SalesHist.fxml", origin);
		this.sales = sales;
		load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		System.out.print(sales);
		
		NameColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("name"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<Sale, Float>("monto"));
		Table.setItems(sales);
		
		Table.setEditable(false);
		DateField.setEditable(true);
		PriceField.setTextFormatter(new TextFormatter<>(new FloatStringConverter()));

		
		BackButton.setOnAction(e ->{
			dispose();
		});
		
		NameField.textProperty().addListener(e -> {refresh();});
		
		NITField.textProperty().addListener(e -> {refresh();});
		NITField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            PriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		DateField.getEditor().setDisable(true);
		DateField.setOnAction(e -> {refresh();});
		
		
		PriceField.textProperty().addListener(e -> {refresh();});
		PriceField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            PriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
	}
	
	private void refresh() {
		
	}

}
