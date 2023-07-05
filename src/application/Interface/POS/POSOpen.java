package application.Interface.POS;

import InventoryModel.Inventory;
import InventoryModel.Product;
import InventoryModel.Product.AddedProduct;
import SalesModel.Cart;
import SalesModel.POSsesion;
import application.FlowController.SesionAtCl;
import application.Interface.AtClPromptWindow;
import application.Interface.PromptWindow;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
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
	private ObservableList<Node> visibleProducts;
	public POSOpen(SesionAtCl ses, PromptWindow origin, Inventory inventory) throws IOException {
		super(ses, "POSOpen.fxml", origin, "PUNTO DE VENTA");
		this.inventory = inventory;
		this.cart = null;
		this.POSsesion = null;
		this.load();
	}

	private class NameComparator implements Comparator<ItemIcon> {
		String target;
		private NameComparator(String target){
			this.target = target;
		}
		@Override
		public int compare(ItemIcon i1, ItemIcon i2) {
			Product p1 = i1.getProduct();
			Product p2 = i2.getProduct();

			String name1 = p1.getName();
			String name2 = p2.getName();

			return (int)(1000*(NameDistance(name1, this.target) - NameDistance(name2, this.target)));
		}
		private double NameDistance(String shot, String target){
			LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
			JaccardDistance jaccardDistance = new JaccardDistance();
			Integer maxLen = Math.max(target.length(), shot.length());
			return 0.7*(levenshteinDistance.apply(target, shot)/maxLen) + 0.3*jaccardDistance.apply(target, shot);
		}
	}


	private class CodeComparator implements Comparator<ItemIcon> {
		String target;
		private CodeComparator(String target){
			this.target = target;
		}
		@Override
		public int compare(ItemIcon i1, ItemIcon i2) {
			Product p1 = i1.getProduct();
			Product p2 = i2.getProduct();

			String name1 = String.valueOf(p1.getCode());
			String name2 = String.valueOf(p2.getCode());

			return (int)(1000*(CodeDistance(name1, this.target) - CodeDistance(name2, this.target)));
		}
		private double CodeDistance(String shot, String target){
			LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
			JaccardDistance jaccardDistance = new JaccardDistance();
			Integer maxLen = Math.max(target.length(), shot.length());
			return 0.3*(levenshteinDistance.apply(target, shot)/maxLen) + 0.7*(target.equals(shot) ? 1 : 0);
		}
	}

	public SesionAtCl getSesion(){
		return sesion;
	}
	public void activate(POSsesion POSsesion){
		if(POSsesion != null){
			this.POSsesion = POSsesion;
			this.cart = POSsesion.newCart();
		}
	}

	public void deactivate(){
		this.POSsesion = null;
		this.cart = null;
	}
	public boolean isActive(){
		if(this.POSsesion != null){
			return true;
		}
		return false;
	}
	public POSsesion getPOSsesion(){
		return POSsesion;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		stage.setOnCloseRequest(windowEvent -> {
			try {
				sesion.closeApplicationRequest(windowEvent);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		//Notification_B.setOnAction(actionEvent -> notificationRequest());

		LightMode_Opt.setOnAction(actionEvent -> {

		});

		DarkMode_Opt.setOnAction(actionEvent -> {

		});

		//Close_B.setOnAction(actionEvent -> endSesionRequest(););

		//Sales_Opt.setOnAction(actionEvent -> salesRequest(););

		//Inventory_Opt.setOnAction(actionEvent -> inventoryRequest(););

		//Closure_Opt.setOnAction(actionEvent -> closureRequest()););
		
		//Back_B.setOnAction(e -> back());

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
					try {
						cart.remove(focusedItem);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
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
			if(!SearchCode_F.getText().equals("")){SearchCode_F.clear();}
			nameSearch();
		});
		SearchCode_F.textProperty().addListener(e -> {
			if(!SearchProduct_F.getText().equals("")){SearchProduct_F.clear();}
			codeSearch();
		});

		//CategoryFilter_C.setItems(CategoryList);
		//BrandFilter_C.setItems(BrandList);


		this.visibleProducts = ProductList.getChildren();

		//Extract the visible Items from inventory
		for(ItemIcon icon : inventory.getIcons()){
			if(icon.getProduct().isVisible()){
				icon.setOnMouseClicked(e -> {
					try {
						icon.getProduct().addToCart(1, cart);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				});
				visibleProducts.add(icon);
			}
		}

		//Update visible products in case the inventory is changed
		inventory.getIcons().addListener((ListChangeListener<? super ItemIcon>) c -> {
			while (c.next()) {
				if (c.wasRemoved()) {
					for (ItemIcon icon : c.getRemoved()) {
						visibleProducts.remove(icon);
					}
				} else if (c.wasAdded()) {
					for (ItemIcon icon : c.getAddedSubList()) {
						if (icon.getProduct().isVisible()) {
							icon.setOnMouseClicked(e -> {
								try {
									icon.getProduct().addToCart(1, cart);
								} catch (Exception ex) {
									throw new RuntimeException(ex);
								}
							});
							visibleProducts.add(icon);
						}
					}
				}
			}
		});

	}

	public void setTotalLabel() {
		Total_L.setText(String.valueOf(cart.getTotalPrice()));
	}

	private void nameSearch(){
		String productQuery = SearchProduct_F.getText();
		visibleProducts.sort((Comparator) new NameComparator(productQuery));
	}

	private void codeSearch(){
		String codeQuery = SearchCode_F.getText();
		visibleProducts.sort((Comparator) new CodeComparator(codeQuery));
	}

	private void paymentRequest() throws IOException {
		sesion.paymentRequest(cart);
	}

	private void quitRequest(){
		//sesion.quitRequest();
	}

	private void POSClosure() throws IOException {
		//sesion.closureRequest(OpeningCount, this);
	}

	public TableView<AddedProduct> getCartTable(){
		return this.CartList_T;
	};
	public void clearCart() {
		cart.clear();
	}
}
