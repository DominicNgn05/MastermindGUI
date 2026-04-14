/**
 * @author Khanh Ngoc Nguyen
 * @course CSC345
 * @instructor Hudson Lynam 
 * @assignment Programming Project 3
 * @TA Anna Yami
 * @due March 26th, 3:30pm
 * 
 * filename: Logic.java
 * This file implements the logic to mitigate and avoid Attacks
 * Represents the decision making process of Logic 
 */

/**
 * @author Khanh Nguyen
 * Class Logic
 * this class represents the decision making process of Logic to 
 * mitigate or avoid incoming Attacks
 * 
 * @onstructor Logic(int[] typeList, Attack[] damageList, Attack[] speedList)
 */
public class Logic {
	private int[] typeList;
	private Attack[] damageList;
	private Attack[] speedList;
	private float totalDamage;
	private int shield;
	private int armorMitigation;
	private float highestDamage;
	
	/**
	 * Logic(int[] typeList, Attack[] damageList, Attack[] speedList)
	 * Constructor initiates a new decision making process to react to the 
	 * Attacks in the parameter arrays.
	 * @param typeList	a list containing the count of each type of Attack
	 * @param damageList a list containing every Attacks, sorted by damage value
	 * @param speedList a list containing every Attacks, sorted by speed value
	 */
	public Logic(int[] typeList, Attack[] damageList, Attack[] speedList) {
		this.typeList = typeList;
		this.damageList = damageList;
		this.speedList = speedList;
		this.shield = 5;
		this.totalDamage = 0;
		this.armorMitigation =30;
		this.highestDamage = damageList[damageList.length-1].damage();
	}

	/**
	 * getMax()
	 * 
	 * Search the Attack arrays to find the Attack that will deal the highest damage
	 */
	private void getMax() {
		highestDamage = damageList[0].dealDamage(100);
		for (Attack a: damageList) {
			if (a.dealDamage(100)> highestDamage) {
				highestDamage=a.dealDamage(100);
			}
		}
	}
	
	/**
	 * teleport()
	 * implement the process of teleporting away to avoid Attacks
	 */
	private void teleport() {
		System.out.print("Logic had to Teleport away");
	}

	/**
	 * execute()
	 * 
	 * go through the decision tree and report the final state after all attacks have resolved
	 * report the total damage taken or if teleported away to avoid damage.
	 */
	public void execute() {
		if (typeList[0]>6) {
			teleport();
			return;
		}
		for (Attack a : speedList) {
			// lightning damage
			if (a.type() == 0) {
				if (shield == 0) { teleport(); return;}	// no shield available, teleport away
				else {
					shield --;
					typeList[0]--;
				}
			}
			// non lightning
			else {
				float actualDamage=0;
				float rawDamage = a.dealDamage(100);
				if (typeList[0]>0) {	// if lightning remains
						actualDamage = a.dealDamage(100-armorMitigation); // tank with armor
					}
				else {	// no lightning remains
					if (highestDamage > 500000 ) { // and has high damage incoming
						actualDamage = a.dealDamage(100-armorMitigation); // tank with armor
					}				
					// else, block with shield for 0dmg
				}
				// update highest damage
				if (rawDamage >= highestDamage) {
					getMax();	
				}
				// if will take too much and shield remains
				if (totalDamage + actualDamage>=1200000) {
					if (shield>0) {
						actualDamage=0;	// block
						shield--;
					}
				}
					
				totalDamage += actualDamage;
			}
			
		}
		if (totalDamage>=1200000) { // if take too much damage, teleport
			teleport();
			return;
		}
		else {	
			System.out.printf("Logic took %.1f Damage", totalDamage);
		}
	}
}