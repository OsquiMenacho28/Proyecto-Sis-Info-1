package application;

import java.io.IOException; 
import java.sql.SQLException;

import java.sql.Connection;

import DataBase.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sesion {
	User LogedUser;
	Stage Window;
	Scene scene;
	
	POSOpening POSOpening;
	POSClosure POSClosure;
	POSOpen POSOpen;
	AddProd AddProd;
	CashPayment cpp;
	SalesHist sH;
	
	DBManager DataBase;
	Float sum = (float) 0.0;
	
	private ObservableList<Sale> sales = FXCollections.observableArrayList();
	private ObservableList<Client> clients = FXCollections.observableArrayList();
	private ObservableList<Product> products = FXCollections.observableArrayList();
	
	public Sesion(User inputUser, LogIn x) throws Exception {
		//this.DataBase = new DBManager("jdbc:mysql://localhost:2808/ferreteria_dimaco_database", "root", "osquimenacho28");
		this.LogedUser = inputUser;
		this.Window = new Stage();
		
		POSOpening = new POSOpening(this, x);
		POSOpening.show();

	}
	
	public void addProd(POSOpen x) throws IOException {
		AddProd = new AddProd(products, this, x);
		AddProd.show();
	}
	
	public void cashPayment(ObservableList<AddedProduct> cart) throws IOException {
		cpp = new CashPayment(cart, clients, this,  POSOpen);
		cpp.show();
	}
	
	public void POSClosure(Float opening,POSOpen x) throws IOException {
		POSClosure = new POSClosure(opening, sum, this, x);
		POSClosure.show();
	}
	
	public void POSOpen(Float OpeningCount) throws IOException {
		POSOpen = new POSOpen(OpeningCount, products, this, POSOpening);
		POSOpen.show();
	}
	
	public void POSOpening() throws IOException {
		new POSOpening(this, null);
	}
	
	public void SalesHist(PromptWindow x) throws IOException {
		sH = new SalesHist(this.sales, this, x);
		sH.show();
	}
	
	public void show(PromptWindow origin) {
		origin.show();
	}
	
	public void updateProducts(ObservableList<Product> products) {
		this.products = products;
		POSOpen.refreshProducts(this.products);
	}
	
	public void addProduct(Product x) throws SQLException {
		products.add(x);
		POSOpen.getCategoryList();
		POSOpen.refreshProductList(POSOpen.products);
	}
	
	public void updateClients(ObservableList<Client> clients) {
		this.clients = clients;
	}
	
	public void updateSales(ObservableList<Sale> sales) {
		this.sales = sales;
	}
	
	public void addSale(Client c, ObservableList<AddedProduct> cart) throws IOException {
		Sale aux = new Sale(c.getName(), c.getNIT(), cart);
		sum += aux.getMonto();
		sales.add(aux);
		POSOpen.Ncart();
		System.out.println("Saved");
		new Completed(aux, this, null);
	}
	
}
