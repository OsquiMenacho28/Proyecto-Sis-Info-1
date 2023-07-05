package InventoryModel;
import DataBaseManager.*;
import SalesModel.Cart;
import application.Interface.POS.ItemIcon;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;

import java.sql.SQLException;
import java.util.ArrayList;

public class Product extends LinkedObject {


	protected Int_Value code;
	protected Int_Value quantity;
	protected String_Value name;
	protected String_Value description;
	protected String_Value color;
	protected String_Value brand;
	protected String_Value category;
	protected Float_Value price;
	protected Int_Value visible;
	
	private ArrayList<AddedProduct> pendingProducts;
	private Inventory inventory;
	private ItemIcon icon;
	public Product(Inventory inventory, int code, int quantity, String name, String description, String color, String brand, String category, float price) throws Exception {
		super(Inventory.productRV,
									Value.create(code),
									Value.create(name),
									Value.create(description),
									Value.create(color),
									Value.create(brand),
									Value.create(category),
									Value.create(price));

		this.pendingProducts = new ArrayList<AddedProduct>();
		this.inventory = inventory;
		this.icon = new ItemIcon(this);
		addListener( e -> {icon.updateContent();});
		defineBind();
		link();
	}

	public Product(Inventory inventory, RowMirror record) throws Exception {
		super(record);
		this.pendingProducts = new ArrayList<AddedProduct>();
		this.inventory = inventory;
		this.icon = new ItemIcon(this);
		this.visible = Value.create(1);
		addListener( e -> {icon.updateContent();});
		defineBind();
		link();
	}

	protected void defineBind(){
		bind("codigo", code);
		bind("cantidad", quantity);
		bind("nombre", name);
		bind("desrcipcion", description);
		bind("color", color);
		bind("marca", brand);
		bind("categoria", category);
		bind("precio", price);
		//bind()
	}

	public AddedProduct addToCart(int cant, Cart cart) throws Exception {
		if(!cart.equals(null)) {
			AddedProduct addedProduct = new AddedProduct(this, cant, cart);
			decrement(cant);
			this.pendingProducts.add(addedProduct);
			return addedProduct;
		}
		else{
			throw new Exception("Null cart");
		}
	}

	public void backToInventory(AddedProduct product) throws Exception {
		if(pendingProducts.contains(product)) {
			pendingProducts.remove(product);
			increment(product.getCant());
		}
		else{
			throw new Exception("Not valid AddedProduct");
		}
	}

	public void addUnits(Inventory inventory, int cant) throws Exception {
		if(cant >= 0){
			if(inventory.contains(this)){
				increment(getQuantity() + cant);
			}
		}
		else{
			throw new Exception("Not valid quantity");
		}
	}

	public void retireUnits(Inventory inventory, int cant) throws Exception {
		if(cant >= 0 && getQuantity() >= cant){
			if(inventory.contains(this)){
				decrement(getQuantity() - cant);
			}
		}
		else{
			throw new Exception("Not valid quantity");
		}
	}
	public void moveUnits(AddedProduct product, int cant) throws Exception {
		if(pendingProducts.contains(product)){
			if(cant >= 0){
				if(getQuantity()-cant >= 0) {
					product.cant.set(product.cant.getValue() + cant);
					decrement(cant);
				}
				else{
					throw new Exception("Not enough Stock");
				}
			}
			else{
				product.cant.set(product.cant.getValue() - cant);
				increment(cant);
			}
		}
		else{
			throw new Exception("Not valid AddedProduct");
		}
	}

	private void increment(int cant) throws Exception {
		if(cant > 0){
			setQuantity(getQuantity() + cant);
		}
		else{
			throw new Exception("Not valid quantity for retirement");
		}
	}

	private void decrement(int cant) throws Exception {
		if(cant >= 0 ){
			if(getQuantity()-cant >= 0){
				setQuantity(getQuantity() - cant);
			}
			else{
				throw new Exception("Not enough Stock");
			}

		}
		else{
			throw new Exception("Not valid quantity for retirement");
		}
	}
	public Integer getCode() {
		return code.get_value();
	}
	public void setCode(Integer code) throws SQLException {
		set(this.code,Value.create(code));
	}

	public Integer getQuantity() {
		return quantity.get_value();
	}
	public void setQuantity(Integer quantity) throws SQLException {
		if(quantity > 0){
			 set(this.quantity, Value.create(quantity));
		}
		else if(quantity == 0){
			set(this.quantity, Value.create(0));
		}
	}

	public String getName() {
		return name.get_value();
	}
	public void setName(String name) throws Exception {
		if(!name.equals("")){
			set(this.name, Value.create(name));
		}
		else{
			throw new Exception("Not valid product name");
		}
	}

	public String getDescription() {
		return description.get_value();
	}
	public void setDescription(String description) throws Exception {
		if(!description.equals("")){
			set(this.description, Value.create(description));
		}
		else{
			throw new Exception("Not valid description");
		}
	}

	public String getColor() {
			return color.get_value();
	}
	public void setColor(String color) throws Exception {
		if(!color.equals("")){
			set(this.color, Value.create(color));
		}
		else{
			throw new Exception("Not valid color");
		}
	}

	public String getBrand() {
		return brand.get_value();
	}
	public void setBrand(String brand) throws Exception {
		if(!brand.equals("")){
			set(this.brand, Value.create(brand));
		}
		else{
			throw new Exception("Not valid brand");
		}
	}

	public String getCategory() {
		return category.get_value();
	}
	public void setCategory(String category) throws Exception {
		if(!category.equals("")){
			set(this.category, Value.create(category));
		}
		else{
			throw new Exception("Not valid category");
		}
	}

	public float getPrice() {
		return price.get_value();
	}
	public void setPrice(Float price) throws Exception {
		if(price > 0){
			set(this.price, Value.create(price));
		}
		else{
			throw new Exception("Not a valid price");
		}
	}

	public boolean isVisible(){
		return visible.get_value() == 1;
	}

	public ItemIcon getIcon(){
		return this.icon;
	}

	public class AddedProduct extends ObservableValueBase<Integer> {

		private Product product;
		private SimpleIntegerProperty cant;
		private Cart cart;

		public AddedProduct(Product product, int cant, Cart cart) throws Exception {
			if (product != null && cart != null) {
				this.product = product;
				this.cant = new SimpleIntegerProperty(cant);
				this.cart = cart;

				this.cant.addListener(new ChangeListener<Number>(){
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						if(newValue.intValue() <= 1)
						{
							try {
								backToInventory();
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						};
						cart.getCartTable().refresh();
						cart.getPOSsesion().getPOSOpen().setTotalLabel();
					}
				});
			} else {
				throw new Exception("Not valid inicialization");
			}
		}

		public void incrementQuantity() throws Exception {
			this.product.moveUnits(this, 1);
			fireValueChangedEvent();
		}

		public void incrementQuantity(int cant) throws Exception {
			this.product.moveUnits(this, cant);
			this.fireValueChangedEvent();
		}

		public void reduceQuantity() throws Exception {
			reduceQuantity(1);
			this.fireValueChangedEvent();
		}

		public void reduceQuantity(int cant) throws Exception {
			if (this.cant.getValue() > cant) {
				this.product.moveUnits(this, -cant);
			} else {
				this.product.backToInventory(this);
			}
			this.fireValueChangedEvent();
		}

		public float getPrice() {
			return cant.getValue() * product.getPrice();
		}

		public int getCant() {
			return cant.getValue();
		}

		public void setCant(int cant) throws Exception {
			if (this.cant.getValue() > cant) {
				reduceQuantity(this.cant.getValue() - cant);
			} else {
				incrementQuantity(cant - this.cant.getValue());
			}
		}

		public void backToInventory() throws Exception {
			this.product.backToInventory(this);
			this.cart.remove(this);
			this.product = null;
		}

		@Override
		public Integer getValue() {
			return cant.getValue();
		}
	}

	}
