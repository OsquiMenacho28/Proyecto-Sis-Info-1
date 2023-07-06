package application.Interface;

import application.FlowController.Sesion;
import application.FlowController.SesionAtCl;
import javafx.stage.Stage;

import java.io.IOException;

public class AtClPromptWindow extends PromptWindow {


    protected SesionAtCl sesion;

    public AtClPromptWindow(SesionAtCl ses, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, FXMLname, origin);
        this.sesion = ses;
    }

    public AtClPromptWindow(SesionAtCl ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, stage, FXMLname, origin);
        this.sesion = ses;
    }

    public AtClPromptWindow(SesionAtCl ses, Stage stage, String FXMLname, PromptWindow origin, String title) throws IOException {
        super(ses, stage, FXMLname, origin, title);
        this.sesion = ses;
    }

    public AtClPromptWindow(SesionAtCl ses, String FXMLname, PromptWindow origin, String title) throws IOException {
        super(ses, FXMLname, origin, title);
        this.sesion = ses;
    }
}
