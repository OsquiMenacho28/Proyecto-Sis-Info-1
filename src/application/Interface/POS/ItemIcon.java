package application.Interface.POS;

import InventoryModel.Product;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemIcon extends GridPane implements Initializable {
	
	@FXML
	ImageView ProductImage; 
	@FXML
	VBox DescriptionTab; 
	@FXML
	Label ProductLabel;
	@FXML
	VBox AtributeTab; 
	@FXML
	Label PriceLabel;
	@FXML
	Label StockLabel;
	@FXML
	Label BrandLabel;
	
	MenuItem deleteBtn;
	
	Product product;
	Boolean side = false;
	
	public ItemIcon(Product product) throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ItemIcon.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		this.product = product;
		loader.load();
		updateContent();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ContextMenu rmenu = new ContextMenu();
		rmenu.setStyle("-fx-effect: null;");
		
		//MenuItem editBtn = new MenuItem("Editar");
		deleteBtn = new MenuItem("Eliminar");
		
		//rmenu.getItems().add(editBtn);
		rmenu.getItems().add(deleteBtn);
		
		
		ProductImage.setPreserveRatio(true);
		ProductImage.setVisible(true);



		
		ItemIcon container = this;
		this.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				container.setStyle("-fx-background-color : rgba(196,196,196,.3);");
				side = !side;
				event.consume();
			}	
		});
		
		this.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
					if(!rmenu.isShowing()) {
						container.setStyle("-fx-background-color : rgba(196,196,196,.07);");
					}
				side = !side;
				event.consume();
			}	
		});	
		
		ItemIcon icon = this;
		
		this.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, new EventHandler<ContextMenuEvent>() {
			
			@Override
			public void handle(ContextMenuEvent event) {
				event.getSource();
				rmenu.show(icon, event.getScreenX(), event.getScreenY());
				event.consume();
			}
		});
		
		rmenu.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				if(!side) {
					setStyle("-fx-background-color : rgba(196,196,196,.07)");
				}
			}
			
		});

		container.setStyle("-fx-background-color : rgba(196,196,196,.07);");
	
	}
	
	
	public void updateContent() {
		ProductLabel.setText(product.getName());
		PriceLabel.setText("Precio : " + product.getPrice());
		StockLabel.setText("Cantidad : " + product.getQuantity());
		BrandLabel.setText("Marca : " + product.getBrand());
	}

	public Product getProduct() {
		return product;
	}

}
