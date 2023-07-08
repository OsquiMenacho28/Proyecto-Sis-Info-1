package application.FlowController;

import DataBaseManager.DBManager;
import InventoryModel.Inventory;
import InventoryModel.Product;
import SalesModel.ClientsList;
import SalesModel.SalesList;
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
    protected SalesList salesList;
    protected ClientsList clientsList;

    protected BoxBlur blurEffect = new BoxBlur(10, 10, 3);

    public Sesion(User InputUser) throws Exception {

        this.LogedUser = InputUser;
        this.manager = new DBManager("jdbc:mysql://localhost:3306/ferreteria_dimaco_database", "root", "root");
        this.inventory = new Inventory(manager);
        this.salesList = new SalesList(manager);
        this.clientsList = new ClientsList(manager);
    }

    public void showMain() {
        mainWindow.show();
    }

    public Inventory getInventory(){
        return inventory;
    }

    public SalesList getSalesList(){
        return salesList;
    }

    public ClientsList getClientsList(){
        return clientsList;
    }

}
