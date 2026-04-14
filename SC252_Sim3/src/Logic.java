/**
 * @author Khanh Ngoc Nguyen
 * filename Logic.java
 * 
 * this file implements  4 basic logical functions as static methods
 */
public class Logic {
	/**
	 * @param in1
	 * @param in2
	 * @return in1 OR in2 
	 */
	public static boolean OR(Boolean in1, Boolean in2) {
		OR or = new OR();
		or.a.set(in1);
		or.b.set(in2);
		or.execute();
		return or.out.get();
	}
	/**
	 * 
	 * @param in1
	 * @param in2
	 * @return in1 AND in2
	 */
	public static boolean AND(Boolean in1, Boolean in2) {
		AND and = new AND();
		and.a.set(in1);
		and.b.set(in2);
		and.execute();
		return and.out.get();
	}
	/**
	 * 
	 * @param in1
	 * @param in2
	 * @return in1 XOR in2
	 */
	public static boolean XOR(Boolean in1, Boolean in2) {
		XOR xor = new XOR();
		xor.a.set(in1);
		xor.b.set(in2);
		xor.execute();
		return xor.out.get();
	}
	/**
	 * 
	 * @param in
	 * @return not in
	 */
	public static boolean NOT(Boolean in) {
		NOT not = new NOT();
		not.in.set(in);
		not.execute();
		return not.out.get();
	}
}

