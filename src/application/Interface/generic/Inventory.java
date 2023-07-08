package application.Interface.generic;

import InventoryModel.Product;
import application.FlowController.Sesion;
import application.FlowController.SesionAtCl;
import application.Interface.AtClPromptWindow;
import application.Interface.PromptWindow;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Inventory extends AtClPromptWindow implements Initializable {


    @FXML
    private TableColumn<Product, String> BrandColumn;

    @FXML
    private TableColumn<Product, Integer> CantColumn;

    @FXML
    private TableColumn<Product, String> CatColumn;

    @FXML
    private TableColumn<Product, Integer> CodeColumn;

    @FXML
    private TableView<Product> Inventory_T;

    @FXML
    private Line LeftLine1;

    @FXML
    private TableColumn<Product, String> NameColumn;

    @FXML
    private Button OK_B;

    @FXML
    private Line RightLine1;

    @FXML
    private GridPane gridPane;


    public Inventory(SesionAtCl ses1, InventoryModel.Inventory inventory, PromptWindow origin) throws IOException {
        super(ses1, "Inventory.fxml", origin);
        stage.setTitle("FERRETERÍA DIMACO - INVENTARIO");
        this.load();
        Inventory_T.setItems(inventory.getCollection());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OK_B.setOnAction(actionEvent -> back());

        stage.setOnCloseRequest(windowEvent -> {
            try {
                sesion.closeApplicationRequest(windowEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        BrandColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> addedProductStringCellDataFeatures) {
                return new SimpleStringProperty(addedProductStringCellDataFeatures.getValue().getBrandString());
            }
        });

        CantColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Integer>, ObservableValue<Integer>>(){
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Product, Integer> addedProductStringCellDataFeatures) {
                return new SimpleObjectProperty<>(addedProductStringCellDataFeatures.getValue().getQuantity());
            }
        });

        CatColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> addedProductStringCellDataFeatures) {
                return new SimpleStringProperty(addedProductStringCellDataFeatures.getValue().getCategoryString());
            }
        });

        CodeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Integer>, ObservableValue<Integer>>(){
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Product, Integer> addedProductStringCellDataFeatures) {
                return new SimpleObjectProperty<>(addedProductStringCellDataFeatures.getValue().getCode());
            }
        });

        NameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> addedProductStringCellDataFeatures) {
                return new SimpleStringProperty(addedProductStringCellDataFeatures.getValue().getName());
            }
        });
    }
}
