package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EntryProduct extends PromptWindow implements Initializable {

    @FXML
    private Label ProductCodeLabel;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private TextField ProductQuantity;

    @FXML
    private DatePicker ArriveDate;

    @FXML
    private ComboBox <String> EntryDescription;

    @FXML
    private Button Entry_B;

    @FXML
    private Button Remove_B;

    @FXML
    private Button Back_B;

    private BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public EntryProduct(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "EntryProduct.fxml", origin);
        stage.setTitle("REGISTRO O RETIRO DE MATERIAL");
        stage.setWidth(742);
        stage.setHeight(493);
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> origin.stage.getScene().getRoot().setEffect(null));

        Back_B.setOnAction(actionEvent -> back());

        Entry_B.setOnAction(actionEvent -> {
            stage.hide();
            try {
                EntryConfirmed entryConfirmed = new EntryConfirmed(null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Remove_B.setOnAction(actionEvent -> {
            stage.hide();
            try {
                RemoveConfirmed removeConfirmed = new RemoveConfirmed(null, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
