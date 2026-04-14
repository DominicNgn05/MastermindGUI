/**
 * 
 * @author Khanh Nguyen
 * 
 * This file implements the GUI of the game of Mastermind
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
public class MastermindGUIView extends Application {

		@Override
		public void start(Stage stage) throws Exception {
			GameEngine gameEngine = new GameEngine();

			stage.setTitle("Mastermind");
			BorderPane window = new BorderPane();
			// "MASTERMIND TITLE
			BorderPane title = new BorderPane();
			Label label = new Label("MASTERMIND");
			label.setFont(new Font(30));
			title.setCenter(label);
			window.setTop(title);
			GuessArea guessArea = new GuessArea(gameEngine);
			
			// centering wrapper
			StackPane guessAreaWrap = new StackPane();
			guessAreaWrap.setAlignment(Pos.TOP_CENTER);
			guessAreaWrap.getChildren().add(guessArea);
			
			// scrolling wrapper
			ScrollPane guessAreaScroll = new ScrollPane(guessAreaWrap);
			guessAreaScroll.setFitToWidth(true);
			
			//Spec specified preferred height
			guessArea.setPrefHeight(500);
			
			StackPane mainboard = new StackPane(guessAreaScroll);
			mainboard.setAlignment(Pos.TOP_CENTER);
			window.setCenter(mainboard);
			
			// The buttons
			ControlPanel controlpanel = new ControlPanel(guessArea, gameEngine);
			window.setBottom(controlpanel);
			Scene scene = new Scene(window, 700, 500);
			stage.setScene(scene);

			stage.show();
			
			
			
		}

}