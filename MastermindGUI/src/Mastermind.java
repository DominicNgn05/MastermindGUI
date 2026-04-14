/**
 * 
 * @author Khanh Ngoc Nguyen
 *	
 *This file Implements the gameplay loop of the game of Mastermind
 *The UI is entirely text-based in the console.
 *
 *The implementation allows for infinite replayability.
 *
 */
import java.util.Scanner;

import javafx.application.Application;

public class Mastermind {
	
	public static void main(String[] args) {
		String mode = args[0];
		// ____________________ Text Based Mode ____________________-
		if (mode.equals("-text")) {
		System.out.println("Welcome to Mastermind!\n"
				+ "You will guess a 4-color sequence randomly generated from\n"
				+ "Red, Orange, Yellow, Green, Blue, Purple!\n");
		String command="";
		Scanner keyboardScanner = new Scanner(System.in);
		GameEngine gameEngine = new GameEngine();
		System.out.println("To make your guess, type the initials of the color without whitespace");
		
		//Game Begins
		boolean playing = true;
		
		while (playing) {
			while(!gameEngine.finished()) {
			command = keyboardScanner.next().toUpperCase();
			while (!(gameEngine.isLegalGuess(command))) {
				System.out.println("Illegal guess. Make sure to guess only the legal colors,"
						+ " and remove all whitespace\nTry again!");
				command=keyboardScanner.next().toUpperCase();
			}
			gameEngine.gameLoop(command);
			}
			
			//game finished, prompt User for another round.
			System.out.println("Go next? [YES] to continue or anything else to exit");
			command = keyboardScanner.next();
			if ((command.toUpperCase().equals("YES"))) {
			gameEngine = new GameEngine();
			System.out.println("To make your guess, type the initials of the color without whitespace");
			}
			else {
					System.out.println("Program Exit!");
					playing=false;
			}	
		}
		keyboardScanner.close();
		}
		// ______________ Window Based Mode _______________
		if (mode.equals("-window")) {
			Application.launch(MastermindGUIView.class, args);
		}
	}

}
