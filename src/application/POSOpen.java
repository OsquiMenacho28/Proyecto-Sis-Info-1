package application;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class POSOpen extends PromptWindow implements Initializable {

	PaymentRequest paymentRequest;

	@FXML
	private Button Notification_B;

	@FXML
	private MenuItem LightMode_Opt;

	@FXML
	private MenuItem DarkMode_Opt;

	@FXML
	private Button Close_B;

	@FXML
	private Button Back_B;

	@FXML
	private VBox RightPane;

	@FXML
	private TextField SearchCode_F;

	@FXML
	private TextField SearchProduct_F;

	@FXML
	private ComboBox<String> BrandFilter_C;

	@FXML
	private ComboBox<String> CategoryFilter_C;

	@FXML
	private MenuButton Mode_CB;

	@FXML
	private MenuButton Menu_B;

	@FXML
	private MenuItem Sales_Opt;

	@FXML
	private MenuItem Inventory_Opt;

	@FXML
	private MenuItem Closure_Opt;

	@FXML
	private ImageView RightMenuButton;

	@FXML
	private ScrollPane ListScroll;

	@FXML
	private FlowPane ProductList;

	@FXML
	private AnchorPane LeftPane;

	@FXML
	private TableView<AddedProduct> CartList_T;

	@FXML
	TableColumn<AddedProduct,String> ItemColumn;

	@FXML
	TableColumn<AddedProduct,Float> PriceColumn;

	@FXML
	TableColumn<AddedProduct,Integer> CantColumn;

	@FXML
	TableColumn<AddedProduct,Float> PartialPriceColumn;

	@FXML
	private Label Total_L;

	@FXML
	private Button Pay_B;

	@FXML
	private Button Clear_B;

	private float OpeningCount = (float)0.0;
	public ObservableList<Product> products = FXCollections.observableArrayList();
	
	public ObservableList<AddedProduct> cart = FXCollections.observableArrayList();
	private ObservableList<String> CategoryList = FXCollections.observableArrayList();

	private ObservableList<String> BrandList = FXCollections.observableArrayList();
	private AddedProduct focusedItem;
	private ObservableList<AddedProduct> selectedItems;

	private BoxBlur blurEffect = new BoxBlur(10, 10, 3);
	
	public POSOpen(float OpeningCount, ObservableList<Product> products,
				   SesionAtCl ses, PromptWindow origin) throws IOException {
		super(ses, "POSOpen.fxml", origin);
		super.stage.setResizable(true);
		super.stage.setMaximized(true);
		super.stage.setTitle("PUNTO DE VENTA");
		this.OpeningCount = OpeningCount;
		this.products = products;
		this.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		stage.setOnCloseRequest(windowEvent -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("CERRAR APLICACIÓN");
			alert.setHeaderText("¡ADVERTENCIA!");
			alert.setContentText("Se cerrará la aplicación. ¿Desea continuar?");
			alert.initStyle(StageStyle.DECORATED);
			stage.getScene().getRoot().setEffect(blurEffect);
			Optional <ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Platform.exit();
			}
			else {
				stage.getScene().getRoot().setEffect(null);
				windowEvent.consume();
			}
		});

		Clear_B.setOnAction(actionEvent -> cart.clear());

		Notification_B.setOnAction(actionEvent -> {
			stage.getScene().getRoot().setEffect(blurEffect);
			try {
				Notifications notifications = new Notifications((SesionAtCl) null,this);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		LightMode_Opt.setOnAction(actionEvent -> {

		});

		DarkMode_Opt.setOnAction(actionEvent -> {

		});

		Close_B.setOnAction(actionEvent -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("CERRAR SESIÓN");
			alert.setHeaderText("¡AVISO!");
			alert.setContentText("¿Está seguro de Cerrar Sesión?");
			alert.initStyle(StageStyle.DECORATED);
			stage.getScene().getRoot().setEffect(blurEffect);
			Optional <ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				dispose();
				try {
					new SelectAccount();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			else {
				stage.getScene().getRoot().setEffect(null);
			}
		});

		Sales_Opt.setOnAction(actionEvent -> {
			stage.getScene().getRoot().setEffect(blurEffect);
			try {
				Sales sales = new Sales((SesionAtCl) null, this);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		Inventory_Opt.setOnAction(actionEvent -> {
			stage.getScene().getRoot().setEffect(blurEffect);
			try {
				Inventory inventory = new Inventory((SesionAtCl) null, this);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		Closure_Opt.setOnAction(actionEvent -> {
			stage.getScene().getRoot().setEffect(blurEffect);
			try {
				POSClosure posClosure = new POSClosure(OpeningCount, 0,null,this);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		
		Back_B.setOnAction(e -> back());

		Pay_B.setOnAction(actionEvent -> {
			if (cart.isEmpty()) {
				Alert alertDialog = new Alert(Alert.AlertType.ERROR);
				alertDialog.setTitle("ERROR!");
				alertDialog.setHeaderText("¡El Carrito de Compras se encuentra vacio!");
				alertDialog.setContentText("Por favor, añada productos al Carrito de Compras");
				alertDialog.initStyle(StageStyle.DECORATED);
				java.awt.Toolkit.getDefaultToolkit().beep();
				stage.getScene().getRoot().setEffect(blurEffect);
				alertDialog.showAndWait();
				stage.getScene().getRoot().setEffect(null);
			}
			else {
				stage.getScene().getRoot().setEffect(blurEffect);
				try {
					PaymentRequest paymentRequest = new PaymentRequest(cart,null, this);
					paymentRequest.stage.setTitle("CONFIRMAR PAGO");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		cart.addListener(new ListChangeListener<AddedProduct>() {
			@Override
			public void onChanged(Change<? extends AddedProduct> c) {
					if(cart.isEmpty()) {
						Total_L.setText("");
					}
					else {
						setTotalLabel();
					}
			}
		});
		
		
		CartList_T.prefWidthProperty().bind(LeftPane.widthProperty());

		
		ListScroll.setFitToWidth(true);
		//ListScroll.setFitToHeight(true);

		
		SearchProduct_F.textProperty().addListener(e -> {
			try {
				refreshProducts();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		CategoryFilter_C.setItems(CategoryList);
		BrandFilter_C.setItems(BrandList);

		ItemColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, String>("name"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("price"));
		CantColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Integer>("cant"));
		PartialPriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("tprice"));
		CantColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		
		
		CantColumn.setOnEditCommit((e) -> {
			AddedProduct x = e.getRowValue();
			x.setCant(e.getNewValue());
			CartList_T.refresh();
		});
		
		selectedItems = CartList_T.getSelectionModel().getSelectedItems();
		selectedItems.addListener(new ListChangeListener<AddedProduct>() {
			@Override
			public void onChanged(Change<? extends AddedProduct> c) {
				if(!selectedItems.isEmpty()) {
					focusedItem = selectedItems.get(0);
				}
				else {
					focusedItem = null;
				}
			}
		});
		
		CartList_T.setOnKeyReleased(e -> {
			KeyCode pKey = e.getCode();
			
			System.out.println(e.getCode());
			
			if((pKey == KeyCode.C)) {
				cart.clear();
			}
			else if(focusedItem != null) {
				
				if((pKey == KeyCode.DELETE)) {
					cart.remove(focusedItem);
				}
				if(pKey == KeyCode.EQUALS) {
					focusedItem.Add();
				}
				if(pKey == KeyCode.MINUS) {
					focusedItem.Sub();
				}
				if(pKey == KeyCode.BACK_SPACE) {
					focusedItem.setCant(focusedItem.getCant() / 10);
				}
				if(pKey.isDigitKey()) {
					focusedItem.setCant(focusedItem.getCant() * 10 + Integer.parseInt(pKey.getChar()) );
				}
			}
		});
		CartList_T.setItems(cart);
		
		FillList();
		
		refreshProductList(this.products);	

	}

	private Product getProductWithId(int code) {
		int index;
		for(index = 0; index < products.size(); index++) {
			if (products.get(index).getCode() == code) {
				return products.get(index);
			}
		}
		return null;
	}
	
	private int getIndexOfAP(ObservableList<AddedProduct> list, int code) {
		int i;
		for(i = 0; i < list.size(); i++) {
			if (list.get(i).getCode() == code) {
				return i;
			}
		}
		return -1;
	}
	
	private void setTotalLabel() {
		Total_L.setText(String.valueOf(TotalPrice()));
	}
	
	public float TotalPrice() {
		int x = 0;
		for(AddedProduct e : cart) {
			x += e.getTprice();
		}
		return x;
	}
	
	public void refreshProducts() throws SQLException {
		
		String filter = CategoryFilter_C.getValue();
		if(filter.equals("") || filter == null) {
			refreshProductList(this.products);
		}
		else {
			ObservableList<Product> newList = FXCollections.observableArrayList();
			for(Product e : this.products) {
				if(e.category .equals(filter)) {
					newList.add(e);
				}
			}
			refreshProductList(newList);
		}
	}

	public void getCategoryList() throws SQLException {
		CategoryList.clear();
		for(Product p : this.products) {
			String cat = p.getCategory();;
			if(!CategoryList.contains(cat)) {
				CategoryList.add(cat);
			}
			
		}
	}
	
	
	public void refreshProductList(ObservableList<Product> products) {
		ProductList.getChildren().clear();
		try {
			for (Product e : products) {
				
				ItemIcon ItemIcon = new ItemIcon(e);
				
				ItemIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
					
					@Override
					public void handle(MouseEvent event) {
						if(event.getButton() == MouseButton.PRIMARY) {
							
							int id = ItemIcon.getItemCode();
							
							int pos = getIndexOfAP(cart, id);
							
							if(pos >= 0) {
								cart.get(pos).Add();
							}
							else {
								AddedProduct aux = new AddedProduct(getProductWithId(id), 1); 	
								
								aux.addCantListener(new ChangeListener<Number>(){
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
										if(newValue.intValue() <= 1) 
										{
											SimpleIntegerProperty x= (SimpleIntegerProperty)observable;
											cart.remove(x.getBean());
										};
										CartList_T.refresh();
										setTotalLabel();
									}
								});
								
								cart.add(aux);
							}
					}}
				});

				ItemIcon.deleteBtn.setOnAction(f -> {
					deleteItem(ItemIcon.code);
				});
				ProductList.getChildren().add(ItemIcon);
	    	}
		}
		catch(IOException e) {
			e.printStackTrace();
		}	
	}

	private void deleteItem(int code){
		products.remove(code);
		ProductList.getChildren().remove(code);
	}
	
	private void paymentRequest() throws IOException {
		ses2.cashPayment(FXCollections.observableArrayList(cart));
	}
	
	
	/*private void AddProd() throws IOException{
		ses2.addProd(this);
	}*/
	
	private void POSClosure() throws IOException {
		ses2.POSClosure(OpeningCount, this);
		dispose();
	}
	
	public void refreshProducts(ObservableList<Product> x) {
		this.products = x;
		refreshProductList(this.products);
	}
	
	private void FillList() {
		for(int i = 0; i < 20; i++) {
			Product aux = new Product(i, (int)Math.floor(Math.random()*(30-0+1)+0), "Producto " + i, "", "", "Marca " + i, "", (float)Math.floor(Math.random()*(1000-100+1)+100));
			products.add(aux);
		}
	}
	
	public void Ncart() {
		cart.clear();
	}
}
