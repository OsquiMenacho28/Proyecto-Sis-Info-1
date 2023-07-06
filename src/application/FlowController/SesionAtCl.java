package application.FlowController;

import DataBaseManager.DBManager;
import ElectronicInvoice.GenerateInvoice;
import SalesModel.Cart;
import SalesModel.POSsesion;
import application.Interface.LI.SelectAccount;
import application.Interface.POS.*;
import application.Interface.PromptWindow;
import application.Interface.generic.Inventory;
import application.Interface.generic.Notifications;
import application.Interface.generic.Sales;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class SesionAtCl extends Sesion{

	private application.Interface.POS.POSOpening POSOpening;
	private application.Interface.POS.POSOpen POSOpen;
	private application.Interface.POS.POSClosure POSClosure;
	private application.Interface.generic.Notifications Notifications;
	private application.Interface.POS.PaymentRequest PaymentRequest;
	private application.Interface.POS.PaymentConfirmed PaymentConfirmed;
	private application.Interface.generic.Sales Sales;
	private application.Interface.generic.Inventory Inventory;
	private ClosureConfirmed ClosureConfirmed;
	private GenerateInvoice GenerateInvoice;

	private ArrayList<POSsesion> POSsesions;

	private InventoryModel.Inventory inventory;
	private DBManager manager;

	public SesionAtCl(User InputUser) throws Exception {
		super(InputUser);

		this.manager = new DBManager("jdbc:mysql://localhost:2808/ferreteria_dimaco_database",
				"root", "osquimenacho28");

		this.inventory = new InventoryModel.Inventory(manager);

		this.POSOpening = new POSOpening(this, new SelectAccount());
		super.mainWindow = this.POSOpening;
		this.POSOpening.hide();

		this.POSOpen = new POSOpen(this, this.POSOpening, this.inventory);
		this.POSOpen.hide();

		this.POSClosure = new POSClosure(0, 0, this, this.POSOpen);
		this.POSClosure.hide();

		this.ClosureConfirmed = new ClosureConfirmed(this, this.POSClosure);
		this.ClosureConfirmed.hide();

		this.Notifications = new Notifications(this, POSOpen);
		this.Notifications.hide();

		this.PaymentRequest = new PaymentRequest(this, this.POSOpen);
		this.PaymentRequest.hide();

		this.PaymentConfirmed = new PaymentConfirmed(this, this.POSOpen);
		this.PaymentConfirmed.hide();

		this.GenerateInvoice = new GenerateInvoice(this, new Stage(StageStyle.UNDECORATED), this.POSOpen);
		this.GenerateInvoice.hide();

		this.Sales = new Sales(this, this.POSOpen);
		this.Sales.hide();

		this.Inventory = new Inventory(this, this.inventory, this.POSOpen);
		this.Inventory.hide();

		this.POSsesions = new ArrayList<POSsesion>();

		this.run();
	}

	public void run() throws IOException{
		this.showPOSOpen();
		blurPOS();
		this.showPOSOpening();
	}

	public void openPOS(Float OpeningCount) throws IOException{
		if(!POSOpen.isActive()){
			this.POSOpening.hide();
			this.showPOSOpen();
			this.POSOpen.activate(new POSsesion(OpeningCount, this.POSOpen));
		}
	}
	public void closePOSrequest(POSsesion POSsesion) throws IOException{
		if(POSOpen.isActive()) {
			blurPOS();
			this.showPOSClosure();
		}
	}

	public void onClosureConfirmedRequest() {
		blurPOS();
		showClosureConfirmed();
	}

	public void notificationsRequest() throws IOException{
		blurPOS();
		showNotifications();
	}

	public void paymentRequest(Cart cart) throws IOException {
		if (cart.isEmpty()) {
			Alert alertDialog = new Alert(Alert.AlertType.ERROR);
			alertDialog.setTitle("ERROR!");
			alertDialog.setHeaderText("¡El Carrito de Compras se encuentra vacio!");
			alertDialog.setContentText("Por favor, añada productos al Carrito de Compras");
			alertDialog.initStyle(StageStyle.DECORATED);
			java.awt.Toolkit.getDefaultToolkit().beep();
			blurPOS();
			alertDialog.showAndWait();
			unblurPOS();
		}
		else {
			blurPOS();
			showPaymentRequest();
		}
	}

	public void paymentConfirmedRequest() throws IOException {
		blurPOS();
		showPaymentConfirmed();
	}

	public void onGenerateInvoiceRequest() {
		blurPOS();
		showGenerateInvoice();
	}

	public void salesRequest() throws IOException {
		blurPOS();
		showSales();
	}

	public void inventoryRequest() throws IOException {
		blurPOS();
		showInventory();
	}

	public void logOutRequest() throws IOException{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("CERRAR SESIÓN");
		alert.setHeaderText("¡AVISO!");
		alert.setContentText("¿Está seguro de Cerrar Sesión?");
		alert.initStyle(StageStyle.DECORATED);

		blurPOS();

		Optional <ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			POSOpen.hide();
			try {
				new SelectAccount();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			unblurPOS();
		}
	}

	public void closeApplicationRequest(Event windowEvent) throws IOException{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("CERRAR APLICACIÓN");
		alert.setHeaderText("¡ADVERTENCIA!");
		alert.setContentText("Se cerrará la aplicación. ¿Desea continuar?");
		alert.initStyle(StageStyle.DECORATED);

		blurPOS();
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Platform.exit();
		}
		else {
			unblurPOS();
			windowEvent.consume();
		}
	}

	private void showPOSOpening() throws IOException {
		POSOpening.restart();
		POSOpening.show();
	}
	private void showPOSOpen() throws IOException {
		POSOpen.show();
	}
	private void showPOSClosure() throws IOException {
		POSClosure.show();
	}

	private void showClosureConfirmed() {
		ClosureConfirmed.show();
	}

	private void showNotifications() throws IOException {
		Notifications.show();
	}
	private void showPaymentRequest() throws IOException {
		PaymentRequest.restart();
		PaymentRequest.show();
	}
	private void showPaymentConfirmed() throws IOException {
		PaymentConfirmed.restart();
		PaymentConfirmed.show();
	}

	private void showGenerateInvoice() {
		GenerateInvoice.show();
	}

	private void showSales() throws IOException {
		Sales.show();
	}
	private void showInventory() throws IOException {
		Inventory.show();
	}

	private void show(PromptWindow window) {
		window.show();
	}

	private void blurPOS(){
		POSOpen.setEffect(blurEffect);
	}

	private void unblurPOS(){
		POSOpen.setEffect(null);
	}

	public User getUser(){
		return super.LogedUser;
	}
}
