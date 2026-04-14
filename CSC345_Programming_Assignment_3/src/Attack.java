/**
 * @author Khanh Ngoc Nguyen
 * @course CSC345
 * @instructor Hudson Lynam 
 * @assignment Programming Project 3
 * @TA Anna Yami
 * @due March 26th, 3:30pm
 * 
 * filename Attack.java
 * 
 * This file implements the Attack class, which represents an attack with
 * 3 stats: type, damage, speed.
 */

/**
 * @author Khanh Nguyen
 * Class Attack
 * 
 * This class represents an attack with type, damage and speed.
 * 
 * @constructor Attack(int type, int damage, float speed) initiates an attack
 * @constructor Attack(String inputLine) initiates an attack using a String containing all the data
 */
public class Attack {
	private int type;
	private int damage;
	private float speed;
	private int hit;
	
	/**
	 * Attack(int type, int damage, float speed)
	 * Constructor initates an Attack with type, damage, speed
	 * @param type the type of the attack
	 * @param damage the damage value of the attack
	 * @param speed the speed value of the attack
	 */
	public Attack(int type, int damage, float speed) {
		this.type=type;
		this.damage=damage;
		this.speed=speed;
		if (type==0) {
			this.hit=0;
		}
		else {
		this.hit=1;
		}
	}
	
	/**
	 * dealDamage(int weight)
	 * 
	 * calculate the actual damage the attack will deal after 
	 * applying all damage mitigations
	 * 
	 * @param weight the % mitigated by armor
	 * @return the actual damage the attack deals
	 */
	public float dealDamage(int weight) {
		return (float) damage*weight*hit/ (100.0f);
	}
	
	/**
	 * Attack(int type, int damage, float speed)
	 * Constructor initates an Attack with type, damage, speed
	 * @param type the type of the attack
	 * @param damage the damage value of the attack
	 * @param speed the speed value of the attack
	 */
	public Attack(String inputLine) {
		String[] attackData = inputLine.split(", ");
		switch (attackData[0]) {
		case ("Lightning"): {this.type=0; break;}
		case ("Fire"): {this.type=1; break;}
		case ("Ice"): {this.type=2; break;}
		}
		this.damage = Integer.valueOf(attackData[1]);
		this.speed = Float.valueOf(attackData[2]);
		if (type==0) {
			this.hit=0;
		}
		else {
		this.hit=1;
		}
	}
	
	/**
	 * type() 
	 * @return the type of the Attack
	 */
	public int type() {
		return this.type;
	}
	/**
	 * damage()
	 * @return the raw damage value of the Attack
	 */
	public int damage() {
		return this.damage;
	}
	/**
	 * speed()
	 * @return the speed of the Attack
	 */
	public float speed() {
		return this.speed;
	}
	
}
