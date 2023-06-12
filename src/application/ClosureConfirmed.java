package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClosureConfirmed extends PromptWindow implements Initializable {

    @FXML
    private Button BackButton;

    @FXML
    private Button CloseButton;

    @FXML
    private ProgressBar progressBar1;

    @FXML
    private ProgressBar progressBar2;

    public ClosureConfirmed(SesionAdmin ses2, PromptWindow origin) throws Exception {
        super(ses2, "ClosureConfirmed.fxml", origin);
        this.load();
        start(stage);
    }

    /*public void init() throws Exception {
        // Configurar el preloader
        preloaderStage = new Stage();
        progressBar = new ProgressBar();
        BorderPane root = new BorderPane(progressBar);
        Scene scene = new Scene(root, 300, 150);
        preloaderStage.setScene(scene);
        preloaderStage.show();
    }*/

    public void start(Stage primaryStage) throws Exception {
        // Configurar la escena principal de tu aplicación
        // ...

        // Simular un progreso en la barra de progreso
        double progress = 0.0;
        while (progress < 1.0) {
            progress += 0.01;
            progressBar1.setProgress(progress);
            Thread.sleep(50);
        }

        // Cerrar la pantalla de carga
        stage.hide();

        // Mostrar la escena principal
        origin.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackButton.setOnAction(actionEvent -> {
            back();
        });
    }
}
