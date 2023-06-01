package application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SesionAdmin {

    User LogedUser;
    Stage Window;
    Scene scene;

    public SesionAdmin(User inputUser, LogInAdmin x) throws Exception {
        //this.DataBase = new DBManager("jdbc:mysql://localhost:2808/ferreteria_dimaco_database", "root", "osquimenacho28");
        this.LogedUser = inputUser;
        this.Window = new Stage();

        InventoryManagement inventory = new InventoryManagement(this, x);
        inventory.show();
    }
}
