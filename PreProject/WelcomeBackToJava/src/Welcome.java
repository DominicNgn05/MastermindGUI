import java.util.Scanner;

public class Welcome {

	public static void main(String[] args) {
		System.out.println("Welcome back, it's been a while since you last touched Java. \nNow tell me, what do you need?");
		Scanner keyboard = new Scanner(System.in);
		String input = keyboard.next();
		System.out.println("I see, let's begin to " + input);
		keyboard.close();
	}

}
