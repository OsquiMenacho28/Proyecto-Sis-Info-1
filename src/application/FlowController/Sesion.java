package application.FlowController;

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

    protected ObservableList<Sale> sales = FXCollections.observableArrayList();
    protected ObservableList<Client> clients = FXCollections.observableArrayList();
    protected ObservableList<Product> products = FXCollections.observableArrayList();

    protected BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public Sesion(User InputUser) {
        this.LogedUser = InputUser;
    }

    public void showMain() {
        mainWindow.show();
    }

    public void updateProducts(ObservableList<Product> products) {this.products = products;}
    public void updateClients(ObservableList<Client> clients) {
        this.clients = clients;
    }

    public void updateSales(ObservableList<Sale> sales) {
        this.sales = sales;
    }
}
