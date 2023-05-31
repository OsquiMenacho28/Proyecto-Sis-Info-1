package application;

import java.io.IOException;

public class Prueba extends PromptWindow{
	public Prueba(Sesion ses,PromptWindow origin) throws IOException{
		super(ses, "C:\\Users\\Andre\\Desktop\\Prueba.fxml", origin);
		load();
		//stage.initStyle(StageStyle.DECORATED);
		stage.show();
	}
	
	
	
}
