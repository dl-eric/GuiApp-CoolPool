package edu.andover.coolpool;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GameManager {
	private Scene scene;

	private BorderPane rootLayout;


	public GameManager(Scene scene){
	    this.scene = scene;

	    rootLayout = new BorderPane();

	    scene.setRoot(rootLayout);
	}


	public void initStartScreen(Scene scene){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/MainMenuScreen.fxml"));
		try {
			Parent mainMenuScreen = (Parent) loader.load();
			rootLayout.setCenter(mainMenuScreen);
			scene.setRoot(rootLayout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//controller for start screen calls this when button is pressed
	public void initPoolScreen(){}

	public void initEndScreen(){}
}
