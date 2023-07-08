package application;

import DataBaseManager.DBManager;
import DataBaseManager.Value;
import InventoryModel.Color;
import InventoryModel.Inventory;
import InventoryModel.Product;
import application.FlowController.Sesion;
import application.FlowController.SesionAdmin;
import application.FlowController.SesionAtCl;
import application.FlowController.User;
import application.Interface.LI.SelectAccount;
import application.Interface.POS.POSOpen;
import application.Interface.POS.POSOpening;
import javafx.application.Application;

//import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		//SesionAdmin sesion = new SesionAdmin(User.searchDB("User1", "1234"));
		SesionAtCl sesion = new SesionAtCl(User.searchDB("User1", "1234"));
	}
	 public static void main(String[] args) {
		launch(args);
		;
	}
}

