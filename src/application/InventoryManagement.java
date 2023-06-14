package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryManagement extends PromptWindow implements Initializable {

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

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public InventoryManagement(SesionAdmin ses, PromptWindow origin) throws IOException {
        super(ses, "InventoryManagement.fxml", origin);
        super.stage.setMaximized(true);
        super.stage.setTitle("GESTIÓN DE INVENTARIO");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stage.setOnCloseRequest(windowEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CERRAR APLICACIÓN");
            alert.setHeaderText("¡ADVERTENCIA!");
            alert.setContentText("Se cerrará la aplicación. ¿Desea continuar?");
            alert.initStyle(StageStyle.DECORATED);
            stage.getScene().getRoot().setEffect(blurEffect);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
            else {
                stage.getScene().getRoot().setEffect(null);
                windowEvent.consume();
            }
        });

        Notification_B.setOnAction(actionEvent -> {
            stage.getScene().getRoot().setEffect(blurEffect);
            try {
                Notifications notifications = new Notifications((SesionAtCl) null,this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        LightMode_Opt.setOnAction(actionEvent -> {

        });

        DarkMode_Opt.setOnAction(actionEvent -> {

        });

        Close_B.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CERRAR SESIÓN");
            alert.setHeaderText("¡AVISO!");
            alert.setContentText("¿Está seguro de Cerrar Sesión?");
            alert.initStyle(StageStyle.DECORATED);
            stage.getScene().getRoot().setEffect(blurEffect);
            Optional <ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                dispose();
                try {
                    new SelectAccount();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                stage.getScene().getRoot().setEffect(null);
            }
        });

        Sales_Opt.setOnAction(actionEvent -> {
            stage.getScene().getRoot().setEffect(blurEffect);
            try {
                Sales sales = new Sales((SesionAdmin) null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        AddProduct_Opt.setOnAction(actionEvent -> {
            stage.getScene().getRoot().setEffect(blurEffect);
            try {
                AddProd addProd = new AddProd(products, null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        EditProduct_B.setOnAction(actionEvent -> {
            stage.getScene().getRoot().setEffect(blurEffect);
            try {
                EditProduct editProduct = new EditProduct(null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

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

        DeleteProduct_B.setOnAction(actionEvent -> {
            stage.getScene().getRoot().setEffect(blurEffect);
            try {
                DeleteProduct deleteProduct = new DeleteProduct(null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        EntryProduct_B.setOnAction(actionEvent -> {
            stage.getScene().getRoot().setEffect(blurEffect);
            try {
                EntryProduct entryProduct = new EntryProduct(null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        /*Metrics_B.setOnAction(actionEvent -> {
            stage.getScene().getRoot().setEffect(blurEffect);
            try {
                Metrics metrics = new Metrics(null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });*/

        Back_B.setOnAction(e -> {
            try {
                origin = new SelectAccount();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            back();
        });

        ListScroll.setFitToWidth(true);
        //ListScroll.setFitToHeight(true);
    }
}
