package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashPayment extends PromptWindow implements Initializable{

	SesionAtCl ses;

	@FXML
	Button BackButton;
	
	@FXML
	Button MainButton;
	
	@FXML
	TextField NameField;
	
	@FXML
	TextField NITField;
	
	@FXML
	TableView<Client> Table;
	
	@FXML
	TableColumn<Client, String> NameColumn;
	
	@FXML
	TableColumn<Client, Integer> NITColumn;
	
	ObservableList<Client> clients = FXCollections.observableArrayList();
	ObservableList<AddedProduct> cart = FXCollections.observableArrayList();
	Boolean flag = false;
	
	public CashPayment(ObservableList<AddedProduct> cart, ObservableList<Client> clients, SesionAtCl ses, POSOpen origin) throws IOException{
		super(ses, "CashPayment.fxml", origin);
		this.cart = cart;
		this.clients = clients;
		
		load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Table.setEditable(false);
		BackButton.setOnAction(e -> backDispose());
		
		NITField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		
		MainButton.setOnAction(e -> {
			try {
				confirm();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		NameField.textProperty().addListener(e -> {refresh();});
		
		NITField.textProperty().addListener(e -> {refresh();});
		
		NITField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
		            NITField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		NameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
		NITColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("NIT"));
		
		Table.setItems(clients);
	}
	
	private void refresh(){
		
	}
	
	private Boolean v(String x) {
		return (x != null) && !x.equals(""); 
	}
	
	private Client addClient() {
		
		String name = NameField.getText();
		String NIT = NITField.getText();
		
		
		if(v(NIT) && v(name)) {
			Client aux = new Client(name, Integer.parseInt(NIT));
			int pos = indexClient(aux.NIT);
			if(pos >= 0) {
				clients.set(pos, aux);
			}
			else {
				clients.add(aux);
			}
			flag = true;
			return aux;
		}
		
		return null;
	}
	
	
	private int indexClient(int NIT) {
		for(int i = 0; i <clients.size(); i++ ) {
			if(clients.get(i).getNIT() == NIT) {
				return i;
			}
		}
		return -1;
	}
	
	
	private void confirm() throws IOException {
		Client aux = addClient();
		if(aux != null && flag) {
			ses.addSale(aux, this.cart);
			ses.updateClients(clients);
			dispose();
		}	
	}
}
