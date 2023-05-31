package application;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sale {
	String name;
	private ObservableList<AddedProduct> cart = FXCollections.observableArrayList();
	int NIT;
	
	public Sale(String name, int nIT, ObservableList<AddedProduct> cart) {
		super();
		this.cart = cart;
		this.name = name;
		NIT = nIT;
	}
	
	
	
	/**
	 * @return the cart
	 */
	public ObservableList<AddedProduct> getCart() {
		return cart;
	}



	/**
	 * @param cart the cart to set
	 */
	public void setCart(ObservableList<AddedProduct> cart) {
		this.cart = cart;
	}



	/**
	 * @return the monto
	 */
	public float getMonto() {
		float monto = 0;
		for(AddedProduct e : cart) {
			monto += e.getTprice(); 
		}
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */

	/**
	 * @return the date
	 */

	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the nIT
	 */
	public int getNIT() {
		return NIT;
	}
	/**
	 * @param nIT the nIT to set
	 */
	public void setNIT(int nIT) {
		NIT = nIT;
	}
	
	public String getsNIT() {
		return String.valueOf(NIT);
	}
	
	
}
