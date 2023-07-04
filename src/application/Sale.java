package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Sale {
	private String name;
	private int NIT;
	private static final int paymentMethodCode = 1;
	private static final int coinCode = 1;
	private static final int exchangeRate = 1;
	private static final double discountAmount = 0.0;

	private Date date;
	private ObservableList<AddedProduct> cart = FXCollections.observableArrayList();

	public Sale(String name, int NIT, ObservableList<AddedProduct> cart) {
		this.cart = cart;
	}

	public static int getPaymentMethodCode() {
		return paymentMethodCode;
	}

	public static int getCoinCode() {
		return coinCode;
	}

	public static int getExchangeRate() {
		return exchangeRate;
	}

	public static double getDiscountAmount() {
		return discountAmount;
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
	 * @return the date
	 */
	public Date getDate(){
		return this.date;
	}

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
	 * @return the NIT
	 */
	public int getNIT() {
		return NIT;
	}
	/**
	 * @param NIT the NIT to set
	 */
	public void setNIT(int NIT) {
		this.NIT = NIT;
	}

	public String getsNIT() {
		return String.valueOf(NIT);
	}
}
