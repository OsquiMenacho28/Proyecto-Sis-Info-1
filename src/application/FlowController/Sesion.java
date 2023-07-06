package application.FlowController;

import DataBaseManager.DBManager;
import InventoryModel.Inventory;
import InventoryModel.Product;
import application.Interface.PromptWindow;
import SalesModel.Client;
import SalesModel.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.BoxBlur;

public class Sesion {
    protected PromptWindow mainWindow;

    protected User LogedUser;

    protected DBManager manager;
    protected Inventory inventory;

    protected BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public Sesion(User InputUser) {
        this.LogedUser = InputUser;
    }

    public void showMain() {
        mainWindow.show();
    }

}
