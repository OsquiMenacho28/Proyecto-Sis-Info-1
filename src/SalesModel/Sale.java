package SalesModel;

import java.time.LocalDateTime;
import java.util.Date;

import DataBaseManager.LinkedObject;
import DataBaseManager.Value;
import InventoryModel.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sale extends LinkedObject {
	private Client client;
	private Cart cart;
	private LocalDateTime time;

	public Sale(Client client, Cart cart) throws Exception {
		super(POSsesion.saleRV,
				Value.create(1)); ////Modificar
		if(client.equals(null) || cart.equals(null) || !cart.isNotEmpty()){
			throw new Exception("Invalid inicialization");
		}
		this.client = client;
		this.cart = cart;
		this.time = LocalDateTime.now();
	}

	protected void defineBind() {
			//Hacer el bind
	}
}
