package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class NumberPad extends GridPane implements Initializable{
		
	@FXML
	Button b1;
	
	@FXML
	Button b2;

	@FXML
	Button b3;

	@FXML
	Button b4;

	@FXML
	Button b5;

	@FXML
	Button b6;

	@FXML
	Button b7;

	@FXML
	Button b8;
	

	@FXML
	Button b9;

	@FXML
	Button b0;

	@FXML
	Button MinButton;

	@FXML
	Button PlusButton;
	
	
	EventTarget targetNode;
	
	
	public NumberPad() throws IOException {
		this.targetNode = this;
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("NumberPad.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		loader.load();
	}
	
	public NumberPad(Node targetNode) throws IOException {
		this.targetNode = targetNode;
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("NumberPad.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		setAlignment(Pos.CENTER);
		setMinWidth(Region.USE_COMPUTED_SIZE);
		setMinHeight(Region.USE_COMPUTED_SIZE);
		prefHeightProperty().bind(widthProperty());
		
		
		b0.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "0", "DIGIT0", KeyCode.DIGIT0, false, false, false, false));});
		b1.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "1", "DIGIT1", KeyCode.DIGIT1, false, false, false, false));});
		b2.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "2", "DIGIT2", KeyCode.DIGIT2, false, false, false, false));});
		b3.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "3", "DIGIT3", KeyCode.DIGIT3, false, false, false, false));});
		b4.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "4", "DIGIT4", KeyCode.DIGIT4, false, false, false, false));});
		b5.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "5", "DIGIT5", KeyCode.DIGIT5, false, false, false, false));});
		b6.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "6", "DIGIT6", KeyCode.DIGIT6, false, false, false, false));});
		b7.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "7", "DIGIT7", KeyCode.DIGIT7, false, false, false, false));});
		b8.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "8", "DIGIT8", KeyCode.DIGIT8, false, false, false, false));});
		b9.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "9", "DIGIT9", KeyCode.DIGIT9, false, false, false, false));});
		
		MinButton.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "-", "MINUS", KeyCode.MINUS, false, false, false, false));});
		PlusButton.setOnAction(e -> {Event.fireEvent(targetNode,new KeyEvent(KeyEvent.KEY_RELEASED, "+", "PLUS", KeyCode.EQUALS, false, false, false, false));});
	
		
	}
	
	
	public void setTargetNode(EventTarget targetNode) {
		this.targetNode = targetNode;
	}
}
