package ElectronicInvoice;

import application.PromptWindow;
import application.SesionAtCl;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class GenerateInvoice extends PromptWindow {
    @FXML
    private GridPane loadingInvoiceGridPane;
    @FXML
    private ProgressIndicator invoiceProgressIndicator;
    @FXML
    private Label generatingInvoiceLabel;

    public GenerateInvoice(SesionAtCl ses, PromptWindow origin) throws Exception {
        super(ses, "GenerateInvoice.fxml", origin);
        stage.setWidth(413);
        stage.setHeight(219);
        this.load();
        generateInvoice();
    }

    private void generateInvoice() throws Exception {
        Invoice invoiceGenerated = new Invoice(null, null);
        try {
            class Progress {
                double value = 0.0;
            }
            Progress progress = new Progress();

            Timeline textAnimation = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(generatingInvoiceLabel.textProperty(), "Generando Factura   ")),
                    new KeyFrame(Duration.seconds(1.0), new KeyValue(generatingInvoiceLabel.textProperty(), "Generando Factura.  ")),
                    new KeyFrame(Duration.seconds(1.5), new KeyValue(generatingInvoiceLabel.textProperty(), "Generando Factura.. ")),
                    new KeyFrame(Duration.seconds(2.0), new KeyValue(generatingInvoiceLabel.textProperty(), "Generando Factura..."))
            );
            textAnimation.setCycleCount(2);
            textAnimation.play();

            new Thread(() -> {
                while (progress.value < 1.0) {
                    progress.value += 0.01;
                    Platform.runLater(() -> invoiceProgressIndicator.setProgress(progress.value));
                    try {
                        Thread.sleep(40);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                Platform.runLater(() -> {
                    loadingInvoiceGridPane.setCursor(Cursor.DEFAULT);
                    generatingInvoiceLabel.setText("Factura N° " + Invoice.getInvoiceNumber() + " generada exitosamente!");
                });
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            origin.ses2.POSOpen.cart.clear();
                            back();
                        });
                    }
                }, 4000);
            }).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
