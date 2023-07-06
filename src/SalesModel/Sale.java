package SalesModel;

import DataBaseManager.*;
import InventoryModel.Product;
import application.FlowController.User;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Sale extends LinkedObject {

	private Client client;
	private Cart cart;
	private LocalDateTime time;

	private POSsesion sesion;
	private static final int paymentMethodCode = 1;
	private static final int coinCode = 1;
	private static final int exchangeRate = 1;
	private static final double discountAmount = 0.0;

	private Int_Value idVenta;
	private Date_Value dateTime;
	private Float_Value total;
	private Int_Value cashier;
	private Int_Value idClient;

	private ObservableList<Product.AddedProduct> products;

	public Sale(Client client, Cart cart, POSsesion sesion) throws Exception {
		super(SalesList.saleRV,
				Value.create(sesion.getNewSaleNumber()),
				Value.create(cart.getTotalPrice()),
				Value.create(sesion.getCashierNumber()),
						Value.create(client.getID()));

		this.client = client;
		this.cart = cart;
		products = cart.getCartTable().getItems();
		this.time = LocalDateTime.now();
		this.sesion = sesion;
	}

	public Sale(RowMirror row) throws Exception {
		super(row);
	}

	protected void defineBind() {
			bind("id_venta",idVenta );
			bind("fecha", dateTime );
			bind("total", total);
			bind("caja_id_caja", cashier);
			bind("cliente_id_cliente", idClient);
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

	public Cart getCart(){
		return this.cart;
	}

	public float getMonto(){
		return this.cart.getTotalPrice();
	}
}
