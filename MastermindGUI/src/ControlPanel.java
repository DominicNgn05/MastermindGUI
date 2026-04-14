/**
 * @author Khanh Nguyen
 * 
 * This file implements the buttons to submit a guess, delete last guess, and reset the game
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ControlPanel extends BorderPane{
	private GuessArea guessArea;
	private GameEngine engine;
	private class CommandPanel extends GridPane{
		private Button submitButton;
		private Button removeButton;
		private Button restartButton;
		public CommandPanel() {
			super();
			//submit Button
			submitButton = new Button();
			submitButton.setText("Submit Guess");
			submitButton.setOnAction((event) ->{
				guessArea.makeGuess();
			});
			//remove Button
			removeButton = new Button();
			removeButton.setText("Remove Last Peg");
			removeButton.setOnAction((event) ->{
				guessArea.removeLast();
			});
			//restart Button
			restartButton = new Button();
			restartButton.setText("Restart");
			restartButton.setOnAction((event) ->{
				guessArea.reset();
				engine.reset();
			});
			add(submitButton, 0, 0);
			add(removeButton, 1, 0);
			add(restartButton, 2, 0);
			setAlignment(Pos.TOP_CENTER);
		}
	}
	
	////////////////////////
	private class ColorButton extends StackPane{
		private Color color;
		private class GuessButton extends Button{
			private class GuessButtonHandler implements EventHandler<ActionEvent>{
				
				public GuessButtonHandler() {
					super();
				}
				@Override
				public void handle(ActionEvent event) {
					guessArea.addColortoActive(color);
				}
				
			}
			
			public GuessButton(int size) {
				super();
				this.setMaxSize(size, size);
				this.setMinSize(size, size);
				this.setPrefSize(size, size);
				Circle circle = new Circle(size/2);
				this.setShape(circle);
				this.setOpacity(0);
				EventHandler<ActionEvent> handler = new GuessButtonHandler();
				this.setOnAction(handler);
			}
		}
		private GuessButton button;
		public ColorButton(Color color, int size) {
			super();
			this.color=color;
			this.button = new GuessButton(size);
			Circle circle = new Circle(size/2);
			circle.setFill(color);
			this.getChildren().add(circle);
			this.getChildren().add(button);
			
		}
	}
	/////////////////////////
	
	private class GuessPanel extends GridPane{
		public GuessPanel() {
			super();
			setHgap(10);
			this.add(new ColorButton(Color.RED,50), 0, 0);
			this.add(new ColorButton(Color.BLUE,50), 1, 0);
			this.add(new ColorButton(Color.GREEN,50), 2, 0);
			this.add(new ColorButton(Color.YELLOW,50), 3, 0);
			this.add(new ColorButton(Color.ORANGE,50), 4, 0);
			this.add(new ColorButton(Color.PURPLE,50), 5, 0);
			setAlignment(Pos.TOP_CENTER);
		}
	}
	

	public ControlPanel(GuessArea guessArea, GameEngine engine) {
		super();
		this.engine = engine;
		this.setTop(new GuessPanel());
		this.setBottom(new CommandPanel());
		this.guessArea = guessArea;
	}
}
