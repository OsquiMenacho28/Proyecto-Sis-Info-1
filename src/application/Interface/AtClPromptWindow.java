package application.Interface;

import application.FlowController.Sesion;
import application.FlowController.SesionAtCl;
import javafx.stage.Stage;

import java.io.IOException;

public class AtClPromptWindow extends PromptWindow {


    protected SesionAtCl sesion;

    public AtClPromptWindow(Sesion ses, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, FXMLname, origin);
    }

    public AtClPromptWindow(Sesion ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
        super(ses, stage, FXMLname, origin);
    }

    public AtClPromptWindow(Sesion ses, Stage stage, String FXMLname, PromptWindow origin, String title) throws IOException {
        super(ses, stage, FXMLname, origin, title);
    }

    public AtClPromptWindow(Sesion ses, String FXMLname, PromptWindow origin, String title) throws IOException {
        super(ses, FXMLname, origin, title);
    }
}
