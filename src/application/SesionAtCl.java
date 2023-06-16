package application;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class SesionAtCl extends Sesion {

	private POSOpening POSOpening;
	private POSOpen POSOpen;
	private POSClosure POSClosure;
	private Notifications Notifications;
	private PaymentRequest PaymentRequest;
	private PaymentConfirmed PaymentConfirmed;
	private Sales Sales;
	private Inventory Inventory;

	private ArrayList<POSsesion> POSsesions;

	public SesionAtCl(User InputUser) throws Exception {
		super(InputUser);
		this.POSOpening = new POSOpening(this, new SelectAccount());
		super.mainWindow = this.POSOpening;
		this.POSOpening.hide();

		this.POSOpen = new POSOpen(0, products, this, mainWindow);
		this.POSOpen.hide();

		this.POSClosure = new POSClosure(0, 0, this, this.POSOpen);
		this.POSClosure.hide();

		this.Notifications = new Notifications(this, POSOpen);
		this.Notifications.hide();

		this.PaymentRequest = new PaymentRequest(null, this, this.POSOpen);
		this.PaymentRequest.hide();

		this.PaymentConfirmed = new PaymentConfirmed(this, this.POSOpen);
		this.PaymentConfirmed.hide();

		this.Sales = new Sales(this, this.POSOpen);
		this.Sales.hide();

		this.Inventory = new Inventory(this, this.POSOpen);
		this.Inventory.hide();

		this.POSsesions = new ArrayList<POSsesion>();

		this.run();
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
	private void showNotifications() throws IOException {
		POSClosure.show();
	}
	private void showPaymentRequest() throws IOException {
		PaymentRequest.restart();
		PaymentRequest.show();
	}
	private void showPaymentConfirmed() throws IOException {
		PaymentConfirmed.restart();
		PaymentConfirmed.show();
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
	public void openPOS(Float OpeningCount) throws IOException{
		if(!POSOpen.isActive()){
			this.POSOpening.hide();
			this.showPOSOpen();
			this.POSOpen.openSesion(OpeningCount);
		}
	}
	public void closePOSrequest(POSsesion POSsesion) throws IOException{
		if(POSOpen.isActive()){
			blurPOS();
			this.showPOSClosure();
		}
	}

	public void notificationsRequest() throws IOException{
		blurPOS();
		showNotifications();
	}

	public void inventoryRequest() throws IOException {
		blurPOS();
		showInventory();
	}

	public void salesRequest() throws IOException {
		blurPOS();
		showSales();
	}

	public void disposeRequest(Event windowEvent) throws IOException{
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

	public void exitRequest() throws IOException{
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

	public void emptyCart(){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		alertDialog.setTitle("ERROR!");
		alertDialog.setHeaderText("El Carrito de Compras se encuentra vacio!");
		alertDialog.setContentText("Por favor, añada productos al Carrito de Compras");
		alertDialog.initStyle(StageStyle.DECORATED);
		java.awt.Toolkit.getDefaultToolkit().beep();

		blurPOS();

		alertDialog.showAndWait();

		unblurPOS();
	}

	public void paymentRequest() throws IOException {
		blurPOS();
		showPaymentRequest();
	}

	public void run() throws IOException{
		this.showPOSOpening();
	}
}
