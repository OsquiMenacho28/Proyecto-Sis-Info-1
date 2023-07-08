package SalesModel;

import DataBaseManager.*;
import InventoryModel.Product;
import application.FlowController.User;
import javafx.collections.ObservableList;

import java.sql.Date;
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

	private Int_Value idVenta = new Int_Value();
	private Date_Value dateTime = new Date_Value();
	private Float_Value total = new Float_Value();
	private Int_Value cashier = new Int_Value();
	private Int_Value idClient = new Int_Value();

	private ObservableList<Product.AddedProduct> products;

	public Sale(Client client, Cart cart, POSsesion sesion) throws Exception {
		super(SalesList.saleRV,
				Value.create(sesion.getNewSaleNumber()),
				Value.create(new Date(System.currentTimeMillis())),
				Value.create(cart.getTotalPrice()),
				Value.create(sesion.getCashierNumber()),
				Value.create(client.getID()));

		this.client = client;
		this.cart = cart;
		products = cart.getCartTable().getItems();
		this.time = LocalDateTime.now();
		this.sesion = sesion;
		defineBind();
		link();
	}

	public Sale(RowMirror row) throws Exception {
		super(row);
		defineBind();
		link();
	}
	@Override
	protected void defineBind() {
			bind("ID_VENTA",idVenta );
			bind("FECHA", dateTime );
			bind("TOTAL", total);
			bind("CAJA_ID_CAJA", cashier);
			bind("CLIENTE_ID_CLIENTE", idClient);
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

	public Client getClient(){
		return this.client;
	}
}
