/**
 * @author Khanh Ngoc Nguyen
 * @course CSC345
 * @instructor Hudson Lynam 
 * @assignment Programming Project 3
 * @TA Anna Yami
 * @due March 26th, 3:30pm
 * 
 * The main file of Programming Project 3
 * Reads from an input file the information of Attacks
 * and report the final state after all Attacks have resolved.
 * 
 * 
 */import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class sorting {
	
	public static void printBySpeed(Attack[] list) {
		for (Attack a: list) {
			System.out.print(a.speed() + ", ");
		}
	}
	
	public static void printByDamage(Attack[] list) {
		for (Attack a: list) {
			System.out.print(a.damage() + ", ");
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
	File file = new File(args[0]);
	Scanner fileRead = new Scanner(file);
	int[] typeList = {0,0,0};
	while (fileRead.hasNextLine()) {
		String type = fileRead.nextLine().split(",")[0];
		switch (type) {
		case ("Lightning"): {typeList[0]+=1; break;}
		case ("Fire"): {typeList[1]+=1; break;}
		case ("Ice"): {typeList[2]+=1; break;}
		}
	}
	fileRead.close();
	int count = typeList[0]+ typeList[1] + typeList[2];
	Attack[] damageList = new Attack[count];
	Attack[] speedList = new Attack[count];
	int index = 0;
	fileRead = new Scanner(file);
	while (fileRead.hasNextLine()) {
		 Attack cur= new Attack(fileRead.nextLine());
		 damageList[index] = cur;
		 speedList[index] = cur;
		 index++;
	}
	fileRead.close();
	SortAttacks.sortBySpeed(speedList, 0, count-1);
	damageList= SortAttacks.sortByDamage(damageList,7);

	Logic logic = new Logic(typeList, damageList, speedList);
	logic.execute();
	}
}








