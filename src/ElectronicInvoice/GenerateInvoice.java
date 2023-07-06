package ElectronicInvoice;

import application.FlowController.SesionAtCl;
import application.Interface.AtClPromptWindow;
import application.Interface.PromptWindow;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateInvoice extends AtClPromptWindow {
    @FXML
    private GridPane loadingInvoiceGridPane;
    @FXML
    private ProgressIndicator invoiceProgressIndicator;
    @FXML
    private Label generatingInvoiceLabel;
    @FXML
    private Button openInvoiceButton;
    @FXML
    private Label countDownLabel;

    public GenerateInvoice(SesionAtCl ses, Stage stage, PromptWindow origin) throws Exception {
        super(ses, stage, "GenerateInvoice.fxml", origin);
        stage.setWidth(413);
        stage.setHeight(247);
        this.load();
        generateInvoice();
    }

    private void generateInvoice() throws Exception {
        Invoice invoiceGenerated = new Invoice(null, null);
        String invoiceFileName = "Factura" + Invoice.getInvoiceNumber() + ".pdf";
        String invoiceFilePath = "C:/Users/usuario/Downloads/" + invoiceFileName;
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
                    openInvoiceButton.setVisible(true);
                    openInvoiceButton.setOnAction(actionEvent -> {
                        File invoicePath = new File(invoiceFilePath);
                        try {
                            Desktop.getDesktop().open(invoicePath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    countDownLabel.setVisible(true);
                    Timer countDown = new Timer();
                    AtomicInteger seconds = new AtomicInteger(5);
                    int delay = 1000;
                    int period = 1000;
                    countDown.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                if (seconds.get() == 1) countDown.cancel();
                                countDownLabel.setText(String.valueOf(seconds.getAndDecrement()));
                            });
                        }
                    }, delay, period);
                });
                Timer showAndWait = new Timer();
                showAndWait.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            try {
                                sesion.getPOSSesion().clearCart();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            back();
                        });
                    }
                }, 6000);
            }).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
