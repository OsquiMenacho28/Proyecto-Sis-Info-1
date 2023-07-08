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


	protected Int_Value code = new Int_Value();

	protected String_Value name = new String_Value();
	protected String_Value description = new String_Value();
	protected Float_Value price = new Float_Value();
	protected Int_Value quantity = new Int_Value();
	protected Int_Value colorFK = new Int_Value();
	protected Int_Value brandFK = new Int_Value();
	protected Int_Value categoryFK = new Int_Value();

	protected Color color;
	protected Brand brand;
	protected Category category;

	private static int measurementUnitCode = 58;

	protected Int_Value visible = Value.create(1);
	
	private ArrayList<AddedProduct> pendingProducts;
	private Inventory inventory;
	private ItemIcon icon;
	public Product(Inventory inventory, int code, String name, String description,float price, int quantity,   String color, String brand, String category) throws Exception {
		super(Inventory.productRV,
									Value.create(code),
									Value.create(name),
									Value.create(description),
									Value.create(price),
									Value.create(quantity),
									Value.create(inventory.checkBrand(brand)),
									Value.create(inventory.checkCategory(category)),
				 					Value.create(inventory.checkColor(color)));


		this.pendingProducts = new ArrayList<AddedProduct>();
		this.inventory = inventory;
		super.addListener( new ChangeListener<Value>(){
			@Override
			public void changed(ObservableValue<? extends Value> observable,Value oldValue, Value newValue) {
				icon.updateContent();
			}
		});
		defineBind();
		link();

		this.color = inventory.getColorsTable().getWithPK(colorFK.get_value());
		this.brand = inventory.getBrandsTable().getWithPK(brandFK.get_value());
		this.category = inventory.getCategoriesTable().getWithPK(categoryFK.get_value());
		this.icon = new ItemIcon(this);
	}

	public Product(Inventory inventory, RowMirror record) throws Exception {
		super(record);
		this.pendingProducts = new ArrayList<AddedProduct>();
		this.inventory = inventory;
		this.visible = Value.create(1);
		super.addListener( new ChangeListener<Value>(){
			@Override
			public void changed(ObservableValue<? extends Value> observable,Value oldValue, Value newValue) {
				icon.updateContent();
			}
		});
		defineBind();
		link();

		this.color = inventory.getColorsTable().getWithPK(colorFK.get_value());
		this.brand = inventory.getBrandsTable().getWithPK(brandFK.get_value());
		this.category = inventory.getCategoriesTable().getWithPK(categoryFK.get_value());

		//System.out.print(inventory.getColorsTable().getCollection());
		this.icon = new ItemIcon(this);
	}

	protected void defineBind(){
		bind("ID_PRODUCTO", code);
		bind("NOMBRE", name);
		bind("DESCRIPCION", description);
		bind("PRECIO_UNITARIO", price);
		bind("STOCK", quantity);
		bind("COLOR_PRODUCTO_ID_COLOR_PRODUCTO", colorFK);
		bind("MARCA_PRODUCTO_ID_MARCA", brandFK);
		bind("CATEGORIA_PRODUCTO_ID_CATEGORIA_PRODUCTO", categoryFK);
	}

	public AddedProduct addToCart(int cant, Cart cart) throws Exception {
		if(!cart.equals(null)) {
			if(!pendingProducts.isEmpty()){
				pendingProducts.get(0).incrementQuantity(1);
				return pendingProducts.get(0);
			}
			else{
				AddedProduct addedProduct = new AddedProduct(this, cant, cart);
				decrement(cant);
				this.pendingProducts.add(addedProduct);
				cart.collection.add(addedProduct);
				return addedProduct;
			}
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
			if(inventory.getCollection().contains(this)){
				increment(getQuantity() + cant);
			}
		}
		else{
			throw new Exception("Not valid quantity");
		}
	}

	public void retireUnits(Inventory inventory, int cant) throws Exception {
		if(cant >= 0 && getQuantity() >= cant){
			if(inventory.getCollection().contains(this)){
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
	public Color getColor() {
		return color;
	}
	public void setColor(String color) throws Exception {
		if(!color.equals("")){
			set(this.colorFK, Value.create(inventory.checkColor(color)));
			this.color = inventory.getColorsTable().getWithPK(this.colorFK.get_value());
		}
		else{
			throw new Exception("Not valid color");
		}
	}

	public Brand getBrand() {
		return brand;
	}
	public void setBrand(String brand) throws Exception {
		if(!brand.equals("")){
			set(this.brandFK, Value.create(inventory.checkBrand(brand)));
			this.brand = inventory.getBrandsTable().getWithPK(this.brandFK.get_value());
		}
		else{
			throw new Exception("Not valid brand");
		}
	}

	public Category getCategory() {
		return category;
	}
	public void setCategory(String category) throws Exception {
		if(!category.equals("")){
			set(this.categoryFK, Value.create(inventory.checkCategory(category)));
			this.category = inventory.getCategoriesTable().getWithPK(this.categoryFK.get_value());
		}
		else{
			throw new Exception("Not valid category");
		}
	}
	public String getBrandString(){
		return this.brand.getBrand();
	}

	public String getColorString(){
		return this.color.getColor();
	}

	public String getCategoryString(){
		return this.category.getCategory();
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

	public static int getMeasurementUnitCode() {
		return measurementUnitCode;
	}

	public static void setMeasurementUnitCode(int measurementUnitCode) {
		Product.measurementUnitCode = measurementUnitCode;
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

		public Product getProduct(){
			return this.product;
		}

		@Override
		public Integer getValue() {
			return cant.getValue();
		}
	}

	}
