package application.Interface;

import application.FlowController.Sesion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PromptWindow extends GridPane {
	protected Stage stage;

	protected Scene scene;

	protected Sesion sesion;
	protected String FXMLname;
	protected PromptWindow origin;
	protected FXMLLoader loader;


	public PromptWindow(Sesion ses, String FXMLname, PromptWindow origin) throws IOException {
		this.sesion = ses;
		this.FXMLname = FXMLname;
		this.origin = origin;

		this.loader = new FXMLLoader(this.getClass().getResource(FXMLname));
		loader.setRoot(this);
		loader.setController(this);

		createWindow();
	}
	public PromptWindow(Sesion ses, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
		this(ses, FXMLname, origin);
		this.stage = stage;
		this.scene = new Scene(this);
		stage.setScene(scene);
	}


	public PromptWindow(Sesion ses, Stage stage, String FXMLname, PromptWindow origin, String title) throws IOException {
		this(ses, stage, FXMLname, origin);
		stage.setTitle(title);
	}

	public PromptWindow(Sesion ses, String FXMLname, PromptWindow origin, String title) throws IOException {
		this(ses, FXMLname, origin);
		stage.setTitle(title);
	}


	public void load() throws IOException {
		loader.load();
		stage.centerOnScreen();
	}

	public void restart() throws IOException{
		loader.load();
	}

	public void createWindow(){
		this.stage = new Stage();
		this.scene = new Scene(this);
		stage.setScene(scene);

		//stage.sizeToScene();
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
		stage.close();
		if (origin != null) {
			origin.show();
		}
	}

	public void back() {
		stage.hide();
		if (origin != null) {
			origin.show();
		}
	}

	public Stage getStage(){
		return this.stage;
	}
}

