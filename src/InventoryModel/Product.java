package InventoryModel;
import DataBaseManager.*;
import SalesModel.Cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Product extends LinkedObject {


	protected Int_Value code;
	protected Int_Value quantity;
	protected String_Value name;
	protected String_Value description;
	protected String_Value color;
	protected String_Value brand;
	protected String_Value category;
	protected Float_Value price;
	
	private ArrayList<AddedProduct> pendingProducts;
	private Inventory inventory;
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
		defineBind();
		link();
	}

	public Product(Inventory inventory, RowMirror record) throws Exception {
		super(record);
		this.pendingProducts = new ArrayList<AddedProduct>();
		this.inventory = inventory;
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

	public void addUnits(Inventory inventory, int cant){

	}

	public void retireUnits(Inventory inventory, int cant){

	}
	public void moveUnits(AddedProduct product, int cant) throws Exception {
		if(pendingProducts.contains(product)){
			if(cant >= 0){
				if(getQuantity()-cant >= 0) {
					product.cant += cant;
					decrement(cant);
				}
				else{
					throw new Exception("Not enough Stock");
				}
			}
			else{
				product.cant -= cant;
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
		else{
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


	public class AddedProduct {

		private Product product;
		private int cant;

		private Cart cart;

		public AddedProduct(Product product, int cant, Cart cart) throws Exception {
			if (!product.equals(null) && !cart.equals(null)) {
				this.product = product;
				this.cant = cant;
				this.cart = cart;
			} else {
				throw new Exception("Not valid inicialization");
			}
		}

		public void incrementQuantity() throws Exception {
			this.product.moveUnits(this, 1);
		}

		public void incrementQuantity(int cant) throws Exception {
			this.product.moveUnits(this, cant);
		}

		public void reduceQuantity() throws Exception {
			if (cant > 1) {
				this.product.moveUnits(this, -1);
			} else {
				this.product.backToInventory(this);
			}
		}

		public void reduceQuantity(int cant) throws Exception {
			if (this.cant > cant) {
				this.product.moveUnits(this, -cant);
			} else {
				this.product.backToInventory(this);
			}
		}

		public float getPrice() {
			return cant * product.getPrice();
		}

		public int getCant() {
			return cant;
		}

		public void setCant(int cant) throws Exception {
			if (this.cant > cant) {
				reduceQuantity(this.cant - cant);
			} else {
				incrementQuantity(cant - this.cant);
			}
		}

		public void backToInventory() throws Exception {
			this.product.backToInventory(this);
			this.product = null;
		}
	}

	}
