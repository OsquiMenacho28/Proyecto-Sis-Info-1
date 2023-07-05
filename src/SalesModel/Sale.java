package SalesModel;

import java.time.LocalDateTime;
import java.util.Date;

import DataBaseManager.*;
import InventoryModel.Inventory;
import InventoryModel.Product;
import application.FlowController.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sale extends LinkedObject {

	private Int_Value idVenta;
	private Date_Value dateTime;
	private Float_Value total;
	private Int_Value cashier;
	private Int_Value idClient;

	private ObservableList<Product.AddedProduct> products;
	private Client client;
	private Cart cart;
	private LocalDateTime time;

	public Sale(Client client, Cart cart, User user) throws Exception {
		super(, Value.create(), Value.create(cart.getTotalPrice()), Value.create(user.getCashierID(), Value.create(client.getID()));

		this.client = client;
		this.cart = cart;
		this.time = LocalDateTime.now();
	}

	protected void defineBind() {
			bind("id_venta",idVenta );
			bind("fecha", dateTime );
			bind("total", total);
			bind("caja_id_caja", cashier);
			bind("cliente_id_cliente", idClient)
	}
}
