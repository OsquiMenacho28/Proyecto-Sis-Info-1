package application;

public class Sesion {
    protected PromptWindow mainWindow;

    protected User LogedUser;

    public Sesion(User InputUser) {
        this.LogedUser = InputUser;
    }

    public void showMain() {
        mainWindow.show();
    }
}
