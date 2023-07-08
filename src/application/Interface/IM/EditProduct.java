package application.Interface.IM;

import InventoryModel.Product;
import application.Interface.AdminPromptWindow;
import application.Interface.PromptWindow;
import application.FlowController.SesionAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditProduct extends AdminPromptWindow implements Initializable {

    @FXML
    Button Back_B;

    @FXML
    Button Edit_B;

    @FXML
    TextField ProductCodeField;

    @FXML
    TextField ProductNameField;

    @FXML
    TextField ProductBrandField;

    @FXML
    TextField ProductPriceField;

    @FXML
    TextField ProductColorField;

    @FXML
    TextField ProductCategoryField;

    @FXML
    TextField ProductQuantityField;

    @FXML
    TextArea ProductDescriptionArea;

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    private Product product;

    public EditProduct(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "EditProduct.fxml", origin, "EDITAR PRODUCTO");
        stage.setWidth(672);
        stage.setHeight(570);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> {
            origin.setEffect(null);
        });

        ProductNameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                ProductDescriptionArea.requestFocus();
            }
        });
        ProductDescriptionArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                ProductNameField.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                ProductCodeField.requestFocus();
            }
        });
        ProductCodeField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                ProductDescriptionArea.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                ProductColorField.requestFocus();
            }
        });
        ProductColorField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                ProductCodeField.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                ProductBrandField.requestFocus();
            }
        });
        ProductBrandField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                ProductColorField.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                ProductCategoryField.requestFocus();
            }
        });
        ProductCategoryField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                ProductBrandField.requestFocus();
            }
            if (event.getCode() == KeyCode.DOWN) {
                ProductPriceField.requestFocus();
            }
        });
        ProductPriceField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                ProductCategoryField.requestFocus();
            }
        });

        Back_B.setOnAction(actionEvent -> back());

        Edit_B.setOnAction(actionEvent -> {
            if(this.product != null){
                if(this.product.isLinked()){
                    try {
                        update();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("EDITAR PRODUCTO");
                    alert.setHeaderText("¡EXITO!");
                    alert.setContentText("El producto " + ProductNameField.getText() + " se actualizó correctamente");
                    alert.initStyle(StageStyle.DECORATED);
                    stage.getScene().getRoot().setEffect(blurEffect);
                    alert.showAndWait();
                    back();
                }
            }

        });
    }

    private Boolean v(String x) {
        return (x != null) && !x.equals("");
    }

    private void update() throws Exception {

        String name = ProductNameField.getText();
        String description = ProductDescriptionArea.getText();
        String code = ProductCodeField.getText();
        String color = ProductColorField.getText();
        String brand = ProductBrandField.getText();
        String category = ProductCategoryField.getText();
        String price = ProductPriceField.getText();

        if(v(name) && v(description) && v(code) && v(color) && v(brand) && v(category) && v(price)) {
            product.setName(name);
            product.setDescription(description);
            product.setCode(Integer.parseInt(code));
            //product.setColor(color);
            //product.setBrand(brand);
            //product.setCategory(category);
            product.setPrice(Float.parseFloat(price));
        }
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public void clear(){
        this.product = null;
    }
}
