package SalesModel;

import InventoryModel.Product;


public class AddedProduct{

	private Product product;
	private int cant;

	private Cart cart;
	
	public AddedProduct(Product product, int  cant, Cart cart) throws Exception {
		if(!product.equals(null) && !cart.equals(null)) {
			this.product = product;
			this.cant = cant;
			this.cart = cart;
		}
		else{
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
		if(cant > 1){
			this.product.moveUnits(this, -1);
		}
		else{
			this.product.backToInventory(this);
		}
	}

	public void reduceQuantity(int cant) throws Exception {
		if(this.cant >  cant){
			this.product.moveUnits(this, -cant);
		}
		else{
			this.product.backToInventory(this);
		}
	}
	
	public float getPrice() {
		return cant * product.getPrice();
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int  cant) throws Exception {
		if(this.cant > cant){
			reduceQuantity(this.cant - cant);
		}
		else{
			incrementQuantity(cant-this.cant);
		}
	}

	public void backToInventory() throws Exception {
		this.product.backToInventory(this);
	}

}
