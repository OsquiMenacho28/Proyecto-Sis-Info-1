package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PromptWindow extends GridPane {
	Stage stage;

	Scene scene;

	SesionAdmin ses1;
	SesionAtCl ses2;
	String FXMLname;
	PromptWindow origin;
	FXMLLoader loader;

	public PromptWindow(SesionAdmin ses1, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
		this.stage = stage;
		stage.setResizable(true);
		this.ses1 = ses1;
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
	
	public PromptWindow(SesionAtCl ses2, Stage stage, String FXMLname, PromptWindow origin) throws IOException {
		this.stage = stage;
		stage.setResizable(true);
		this.ses2 = ses2;
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

	public PromptWindow(SesionAdmin ses1, String FXMLname, PromptWindow origin) throws IOException {
		this.ses1 = ses1;
		this.FXMLname = FXMLname;
		this.origin = origin;

		this.loader = new FXMLLoader(this.getClass().getResource(FXMLname));
		loader.setRoot(this);
		loader.setController(this);


		createWindow();
		stage.show();
	}
	
	public PromptWindow(SesionAtCl ses2, String FXMLname, PromptWindow origin) throws IOException {
		this.ses2 = ses2;
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
		if (origin != null) {
			origin.show();
		}
	}

	/*public void back() {
		stage.hide();
		if (origin != null) {
			ses1.show(origin);
		}
	}*/
}

