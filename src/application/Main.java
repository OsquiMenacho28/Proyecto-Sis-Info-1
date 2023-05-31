package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

//import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	

	@Override
	public void start(Stage stage) {
		
		//DBManager x = new DBManager("root", "root", "localhost", "POSale_AH");
		try{
			new SelectAccount();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	 public static void main(String[] args) {
		launch(args);
		;
	}
}

