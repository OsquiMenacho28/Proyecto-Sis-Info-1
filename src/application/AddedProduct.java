package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

public class AddedProduct extends Product{
	SimpleIntegerProperty cant;
	public AddedProduct(int id, String name, float price, int stock, String brand, int cant, String Color, String category) {
		super(id, name, price, stock, brand, Color, category);
		this.cant = new SimpleIntegerProperty(this,"CantListener", 1);
	}
	
	public AddedProduct(Product p, int  cant ) {
		super(p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getBrand(), p.getColor(), p.getCategory());
		this.cant = new SimpleIntegerProperty(this,"CantListener", 1);
	}

	public void Add() {
		cant.set(cant.intValue() + 1);;
	}
	
	public void Sub() {
		cant.set(cant.intValue() - 1);;
	}
	
	public void addCantListener(ChangeListener <Number> list) {
		cant.addListener(list);
	}
	
	public float getTprice() {
		return cant.longValue() * price;
	}

	public int getCant() {
		return cant.intValue();
	}

	public void setCantProperty(SimpleIntegerProperty cant) {
		this.cant = cant;
	}
	
	public void setCant(int  cant) {
		this.cant.set(cant);
	}
	
}
