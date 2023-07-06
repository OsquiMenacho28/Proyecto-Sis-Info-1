package application.Interface;

import application.FlowController.Sesion;
import application.FlowController.SesionAdmin;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPromptWindow extends PromptWindow{

    protected SesionAdmin sesion;
    public AdminPromptWindow(SesionAdmin ses, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, FXMLname, origin);
        this.sesion =ses;
    }

    public AdminPromptWindow(SesionAdmin ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, stage, FXMLname, origin);
        this.sesion =ses;
    }

    public AdminPromptWindow(SesionAdmin ses, Stage stage, String FXMLname, PromptWindow origin, String title) throws IOException {
        super(ses, stage, FXMLname, origin, title);
        this.sesion =ses;
    }

    public AdminPromptWindow(SesionAdmin ses, String FXMLname, PromptWindow origin, String title) throws IOException {
        super(ses, FXMLname, origin, title);
        this.sesion =ses;
    }
}
