package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.converter.FloatStringConverter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


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
	
	public POSOpening(SesionAtCl ses, PromptWindow origin) throws IOException {
		super(ses, "POSOpening.fxml", origin);
		this.OpeningCount = (float) 0.0;
		this.flag = false;
		stage.setTitle("APERTURA DE CAJA");
		stage.setWidth(702);
		stage.setHeight(350);
		this.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		CashierField.textProperty().addListener(ev -> {
			if(CashierField.getText() != null) {
				float numInput = Float.parseFloat(CashierField.getText());
				
				if (numInput < 0) {
					CloseButton.setDisable(true);
					CashierField.setStyle("-fx-text-fill : white; -fx-background-color : red;");
					Toolkit.getDefaultToolkit().beep();
					flag = false;
				}
				else {
					if (numInput > 0) {
						CloseButton.setDisable(false);
						this.setOnKeyReleased((e) -> {
							if (e.getCode() == KeyCode.ENTER) {
								try {
									open();
								} catch (IOException ex) {
									throw new RuntimeException(ex);
								}
							}
						});
						OpeningCount = numInput;
						CashierField.setStyle("-fx-text-fill : black;");
						flag = true;
					}
					else {
						CloseButton.setDisable(true);
						flag = false;
					}
				}		
			}
		});
		
		CashierField.setTextFormatter(new TextFormatter<>(new FloatStringConverter()));
		CashierField.setText(String.valueOf(OpeningCount));
		
		HisButton.setOnAction(e -> {try {
			ses2.SalesHist(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}});

		BackButton.setOnAction(e -> {
			try {
				origin = new SelectAccount();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			back();
		});

		CloseButton.setOnAction(e -> { if (flag) {try {
			open();
		} catch (IOException e1) {
			e1.printStackTrace();
		}} });
	}

	public void open() throws IOException{
		this.hide();
		ses2.POSOpen(OpeningCount);
	}
}
