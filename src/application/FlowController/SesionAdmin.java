package application.FlowController;

import application.Interface.IM.*;
import application.Interface.LI.SelectAccount;
import application.Interface.PromptWindow;
import application.Interface.generic.Notifications;
import application.Interface.generic.Sales;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class SesionAdmin extends Sesion {

    private InventoryManagement InventoryManagement;
    private AddProd AddProd;
    private DeleteProduct DeleteProduct;
    private EditProduct EditProduct;
    private EntryConfirmed EntryConfirmed;
    private EntryProduct EntryProduct;
    private Metrics Metrics;
    private RemoveConfirmed RemoveConfirmed;
    private Sales Sales;
    private Notifications Notifications;

    public SesionAdmin(User InputUser) throws Exception {
        super(InputUser);
        this.InventoryManagement = new InventoryManagement(this, new SelectAccount());
        super.mainWindow = this.InventoryManagement;
        this.InventoryManagement.hide();

        this.AddProd = new AddProd(products, this, this.InventoryManagement);
        this.AddProd.hide();

        this.DeleteProduct = new DeleteProduct(this, this.InventoryManagement);
        this.DeleteProduct.hide();

        this.EditProduct = new EditProduct(this, this.InventoryManagement);
        this.EditProduct.hide();

        this.EntryConfirmed = new EntryConfirmed(this, this.EntryProduct);
        this.EntryConfirmed.hide();

        this.EntryProduct = new EntryProduct(this, this.InventoryManagement);
        this.EntryProduct.hide();

        this.Metrics = new Metrics(this, this.InventoryManagement);
        this.Metrics.hide();

        this.RemoveConfirmed = new RemoveConfirmed(this, this.EntryProduct);
        this.RemoveConfirmed.hide();

        this.Sales = new Sales(this, this.InventoryManagement);
        this.Sales.hide();

        this.Notifications = new Notifications(this, this.InventoryManagement);
        this.Notifications.hide();

        this.InventoryManagement.show();
    }

    public void editProductRequest() {
        setBlurEffectToStage();
        EditProduct.show();
    }

    public void deleteProductRequest() {
        setBlurEffectToStage();
        DeleteProduct.show();
    }

    public void entryProductRequest() {
        setBlurEffectToStage();
        EntryProduct.show();
    }

    public void entryConfirmedRequest() {
        EntryConfirmed.show();
    }

    public void removeConfirmedRequest() {
        RemoveConfirmed.show();
    }

    public void notificationsRequest() {
        setBlurEffectToStage();
        Notifications.show();
    }

    public void salesRequest() {
        setBlurEffectToStage();
        Sales.show();
    }

    public void addProductRequest() {
        setBlurEffectToStage();
        AddProd.show();
    }

    public void metricsRequest() {
        setBlurEffectToStage();
        Metrics.show();
    }

    public void logOutRequest() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CERRAR SESIÓN");
        alert.setHeaderText("¡AVISO!");
        alert.setContentText("¿Está seguro de Cerrar Sesión?");
        alert.initStyle(StageStyle.DECORATED);
        setBlurEffectToStage();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            InventoryManagement.hide();
            try {
                new SelectAccount();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            setNullEffectToStage();
        }
    }

    public void closeApplicationRequest(Event windowEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CERRAR APLICACIÓN");
        alert.setHeaderText("¡ADVERTENCIA!");
        alert.setContentText("Se cerrará la aplicación. ¿Desea continuar?");
        alert.initStyle(StageStyle.DECORATED);
        setBlurEffectToStage();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
        else {
            setNullEffectToStage();
            windowEvent.consume();
        }
    }

    public void show(PromptWindow origin) {
        origin.show();
    }

    private void setBlurEffectToStage() {
        InventoryManagement.setEffect(blurEffect);
    }

    private void setNullEffectToStage() {
        InventoryManagement.setEffect(null);
    }
}
