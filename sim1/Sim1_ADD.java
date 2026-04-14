/** Simulates a physical device that performs (signed) addition on
 * a 32-bit input.
 *
 * This Class implementes a simulation of a 32 bit parallel ADDER
 * Does not support subtraction
 * @author: Khanh Nguyen
 */

public class Sim1_ADD
{
	
	
	private class Full_ADD{
		/*
		 * This class implements a single full-adder
		 * using 2 half-adder and 1 OR gate
		 * 
		 * Takes 2 1-bit input, 1 carryIn
		 * output the sum and carry out
		 */
		private class Half_ADD{
			/*
			 * This class implements a half-adder
			 * 
			 * Takes 2 1-bit input
			 * output sum and carry out
			 */
			RussWire input_1, input_2, carry, sum;
			Sim1_AND and;
			Sim1_XOR xor;
			
			public Half_ADD() {
				input_1   = new RussWire();
				input_2  = new RussWire();
				sum = new RussWire();
				carry = new RussWire();
				and = new Sim1_AND();
				xor = new Sim1_XOR();
				
			}
			
			public void execute() {
				
				//carry = inp1 AND inp2
				and.a.set(input_1.get());
				and.b.set(input_2.get());
				and.execute();
				carry.set(and.out.get());
				
				//sum = inp1 XOR inp2
				xor.a.set(input_1.get());
				xor.b.set(input_2.get());
				xor.execute();
				sum.set(xor.out.get());
				
			}
		}
		
		RussWire input_1, input_2, carry_in, carry_out, sum;
		Half_ADD half_add_1, half_add_2;
		Sim1_OR or;
		
		public Full_ADD(){
			input_1= new RussWire();
			input_2= new RussWire();
			carry_in= new RussWire();
			carry_out= new RussWire();
			sum= new RussWire();
			
			half_add_1=new Half_ADD();
			half_add_2=new Half_ADD();
			or = new Sim1_OR();
		}
		
		public void execute() {
			
			//temp_sum= input1 PLUS input2
			half_add_1.input_1.set(input_1.get());
			half_add_1.input_2.set(input_2.get());
			half_add_1.execute();
			
			//sum= temp_sum PLUS carry_in
			half_add_2.input_1.set(half_add_1.sum.get());
			half_add_2.input_2.set(carry_in.get());
			half_add_2.execute();
			sum.set(half_add_2.sum.get());
			
			//carry_out = carry_1 OR carry_2 
			or.a.set(half_add_1.carry.get());
			or.b.set(half_add_2.carry.get());
			or.execute();
			carry_out.set(or.out.get());
		}

		
	}
	public void execute()
	{	
		
		for (int i=0; i<32; i++) {
			//carry_in logic
			if (i==0) {
				full_ADD[i].carry_in.set(false);
			}
			else {
				full_ADD[i].carry_in.set(full_ADD[i-1].carry_out.get());
			}
			
			//setting input
			full_ADD[i].input_1.set(a[i].get());
			full_ADD[i].input_2.set(b[i].get());
			
			//placing result
			full_ADD[i].execute();
			sum[i].set(full_ADD[i].sum.get());
		}
		
		carryOut.set(full_ADD[31].carry_out.get());
		
		//overflow logic (XOR(a;carryIn) AND XOR(b;carryIn)
		
		//XOR(a;carryOut)
		xor_1.a.set(full_ADD[31].carry_in.get());
		xor_1.b.set(carryOut.get());
		xor_1.execute();
		
		
		//XOR(b;carryOut)
		xor_2.a.set(b[31].get());
		xor_2.b.set(full_ADD[31].carry_in.get());
		xor_2.execute();
		
		//AND
		and.a.set(xor_1.out.get());
		and.b.set(xor_2.out.get());
		and.execute();
		
		overflow.set(and.out.get());
		
	}

	private Full_ADD[] full_ADD;
	private Sim1_XOR xor_1 = new Sim1_XOR();
	private Sim1_XOR xor_2 = new Sim1_XOR();
	private Sim1_AND and = new Sim1_AND();
	
	

	// ------ 
	// It should not be necessary to change anything below this line,
	// although I'm not making a formal requirement that you cannot.
	// ------ 

	// inputs
	public RussWire[] a,b;

	// outputs
	public RussWire[] sum;
	public RussWire   carryOut, overflow;

	public Sim1_ADD()
	{
		/* Instructor's Note:
		 *
		 * In Java, to allocate an array of objects, you need two
		 * steps: you first allocate the array (which is full of null
		 * references), and then a loop which allocates a whole bunch
		 * of individual objects (one at a time), and stores those
		 * objects into the slots of the array.
		 */

		a   = new RussWire[32];
		b   = new RussWire[32];
		sum = new RussWire[32];
		full_ADD= new Full_ADD[32];
		for (int i=0; i<32; i++)
		{
			a  [i] = new RussWire();
			b  [i] = new RussWire();
			sum[i] = new RussWire();
			full_ADD[i]= new Full_ADD();
		}

		carryOut = new RussWire();
		overflow = new RussWire();
	}
}

