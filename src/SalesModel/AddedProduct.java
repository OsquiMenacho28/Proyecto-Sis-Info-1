package SalesModel;

import InventoryModel.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

public class AddedProduct extends Product {

	private Product product;
	private SimpleIntegerProperty cant;
	public AddedProduct(int code, int quantity, String description, String name, float price, String brand, int cant, String color, String category) {
		super(code, quantity, name, description, color, brand, category, price);
		this.cant = new SimpleIntegerProperty(this,"CantListener", 1);
	}
	
	public AddedProduct(Product p, int  cant ) {
		super(p.getCode(), p.getQuantity(), p.getName(), p.getDescription(), p.getColor(), p.getBrand(), p.getCategory(), p.getPrice());
		this.cant = new SimpleIntegerProperty(this,"CantListener", 1);
	}

	public void incrementQuantity() {
		cant.set(cant.intValue() + 1);;
	}
	
	public void reduceQuantity() {
		cant.set(cant.intValue() - 1);;
	}
	
	public void addCantListener(ChangeListener <Number> list) {
		cant.addListener(list);
	}
	
	public float getTotalPrice() {
		return cant.longValue() * price;
	}

	public int getCant() {
		return cant.intValue();
	}

	public void setCant(int  cant) {
		this.cant.set(cant);
	}

	public void setCantProperty(SimpleIntegerProperty cant) {
		this.cant = cant;
	}

}
