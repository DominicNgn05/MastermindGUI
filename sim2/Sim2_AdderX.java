/**
 * @author: Khanh Nguyen
 * FileName: Khanh Nguyen
 * Simulates a physical device that performs (signed) addition on
 * a 32-bit input.
 *
 * This Class implementes a simulation of a n bit parallel ADDER
 * Does not support subtraction
 */

public class Sim2_AdderX {
	/**
	 * This class represents a n-bit parallel adder for bit addition
	 * 
	 * The constructor takes an integer parameter that represents the input size 
	 */

	private AND and_1 = new AND();
	private AND and_2 = new AND();
	private AND and_3 = new AND();
	private AND and_4 = new AND();

	private NOT not_1 = new NOT();
	private NOT not_2 = new NOT();
	private NOT not_3 = new NOT();
	private OR or = new OR();
	RussWire[] a,b;
	// outputs
	public RussWire[] sum;
	public RussWire   carryOut, overflow;
	public Sim2_FullAdder[] FullAdders;
	
	public Sim2_AdderX(int n){
		this.a   = new RussWire[n];
		this.b   = new RussWire[n];
		this.sum = new RussWire[n];
		this.FullAdders= new Sim2_FullAdder[n];
		this.carryOut = new RussWire();
		this.overflow = new RussWire();
		for (int i=0; i<n; i++) {
			a[i] = new RussWire();
			b[i] = new RussWire();
			sum[i] = new RussWire();
			FullAdders[i] = new Sim2_FullAdder();
		}
	}
	
	public void execute() {
		/**
		 * Execute all n full adders
		 * Write the final sum to sum
		 * Write the carry out bit and overflow bit to the respective field
		 */
		int size = a.length;
		FullAdders[0].carryIn.set(false);
		FullAdders[0].a.set(a[0].get());
		FullAdders[0].b.set(b[0].get());
		FullAdders[0].execute();
		sum[0].set(FullAdders[0].sum.get());
	for (int i=1; i<size; i++) {
		FullAdders[i].carryIn.set(FullAdders[i-1].carryOut.get());
		FullAdders[i].a.set(a[i].get());
		FullAdders[i].b.set(b[i].get());
		FullAdders[i].execute();
		sum[i].set(FullAdders[i].sum.get());
	}
	
	carryOut.set(FullAdders[size-1].carryOut.get());
	
	//overflow logic ab(not in) OR (not a)(not b)in
	and_1.a.set(a[size-1].get());	
	and_1.b.set(b[size-1].get());
	and_1.execute();
	not_1.in.set(FullAdders[size-1].carryIn.get());
	not_1.execute();
	and_2.b.set(not_1.out.get());	
	and_2.a.set(and_1.out.get());
	and_2.execute();	//and_2.out = ab(not in)
	
	not_2.in.set(a[size-1].get());
	not_2.execute();
	not_3.in.set(b[size-1].get());
	not_3.execute();
	
	and_3.a.set(not_2.out.get());
	and_3.b.set(not_3.out.get());
	and_3.execute();
	
	and_4.a.set(and_3.out.get());
	and_4.b.set(FullAdders[size-1].carryIn.get());
	and_4.execute();
	
	or.a.set(and_2.out.get());
	or.b.set(and_4.out.get());
	or.execute();
	overflow.set(or.out.get());
}

}
