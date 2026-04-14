/**
 * @author Khanh Ngoc Nguyen
 * 
 * This file Implements class GameEngine to execute the Logic Backend for the game of Mastermind
 * 
 * 
 */
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class MastermindIlleagalColorException extends Exception{
	public MastermindIlleagalColorException(String message) {
		super(message);
	}
}
class MastermindIllegalLengthException extends Exception{
	public MastermindIllegalLengthException (String message) {
		super(message);
	}
}

public class GameEngine {
	/*
	 * Class GameEngine contains the logic for the game of Mastermind
	 * Generates color sequence randomly, and store the color sequence via ArrayList
	 * Also generates a HashMap to keep count of each color occurrence in the sequence.
	 */
	private HashMap<String, Integer> colorCount;
	private ArrayList<String> colorPos;
	private final ArrayList<String> colors =new ArrayList<>( Arrays.asList("R", "O", "Y", "G", "B", "P")); 
	
	private int guessCount=0;
	private boolean winFlag=false;
	private boolean guessLegalFlag = false;
	
	public GameEngine () {
		/*
		 * the Constructor initiates a GameEngine object
		 * Generates a random 4-letter sequence to represents the colors
		 */
		HashMap<String, Integer> colorCount = new HashMap<>();
		ArrayList<String> colorPos = new ArrayList<>();
		Random rng = new Random();
		
		for (int i =0; i<4; i++) {
			String curr = colors.get(rng.nextInt(6));
			colorPos.add(curr);			//populate color list 
			if(colorCount.containsKey(curr)) {	// populate color count hashmap
				colorCount.put(curr, colorCount.get(curr) +1);
			}
			else {
				colorCount.put(curr,1);
			}
		
		
			
		}
		this.colorCount = colorCount;
		this.colorPos = colorPos;
		
	}
	
	public void win() {
		winFlag=true;
	}
	
	public GameEngine (int[] sequence) {
		/*
		 * the debug Constructor initiates a GameEngine object
		 * Generates a 4-color sequence determined by in the argument 
		 */
		HashMap<String, Integer> colorCount = new HashMap<>();
		ArrayList<String> colorPos = new ArrayList<>();
		
		for (int i =0; i<4; i++) {
			String curr = colors.get(sequence[i]);
			colorPos.add(curr);			//populate color list 
			if(colorCount.containsKey(curr)) {	// populate color count hashmap
				colorCount.put(curr, colorCount.get(curr) +1);
			}
			else {
				colorCount.put(curr,1);
			}
		
		
			
		}
		this.colorCount = colorCount;
		this.colorPos = colorPos;
		
	}
	
	public boolean checkCorrect(String guess) {
		/*
		 * returns true if the guess is correct
		 * returns false if the guess is incorrect
		 */
		if (checkPos(guess)==4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
	public boolean checkLegalGuess(String guess) throws MastermindIllegalLengthException, MastermindIlleagalColorException{
		/*
		 * throws an exception if the guess is illegal
		 * returns true if the guess is legal
		 */
		for (int i=0; i<4;i++) {
			String curr = String.valueOf(guess.charAt(i));
			if (!(colors.contains(curr))) {
				throw new MastermindIlleagalColorException("illegal guess color");
			}
		}
		if (guess.length()!=4) {
			throw new MastermindIllegalLengthException("illegal guess length");
		}
		return true;
		
	}
	public int checkPos(String guess) {
		/*
		 * checkPos (String) takes in the guess of the player in the form of a String
		 * and return the number of colors correctly guessed in the correct position
		 * 
		 * Args: String guess represents the guess of the player
		 * 
		 * Return: int the number of perfect guesses
		 */
		int ret=0;
		for (int i=0; i<4; i++) {
			String curr = String.valueOf(guess.charAt(i));
			if (curr.equals(colorPos.get(i))) {	//+1 if the color and position matches
				ret+=1;		
			}
		}
		return ret;
	}
	
	public int checkCount (String guess) {
		/*
		 * checkPos (String) takes in the guess of the player in the form of a String
		 * and return the number of colors correctly guessed, regardless of positioning
		 * 
		 * Args: String guess represents the player's guess
		 * 
		 * Return: int the number of correct color guesses
		 */
		int ret= 0;
		HashMap<String, Integer> guessColorCount = new HashMap<>();
		for (int i=0; i<4; i++) {	// populate HashMap of guessed color
			String curr = String.valueOf(guess.charAt(i));
			if (guessColorCount.containsKey(curr)) {
				guessColorCount.put(curr, guessColorCount.get(curr) +1);
			}
			else {
				guessColorCount.put(curr, 1);
			}
		}
		for (String k: guessColorCount.keySet()) {	// compare guessed color with the color sequence
			if (colorCount.containsKey(k)) {
				ret+=Math.min(guessColorCount.get(k), colorCount.get(k));	//return the min value if there is a color match
			}
		}
		return ret;
	}
	public int checkWrongPos(String guess) {
		/*
		 * returns the number of correct color but wrong position of the guess
		 */
		return checkCount(guess)-checkPos(guess);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean finished() {
		/*
		 * returns true if the current game should end
		 */
		return ((guessCount>=10) || winFlag);
	}
	public boolean isLegalGuess(String guess) {
			guessLegalFlag = false;
		// error detection
			try {
				checkLegalGuess(guess);
				guessLegalFlag=true;
				return guessLegalFlag;
			} catch (Exception e) {
				guessLegalFlag=false;
				return guessLegalFlag;
	}
	}
	
	public void gameLoop (String guess){
		/*
		 * This method implements the gameplay loop
		 * 
		 */
				int correctPos= checkPos(guess);
				int correctColor= checkWrongPos(guess);
				
				if (checkCorrect(guess)) {	// all 4 color correctly guessed, player wins
					winFlag=true;
				}
				else {
					guessCount+=1;
						
					// print out info of the latest guess
					System.out.println("# Correct position: " + correctPos
							+"\n# Correct color but wrong position: "+ correctColor
							+"\n"+ (10-guessCount)+" guess left");
				}

		
		if (winFlag) {	// Print Win Message
			System.out.println("Congrats, you found the correct sequence after " + (guessCount+1) +" tries!");
		}
		else {	// Print Lost Message
			System.out.println("Better luck next time!");
		}
	}
	
	public void reset() {
		HashMap<String, Integer> colorCount = new HashMap<>();
		ArrayList<String> colorPos = new ArrayList<>();
		Random rng = new Random();
		
		for (int i =0; i<4; i++) {
			String curr = colors.get(rng.nextInt(6));
			colorPos.add(curr);			//populate color list 
			if(colorCount.containsKey(curr)) {	// populate color count hashmap
				colorCount.put(curr, colorCount.get(curr) +1);
			}
			else {
				colorCount.put(curr,1);
			}
	
		}
		this.colorCount = colorCount;
		this.colorPos = colorPos;
		winFlag=false;
		guessCount=0;
		guessLegalFlag=false;
		
	}
}

