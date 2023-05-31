package application;

import java.io.IOException; 
import java.net.URL;
import java.util.ResourceBundle;

import java.sql.Connection;

import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.converter.FloatStringConverter;


public class POSOpening extends PromptWindow implements Initializable{

	
	@FXML
	Button HisButton;
	
	@FXML
	TextField CashierField;
	
	@FXML
	Button BackButton;
	
	@FXML
	Button CloseButton;

	Boolean flag;
	Float OpeningCount;
	
	public POSOpening(Sesion ses, PromptWindow origin) throws IOException {
		super(ses, "POSOpening.fxml", origin);
		this.OpeningCount = (float) 0.0;	
		this.flag =false;
		
		load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		CashierField.textProperty().addListener(e -> {
			if(CashierField.getText() != null) {
				Float ninput = Float.parseFloat(CashierField.getText());
				
				if(ninput < 0) {
					CashierField.setStyle("-fx-text-fill : black;");
					flag = false;
				}
				else {
					OpeningCount = ninput;
					CashierField.setStyle("-fx-text-fill : white;");
					flag = true;
				}		
			}
		});
		
		CashierField.setTextFormatter(new TextFormatter<>(new FloatStringConverter()));
		CashierField.setText(String.valueOf(OpeningCount));
		
		HisButton.setOnAction(e -> {try {
			ses.SalesHist(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}});
		BackButton.setOnAction(e -> {back();});
		CloseButton.setOnAction(e -> { if(flag) {try {
			close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}} });
	}
	
	
	public void close() throws IOException{
		dispose();
		ses.POSOpen(OpeningCount);	
	}
}
