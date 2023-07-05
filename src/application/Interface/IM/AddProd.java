package application.Interface.IM;

import InventoryModel.Product;
import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProd extends PromptWindow implements Initializable{
	
	@FXML
	Button Back_B;
	
	@FXML
	Button Create_B;

	@FXML
	TextField ProductCodeField;
	
	@FXML
	TextField ProductNameField;
	
	@FXML
	TextField ProductBrandField;
	
	@FXML
	TextField ProductPriceField;
	
	@FXML
	TextField ProductColorField;
	
	@FXML
	TextField ProductCategoryField;
	
	@FXML
	TextField ProductQuantityField;
	
	//@FXML
	//TextField SerialNumberField;

	@FXML
	TextArea ProductDescriptionArea;
	
	/*@FXML
	TableView<Product> Table;
	
	@FXML
	TableColumn<Product, String> ProductColumn;
	
	@FXML
	TableColumn<Product, Integer> SerialNumberColumn;*/
	
	ObservableList<Product> products = FXCollections.observableArrayList();

	private BoxBlur blurEffect = new BoxBlur(10, 10, 3);
	
	public AddProd(ObservableList<Product> products, SesionAdmin ses, InventoryManagement origin) throws IOException {
		super(ses, "AddProd.fxml", origin);
		stage.setTitle("AÑADIR PRODUCTO");
		stage.setWidth(644);
		stage.setHeight(606);
		this.products = products;
		this.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage.setOnCloseRequest(windowEvent -> origin.stage.getScene().getRoot().setEffect(null));

		ProductNameField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.DOWN) {
				ProductDescriptionArea.requestFocus();
			}
		});
		ProductDescriptionArea.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				ProductNameField.requestFocus();
			}
			if (event.getCode() == KeyCode.DOWN) {
				ProductCodeField.requestFocus();
			}
		});
		ProductCodeField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				ProductDescriptionArea.requestFocus();
			}
			if (event.getCode() == KeyCode.DOWN) {
				ProductColorField.requestFocus();
			}
		});
		ProductColorField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				ProductCodeField.requestFocus();
			}
			if (event.getCode() == KeyCode.DOWN) {
				ProductBrandField.requestFocus();
			}
		});
		ProductBrandField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				ProductColorField.requestFocus();
			}
			if (event.getCode() == KeyCode.DOWN) {
				ProductCategoryField.requestFocus();
			}
		});
		ProductCategoryField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				ProductBrandField.requestFocus();
			}
			if (event.getCode() == KeyCode.DOWN) {
				ProductPriceField.requestFocus();
			}
		});
		ProductPriceField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				ProductCategoryField.requestFocus();
			}
			if (event.getCode() == KeyCode.DOWN) {
				ProductQuantityField.requestFocus();
			}
		});
		ProductQuantityField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				ProductPriceField.requestFocus();
			}
		});

		//Table.setEditable(false);
		
		ProductPriceField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		//SerialNumberField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		ProductQuantityField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		
		Back_B.setOnAction(e -> back());
		
		ProductNameField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}});
		
		ProductBrandField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}});
		
		ProductPriceField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}});
		
		ProductColorField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}});
		
		ProductCategoryField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}});
		
		/*SerialNumberField.textProperty().addListener(e -> {try {
			refresh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}});
		
		SerialNumberField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            ProductPriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});*/
		
		ProductQuantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            ProductPriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
	
		ProductPriceField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            ProductPriceField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		/*ProductColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		SerialNumberColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
		Table.setItems(products);*/
		
		Create_B.setOnAction(e -> {
			try {
				add();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("AÑADIR PRODUCTO");
			alert.setHeaderText("¡EXITO!");
			alert.setContentText("El producto " + ProductNameField.getText() + " se registró correctamente");
			alert.initStyle(StageStyle.DECORATED);
			stage.getScene().getRoot().setEffect(blurEffect);
			alert.showAndWait();
			back();
		});
	}
	
	private void refresh() throws SQLException {
		
	}
	
	private Boolean v(String x) {
		return (x != null) && !x.equals(""); 
	}
	
	private void add() throws SQLException {
		
		String name = ProductNameField.getText();
		String description = ProductDescriptionArea.getText();
		String quantity = ProductQuantityField.getText();
		String code = ProductCodeField.getText();
		String color = ProductColorField.getText();
		String brand = ProductBrandField.getText();
		String category = ProductCategoryField.getText();
		String price = ProductPriceField.getText();

		if(v(name) && v(description) && v(code) && v(quantity) && v(color) && v(brand) && v(category) && v(price)) {
			Product aux = new Product(Integer.parseInt(code), Integer.parseInt(quantity), name, description, color, brand, category, Float.parseFloat(price));
			sesion.addProduct(aux);
		}
		
	}
	
}
