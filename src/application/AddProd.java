package application;

import java.io.IOException; 
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class AddProd extends PromptWindow implements Initializable{
	
	@FXML
	Button BackButton;
	
	@FXML
	Button MainButton;
	
	@FXML
	TextField ProductField;
	
	@FXML
	TextField BrandField;
	
	@FXML
	TextField PriceField;
	
	@FXML
	TextField ColorField;
	
	@FXML
	TextField CategoryField;
	
	@FXML
	TextField StockField;
	
	@FXML
	TextField SerialNumberField;
	
	@FXML
	TableView<Product> Table;
	
	@FXML
	TableColumn<Product, String> ProductColumn;
	
	@FXML
	TableColumn<Product, Integer> SerialNumberColumn;
	
	ObservableList<Product> products = FXCollections.observableArrayList();
	
	public AddProd(ObservableList<Product> products, Sesion ses, POSOpen origin) throws IOException {	
		super(ses, "AddProd.fxml", origin);
		this.products = products;
		load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Table.setEditable(false);
		
		PriceField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		SerialNumberField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		StockField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		
		BackButton.setOnAction(e ->{
			dispose();
		});
		
		ProductField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		
		BrandField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		
		PriceField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		
		ColorField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		
		CategoryField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		
		SerialNumberField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		
		SerialNumberField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            PriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		StockField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            PriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
	
		PriceField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            PriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		ProductColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		SerialNumberColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
		Table.setItems(products);
		
		MainButton.setOnAction(e -> {try {
			add();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
	}
	
	private void refresh() throws SQLException {
		
	}
	
	private Boolean v(String x) {
		return (x != null) && !x.equals(""); 
	}
	
	private void add() throws SQLException {
		
		String product = ProductField.getText();
		String brand = BrandField.getText();
		String price = PriceField.getText();
		String color = ColorField.getText();
		String cat = CategoryField.getText();
		String stock = StockField.getText();
		String id = SerialNumberField.getText();
		
		
		if(v(product) && v(brand) && v(price) && v(color) && v(cat) && v(stock) && v(id)) {
			Product aux = new Product(Integer.parseInt(id), product, Float.parseFloat(price), Integer.parseInt(stock), brand, color, cat);
			ses.addProduct(aux);
		}
		
	}
	
}
