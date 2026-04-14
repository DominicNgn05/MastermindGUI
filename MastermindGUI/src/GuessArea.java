/**
 * 
 * @author Khanh Nguyen
 * 
 * This file implements the GUI section that contains the guess log of the User
 * And the current active guess of the game of Mastermind
 * 
 */

import java.util.ArrayList;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GuessArea extends GridPane {
	private GameEngine engine;
	private boolean endFlag = false;
	private static final Map<Color, String> COLOR_NAMES = Map.ofEntries(
		    Map.entry(Color.RED, "R"),
		    Map.entry(Color.BLUE, "B"),
		    Map.entry(Color.GREEN, "G"),
		    Map.entry(Color.YELLOW, "Y"),
		    Map.entry(Color.PURPLE, "P"),
		    Map.entry(Color.ORANGE, "O")
		    );
		public class GuessLine extends GridPane{
			
			public class ResultBox extends GridPane{
				
				public ResultBox(int correctPosition, int wrongPosition) {
					super();
					setPadding(new Insets(4, 6, 4, 6));
					setVgap(3);
					setHgap(3);
					this.setBorder(
							new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
					Circle[] circles = new Circle[4];
					int count =0;
						for (int i = 0; i < correctPosition; i++) {
							circles[count] = new Circle(5);
							circles[count].setFill(Color.BLACK);
							circles[count].setStroke(Color.BLACK);
							count++;
						}
						for (int j = 0; j < wrongPosition; j++) {
							circles[count] = new Circle(5);
							circles[count].setFill(Color.WHITE);
							circles[count].setStroke(Color.BLACK);
							count++;
						}
						while (count <4) {
							circles[count] = new Circle(5);
							circles[count].setFill(Color.TRANSPARENT);
							count++;
						}
					int index = 0;
					for (Circle c : circles) {
						if (index < 2) {
							this.add(c, index, 0);
							index++;
						} else {
							this.add(c, index - 2, 1);
							index++;
						}
					}
				}
			}
			
			private int guessed;
			private ArrayList<Color> colors;
			public GuessLine(int number) {
				super();
				this.colors = new ArrayList<Color>();
				this.guessed = 0;
				setHgap(20);
				Text guessNumber = new Text(String.valueOf(number));
				guessNumber.setFont(new Font(20));
				this.add(guessNumber, 0, 0);
			}

			public void addColor(Color color) {
				if (guessed < 4) {
					Circle newCircle = new Circle(20);
					newCircle.setFill(color);
					this.add(newCircle, guessed + 1, 0); // col, row
					colors.add(color);
					guessed += 1;
				}
			}

			public void removeLast() {
				if (getChildren().size()>1) {
					this.getChildren().remove(getChildren().size() - 1);
					colors.removeLast();
					guessed--;
				}
			}

			public boolean isLegal() {
				return guessed == 4;
			}

			public void resolve() {
				String guessString = "";
				for (Color c: colors) {
					guessString+=COLOR_NAMES.get(c);
				}
				int correctPosition = engine.checkPos(guessString);
				int wrongPosition = engine.checkWrongPos(guessString);
				// TODO check correct guesses
				ResultBox result = new ResultBox(correctPosition, wrongPosition);
				this.add(result, 5, 0);
				
				if (correctPosition == 4) {
					Alert winAlert = new Alert(AlertType.INFORMATION);
					winAlert.setContentText("You Win, GG!!");
					winAlert.show();
					endFlag=true;
				}
				if (count>=10) {
					Alert loseAlert = new Alert(AlertType.INFORMATION);
					loseAlert.setContentText("You ran out of attempts, better luck next time");
					loseAlert.show();
					endFlag=true;
				}
			}
			
		}
		/////////////////////////////////////////////
		private int count=0;
		private final int MAX_ATTEMPT = 10;
		private GuessLine activeLine = null;
		public GuessArea(GameEngine engine) {
			super();
			this.engine = engine;
			setVgap(20);
			setAlignment(Pos.TOP_CENTER);
			startGuess();
		}
		
		public void startGuess() {
			if (count >= MAX_ATTEMPT) {
				activeLine = null;
				return;
			}
			activeLine = new GuessLine(count);
			add(activeLine, 0, count);
		}
		
		/**
		 * activates when user presses Submit
		 * @param guess
		 */
		public void makeGuess() {
			if (activeLine!=null && activeLine.isLegal()) {
				count++;
				activeLine.resolve();
				if (endFlag) {
					brick();
				}
				else {
				startGuess();
			}
			}
		}
		
		private void brick() {
			activeLine = null;
			count = 999; 
		}
		
		public void addColortoActive(Color color) {
			if (activeLine == null) {
				return;
			}
			activeLine.addColor(color);
		}
		
		public void removeLast() {
			if (activeLine == null) {
				return;
			}
			activeLine.removeLast();
		}
		public void reset() {
			getChildren().clear();
			count=0;
			activeLine=null;
			startGuess();
			endFlag=false;
			
		}
}
