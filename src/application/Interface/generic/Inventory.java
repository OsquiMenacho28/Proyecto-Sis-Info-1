package application.Interface.generic;

import InventoryModel.Product;
import application.FlowController.Sesion;
import application.Interface.AtClPromptWindow;
import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import application.FlowController.SesionAtCl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Inventory extends AtClPromptWindow implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Line LeftLine1;

    @FXML
    private Line RightLine1;

    @FXML
    private Button OK_B;

    @FXML
    private TableView<Product> Inventory_T;

    @FXML
    private TableColumn<Product, String> ProductColumn;

    @FXML
    private TableColumn<Product, Float> PriceColumn;

    @FXML
    private TableColumn<Product, Integer> CantColumn;


    public Inventory(Sesion ses1, Inventory inventory, PromptWindow origin) throws IOException {
        super(ses1, "Inventory.fxml", origin);
        stage.setTitle("FERRETERÍA DIMACO - INVENTARIO");
        this.load();
        Inventory_T.setItems((ObservableList<Product>) inventory);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OK_B.setOnAction(actionEvent -> back());

        stage.setOnCloseRequest(windowEvent -> {
            try {
                ses.disposeRequest(windowEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ProductColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        CantColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
    }
}
