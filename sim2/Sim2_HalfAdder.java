/**
 * @author Khanh Nguyen
 * FileName: Sim2_HalfAdder
 * 
 * This file implements a single half adder simulation
 */
public class Sim2_HalfAdder {
		/*
		 * This class implements a half-adder
		 * 
		 * Takes 2 1-bit input a and b
		 * output sum and carry out
		 */
		RussWire a, b, carryOut, sum;
		AND and;
		XOR xor;
		
		public Sim2_HalfAdder() {
			a   = new RussWire();
			b  = new RussWire();
			sum = new RussWire();
			carryOut = new RussWire();
			and = new AND();
			xor = new XOR();
			
		}
		
		public void execute() {
			/*
			 * Execute the half adder based on the set inputs
			 * write the output to sum and carryOut
			 */
			//carry = inp1 AND inp2
			and.a.set(a.get());
			and.b.set(b.get());
			and.execute();
			carryOut.set(and.out.get());
			
			//sum = inp1 XOR inp2
			xor.a.set(a.get());
			xor.b.set(b.get());
			xor.execute();
			sum.set(xor.out.get());
			
		}
	}
