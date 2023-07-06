package application.Interface.IM;

import InventoryModel.Product;
import application.FlowController.SesionAdmin;
import application.Interface.AdminPromptWindow;
import application.Interface.PromptWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagement extends AdminPromptWindow implements Initializable {

    @FXML
    private ScrollPane ListScroll;

    @FXML
    private Button Notification_B;

    @FXML
    private Button Mode_B;

    @FXML
    private MenuItem LightMode_Opt;

    @FXML
    private MenuItem DarkMode_Opt;

    @FXML
    private Button Close_B;

    @FXML
    private MenuButton Menu_B;

    @FXML
    private MenuItem Sales_Opt;

    @FXML
    private MenuItem AddProduct_Opt;

    @FXML
    private Button Back_B;

    @FXML
    private Button EditProduct_B;

    @FXML
    private Button EnableProduct_B;

    @FXML
    private Button DeleteProduct_B;

    @FXML
    private Button EntryProduct_B;

    @FXML
    private Button CancelProduct_B;

    @FXML
    private Button Metrics_B;

    public ObservableList<Product> products = FXCollections.observableArrayList();

    int clickCount = 0;
    boolean isButtonEnabled = false;

    public InventoryManagement(SesionAdmin ses, PromptWindow origin) throws IOException {
        super(ses, "InventoryManagement.fxml", origin, "GESTIÓN DE INVENTARIO");
        super.stage.setMaximized(true);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stage.setOnCloseRequest(windowEvent -> sesion.closeApplicationRequest(windowEvent));

        Notification_B.setOnAction(actionEvent -> sesion.notificationsRequest());

        LightMode_Opt.setOnAction(actionEvent -> {

        });

        DarkMode_Opt.setOnAction(actionEvent -> {

        });

        Close_B.setOnAction(actionEvent -> sesion.logOutRequest());

        Sales_Opt.setOnAction(actionEvent -> sesion.salesRequest());

        AddProduct_Opt.setOnAction(actionEvent -> sesion.addProductRequest());

        EditProduct_B.setOnAction(actionEvent -> sesion.editProductRequest());

        EnableProduct_B.setOnMouseClicked(mouseEvent -> {
            clickCount++;
            if (clickCount % 2 == 1) {
                isButtonEnabled = true;
                EnableProduct_B.setStyle("-fx-background-color: black");
                EnableProduct_B.setFont(new Font(25));
                EnableProduct_B.setText("HABILITAR");
                //deshabilitar
            } else {
                isButtonEnabled = false;
                EnableProduct_B.setStyle("-fx-background-color: #ff8500");
                EnableProduct_B.setFont(new Font(21));
                EnableProduct_B.setText("DESHABILITAR");
                //habilitar
            }
        });

        DeleteProduct_B.setOnAction(actionEvent -> sesion.deleteProductRequest());

        EntryProduct_B.setOnAction(actionEvent -> sesion.entryProductRequest());

        Metrics_B.setOnAction(actionEvent -> sesion.metricsRequest());

        Back_B.setOnAction(actionEvent -> back());

        ListScroll.setFitToWidth(true);
        //ListScroll.setFitToHeight(true);
    }
}
