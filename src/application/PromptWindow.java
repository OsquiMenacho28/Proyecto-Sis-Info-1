package application;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PromptWindow extends GridPane {
	Stage stage;

	Scene scene;
	Sesion ses;
	String FXMLname;
	PromptWindow origin;
	FXMLLoader loader;
	
	public PromptWindow(Sesion ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
		this.stage = stage;
		stage.setResizable(true);
		this.ses = ses;
		this.FXMLname = FXMLname;
		this.origin = origin;
		
		this.loader = new FXMLLoader(this.getClass().getResource(FXMLname));
		loader.setRoot(this);
		loader.setController(this);
		
		scene = new Scene(this);
		this.stage.setScene(scene);
		//stage.sizeToScene();
		stage.show();
	}
	
	public PromptWindow(Sesion ses, String FXMLname, PromptWindow origin) throws IOException {
		this.ses = ses;
		this.FXMLname = FXMLname;
		this.origin = origin;
		
		this.loader = new FXMLLoader(this.getClass().getResource(FXMLname));
		loader.setRoot(this);
		loader.setController(this);
		
		
		createWindow();
		stage.show();
	}
	
	
	public void load() throws IOException {
		loader.load();
		stage.centerOnScreen();
	}
	
	public void createWindow(){
		this.stage = new Stage();
		this.scene = new Scene(this);
		stage.setScene(scene);
		//stage.sizeToScene();
		stage.setResizable(true);
	}
	
	public void dispose() {
		stage.close();
	}
	public void show() {

		scene.getRoot().requestLayout();
		stage.show();
	}
	public void hide() {stage.hide();}

	public void backDispose() {
		stage.hide();
		if(origin != null)
		{
			origin.show();
		}
	}

	public void back() {
		stage.hide();
		if(origin != null)
		{
			ses.show(origin);
		}
	}
}

