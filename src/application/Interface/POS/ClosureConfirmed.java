package application.Interface.POS;

import application.FlowController.SesionAtCl;
import application.Interface.AtClPromptWindow;
import application.Interface.LI.SelectAccount;
import application.Interface.PromptWindow;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClosureConfirmed extends AtClPromptWindow implements Initializable {

    @FXML
    private Button CancelButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label LClosing;

    private boolean windowClosed = false;

    public ClosureConfirmed(SesionAtCl ses2, PromptWindow origin) throws Exception {
        super(ses2, "ClosureConfirmed.fxml", origin,"CIERRE DE CAJA");
        stage.setWidth(506);
        stage.setHeight(365);
        this.load();
    }

    private void initializeFXML() {
        try {
            class Progress {
                double value = 0.0;
            }

            Progress progress = new Progress();

            Timeline dotsAnimation = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(LClosing.textProperty(), "Cerrando Caja   ")),
                    new KeyFrame(Duration.seconds(1.0), new KeyValue(LClosing.textProperty(), "Cerrando Caja.  ")),
                    new KeyFrame(Duration.seconds(1.5), new KeyValue(LClosing.textProperty(), "Cerrando Caja.. ")),
                    new KeyFrame(Duration.seconds(2.0), new KeyValue(LClosing.textProperty(), "Cerrando Caja..."))
            );
            dotsAnimation.setCycleCount(Animation.INDEFINITE);
            dotsAnimation.play();

            new Thread(() -> {
                while (progress.value < 1.0 && !windowClosed) {
                    progress.value += 0.01;
                    Platform.runLater(() -> progressBar.setProgress(progress.value));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(() -> {
                    if (!windowClosed) {
                        Stage loadingStage = (Stage) progressBar.getScene().getWindow();
                        loadingStage.hide();
                        dispose();
                        sesion.end();
                    }
                });
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show(){
        initializeFXML();
        super.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(windowEvent -> {
            windowClosed = true;
            origin.setEffect(null);
        });

        CancelButton.setOnAction(actionEvent -> {
            windowClosed = true;
            back();
        });
    }
}