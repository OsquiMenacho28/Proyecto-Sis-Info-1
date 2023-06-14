package application;

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

public class EditProduct extends PromptWindow implements Initializable {

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

    ObservableList<Product> products = FXCollections.observableArrayList();

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public EditProduct(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "EditProduct.fxml", origin);
        stage.setTitle("EDITAR PRODUCTO");
        stage.setWidth(672);
        stage.setHeight(570);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> {
            origin.stage.getScene().getRoot().setEffect(null);
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
            try {
                update();
            } catch (SQLException e) {
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
        });
    }

    private Boolean v(String x) {
        return (x != null) && !x.equals("");
    }

    private void update() throws SQLException {

        String name = ProductNameField.getText();
        String description = ProductDescriptionArea.getText();
        String code = ProductCodeField.getText();
        String color = ProductColorField.getText();
        String brand = ProductBrandField.getText();
        String category = ProductCategoryField.getText();
        String price = ProductPriceField.getText();

        if(v(name) && v(description) && v(code) && v(color) && v(brand) && v(category) && v(price)) {
            Product aux = new Product(Integer.parseInt(code), 0, name, description, color, brand, category, Float.parseFloat(price));
            products.add(aux);
            ses2.updateProducts(products);
        }
    }
}
