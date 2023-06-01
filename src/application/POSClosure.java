package application;

import javafx.beans.binding.FloatBinding;
import javafx.beans.property.SimpleFloatProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class POSClosure extends PromptWindow implements Initializable{
	@FXML
	Label OpeningLabel;

	@FXML
	Label TotalSalesLabel;
	
	@FXML
	TextField CashierField;
	
	@FXML
	Button CloseButton;

	@FXML
	Button BackButton;
	
	Boolean flag;
	Float OpeningCount;
	Float rClosureCount;
	SimpleFloatProperty ClosureCount;
	FloatBinding TotalSalesCount;
	
	
	public POSClosure(float OpeningCount, float ClosureCount, SesionAtCl ses, POSOpen origin) throws IOException {
		
		super(ses, "POSClosure.fxml", origin);
		
		this.OpeningCount = OpeningCount;
		this.rClosureCount = ClosureCount;
		this.ClosureCount = new SimpleFloatProperty(ClosureCount);
		
		TotalSalesCount = this.ClosureCount.subtract(this.OpeningCount);
		flag = false;
		
		load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		OpeningLabel.setText(String.valueOf(OpeningCount));
		TotalSalesLabel.setText(String.valueOf(TotalSalesCount.getValue()));

		BackButton.setOnAction(e -> {back();});
		CashierField.textProperty().addListener(e -> {
			
			String input = CashierField.getText();
			Float ninput = Float.parseFloat(input);
			
			if(ninput < rClosureCount) {
				ClosureCount.setValue(rClosureCount);
				CashierField.setStyle("-fx-text-fill : black;");
				flag = false;
			}
			else {
				ClosureCount.setValue(ninput);
				CashierField.setStyle("-fx-text-fill : white;");
				flag = true;
			}
			TotalSalesLabel.setText(String.valueOf(TotalSalesCount.getValue()));
			
		});
		
		CashierField.setTextFormatter(new TextFormatter<>(new FloatStringConverter()));
		CashierField.setText(String.valueOf(ClosureCount.floatValue()));
		
	

		CloseButton.setOnAction(e -> {if(flag) {close();};});
	}
	
	public void close() {
		dispose();
	}
}
