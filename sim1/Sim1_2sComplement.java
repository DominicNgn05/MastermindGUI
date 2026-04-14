/* Simulates a physical device that performs 2's complement on a 32-bit input.
 *
 * Author: Khanh Nguyen
 */

public class Sim1_2sComplement
{
	public void execute()
	{
		
		for (int i=0; i<32; i++) {
			not[i].in.set(in[i].get());
			not[i].execute();
			adder.a[i].set(not[i].out.get());	// Bit flip then set value to adder input1
			
			//set adder input2 to 1
			if (i==0) {
				adder.b[i].set(true);
			}
			else {
				adder.b[i].set(false);
			}

		}	
		adder.execute();
		for (int i=0; i<32; i++) {	
			out[i].set(adder.sum[i].get());	// Set output value
		}
		
		
	}



	// you shouldn't change these standard variables...
	public RussWire[] in;
	public RussWire[] out;


	private Sim1_ADD adder;
	private Sim1_NOT not[];



	public Sim1_2sComplement()
	{
		in = new RussWire[32];
		out = new RussWire[32];
		not = new Sim1_NOT[32];
		
		adder = new Sim1_ADD();
		for(int i=0; i<32; i++) {
			in[i]= new RussWire();
			out[i]= new RussWire();
			not[i] = new Sim1_NOT();

		}
	}
}

