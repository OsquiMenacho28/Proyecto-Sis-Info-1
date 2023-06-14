package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Metrics extends PromptWindow implements Initializable {

    @FXML
    private LineChart <Integer, Integer> MetricsChart;

    @FXML
    private DatePicker InitialDate;

    @FXML
    private DatePicker FinalDate;

    @FXML
    private Label MinStock;

    @FXML
    private Label DeliveryTime;

    @FXML
    private Label ActualInventoryLevel;

    @FXML
    private Label ReorderDate;

    @FXML
    private Button Back_B;

    public Metrics(SesionAdmin ses1, PromptWindow origin) throws IOException {
        super(ses1, "Metrics.fxml", origin);
        stage.setTitle("MÉTRICAS - FERRETERÍA DIMACO");
        this.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> origin.stage.getScene().getRoot().setEffect(null));

        Back_B.setOnAction(actionEvent -> back());
    }
}
