package application;

public class SesionAdmin extends Sesion {

    public SesionAdmin(User InputUser) throws Exception {
        super(InputUser);
        super.mainWindow = new InventoryManagement(this, null);
    }

    public void show(PromptWindow origin) {
        origin.show();
    }
}
