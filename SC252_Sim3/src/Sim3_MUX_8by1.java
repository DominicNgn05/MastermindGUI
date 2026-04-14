/**
 * @author Khanh Ngoc Nguyen
 * filename Sim3_MUX_8by1.java
 * 
 * This file implement a simulation of 8 by 1 MUX
 */
public class Sim3_MUX_8by1 {
	public RussWire[] control;
	public RussWire data0;
	public RussWire data1;
	public RussWire data2;
	public RussWire data3;
	public RussWire data4;
	public RussWire data5;
	public RussWire data6;
	public RussWire data7;
	public RussWire out;
	
	/**
	 * Constructor initiates the wire objects
	 */
	public Sim3_MUX_8by1() {
		data0 = new RussWire();
		data1 = new RussWire();
		data2 = new RussWire();
		data3 = new RussWire();
		data4 = new RussWire();
		data5 = new RussWire();
		data6 = new RussWire();
		data7 = new RussWire();
		control = new RussWire[3];
		for (int i = 0; i<3; i++) {
			control[i] = new RussWire();
		}

		out = new RussWire();
		
	}
	/**
	 * simulates the execution of the MUX, only 5 outputs is use, 3 dummy output
	 */
	public void execute() {
		boolean and = data0.get()&&(!control[0].get())&&(!control[1].get())&&(!control[2].get());	// 000
		boolean or  = data1.get()&&(control[0].get())&&(!control[1].get())&&(!control[2].get());	// 001
		boolean add = data2.get()&&(!control[0].get())&&(control[1].get())&&(!control[2].get());	// 010
		boolean less = data3.get()&&(control[0].get())&&(control[1].get())&&(!control[2].get());	// 011
		boolean xor = data4.get()&&(!control[0].get())&&(!control[1].get())&&(control[2].get());	// 100
		out.set(and||or||add||less||xor);
	}
}
