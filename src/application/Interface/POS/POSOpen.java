package application.Interface.POS;

import SalesModel.Cart;
import SalesModel.POSsesion;
import application.Interface.AtClPromptWindow;
import application.Interface.generic.Inventory;
import application.Interface.generic.Notifications;
import application.Interface.generic.Sales;

import InventoryModel.Product;
import InventoryModel.Product.AddedProduct;
import application.Interface.PromptWindow;
import application.FlowController.SesionAtCl;
import application.Interface.LI.SelectAccount;
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

public class POSOpen extends AtClPromptWindow implements Initializable {

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

	private Inventory inventory;
	private Cart cart;
	private POSsesion POSsesion;

	private AddedProduct focusedItem;
	private ObservableList<AddedProduct> selectedItems;
	public POSOpen(SesionAtCl ses, PromptWindow origin, Inventory inventory) throws IOException {
		super(ses, "POSOpen.fxml", origin, "PUNTO DE VENTA");
		this.inventory = inventory;
		this.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		stage.setOnCloseRequest(windowEvent -> {
			try {
				exitRequest();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		Notification_B.setOnAction(actionEvent -> notificationRequest());

		LightMode_Opt.setOnAction(actionEvent -> {

		});

		DarkMode_Opt.setOnAction(actionEvent -> {

		});

		Close_B.setOnAction(actionEvent -> endSesionRequest(););

		Sales_Opt.setOnAction(actionEvent -> salesRequest(););

		Inventory_Opt.setOnAction(actionEvent -> inventoryRequest(););

		Closure_Opt.setOnAction(actionEvent -> closureRequest()););
		
		Back_B.setOnAction(e -> back());

		Pay_B.setOnAction(actionEvent -> {
			try {
				paymentRequest();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		Clear_B.setOnAction(actionEvent -> cart.clear());

		CartList_T.prefWidthProperty().bind(LeftPane.widthProperty());
		cart.addListener((ListChangeListener<? super AddedProduct>) e -> setTotalLabel());

		ItemColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, String>("name"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("price"));
		CantColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Integer>("cant"));
		PartialPriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Float>("tprice"));
		CantColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		
		CantColumn.setOnEditCommit((e) -> {
			AddedProduct product = e.getRowValue();
			try {
				product.setCant(e.getNewValue());
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
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
					try {
						focusedItem.incrementQuantity();
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
				if(pKey == KeyCode.MINUS) {
					try {
						focusedItem.reduceQuantity();
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
				if(pKey == KeyCode.BACK_SPACE) {
					try {
						focusedItem.setCant(focusedItem.getCant() / 10);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
				if(pKey.isDigitKey()) {
					try {
						focusedItem.setCant(focusedItem.getCant() * 10 + Integer.parseInt(pKey.getChar()) );
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		});
		CartList_T.setItems(cart);

		ListScroll.setFitToWidth(true);
		SearchProduct_F.textProperty().addListener(e -> {
			try {
				refreshProducts();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		//CategoryFilter_C.setItems(CategoryList);
		//BrandFilter_C.setItems(BrandList);
		
		FillList();
		try {
			refreshProducts();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void setTotalLabel() {
		Total_L.setText(String.valueOf(cart.getTotalPrice()));
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
		sesion.paymentRequest(cart);
	}

	private void quitRequest(){
		sesion.quitRequest();
	}

	private void POSClosure() throws IOException {
		ses.closureRequest(OpeningCount, this);
	}

	private void FillList() {
		for(int i = 0; i < 20; i++) {
			Product aux = new Product(i, (int)Math.floor(Math.random()*(30-0+1)+0), "Producto " + i, "", "", "Marca " + i, "", (float)Math.floor(Math.random()*(1000-100+1)+100));
			products.add(aux);
		}
	}

	public void exitRequest() throws IOException {
		sesion.exitRequest();
	}


	public void clearCart() {
		cart.clear();
	}
}
