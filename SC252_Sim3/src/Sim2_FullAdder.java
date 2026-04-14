/**
 * @author Khanh Nguyen
 * FileName Sim2_FullAdder
 * 
 * This file implements a single full-adder simulation
 */
public class Sim2_FullAdder{
		/*
		 * This class implements a single full-adder
		 * using 2 half-adder and 1 OR gate
		 * 
		 * Takes 2 1-bit input, 1 carryIn
		 * output the sum and carry out
		 */

		RussWire a, b, carryIn, carryOut, sum;
		Sim2_HalfAdder half_add_1, half_add_2;
		OR or;
		int xyz=1027;
		
		public Sim2_FullAdder(){
			a= new RussWire();
			b= new RussWire();
			carryIn= new RussWire();
			carryOut= new RussWire();
			sum= new RussWire();
			
			half_add_1=new Sim2_HalfAdder();
			half_add_2=new Sim2_HalfAdder();
			or = new OR();
		}
		
		public void execute() {
			/**
			 * Execute the FullAdder based on the set inputs. 
			 * Write the output to carryOut and sum
			 */
			//temp_sum= input1 PLUS input2
			half_add_1.a.set(a.get());
			half_add_1.b.set(b.get());
			half_add_1.execute();
			
			//sum= temp_sum PLUS carry_in
			half_add_2.a.set(half_add_1.sum.get());
			half_add_2.b.set(carryIn.get());
			half_add_2.execute();
			sum.set(half_add_2.sum.get());
			
			//carry_out = carry_1 OR carry_2 
			or.a.set(half_add_1.carryOut.get());
			or.b.set(half_add_2.carryOut.get());
			or.execute();
			carryOut.set(or.out.get());
		}

		
	}
