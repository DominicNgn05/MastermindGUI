/* Simulates a physical device that performs (signed) subtraction on
 * a 32-bit input.
 * 
 * Author:  Khanh Nguyen
 */

public class Sim1_SUB
{
	public void execute()
	{
		// TODO: fill this in!
		//
		// REMEMBER: You may call execute() on sub-objects here, and
		//           copy values around - but you MUST NOT create
		//           objects while inside this function.
		
		//get 2s complement of b
		for (int i=0; i<32; i++) {
			complement.in[i].set(b[i].get());
		}
		complement.execute();
		
		//add a and b 2s complement
		for (int i=0; i<32; i++) {
			adder.a[i].set(a[i].get());
			adder.b[i].set(complement.out[i].get());
		}
		adder.execute();
		
		for (int i=0; i<32;i++) {
			sum[i].set(adder.sum[i].get());
		}
		
	}



	// --------------------
	// Don't change the following standard variables...
	// --------------------

	// inputs
	public RussWire[] a,b;

	// output
	public RussWire[] sum;

	// --------------------
	// But you should add some *MORE* variables here.
	// --------------------
	// TODO: fill this in
	private Sim1_ADD adder;
	private Sim1_2sComplement complement;


	public Sim1_SUB()
	{
		a = new RussWire[32];
		b = new RussWire[32];
		sum = new RussWire[32];
		adder = new Sim1_ADD();
		complement = new Sim1_2sComplement();
	}
}

