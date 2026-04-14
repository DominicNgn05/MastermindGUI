/**
 * @author Khanh Nguyen
 * filename Sim3_ALUElement.java
 * 
 * This file implements a simulation of a 1-bit ALU
 */
public class Sim3_ALUElement {
	//in
	public RussWire[] aluOp;
	public RussWire bInvert;
	public RussWire a;
	public RussWire b;
	public RussWire carryIn;
	public RussWire less;
	
	//out
	public RussWire result;
	public RussWire addResult;
	public RussWire carryOut;
	//utils
	private Sim3_MUX_8by1 mux;
	private Sim3_MUX_8by1 muxInvert;
	private Sim2_FullAdder add;
	
	/**
	 * Constructor initiates all the wire objects and other pieces of Hardware needed in an ALU
	 * including a full adder
	 */
	public Sim3_ALUElement() {
		this.aluOp = new RussWire[3];
		for (int i=0; i<3; i++) {
			this.aluOp[i] = new RussWire();
		}
		this.bInvert = new RussWire();
		this.a = new RussWire();
		this.b = new RussWire();
		this.carryIn = new RussWire();
		this.less = new RussWire();
		
		//
		this.carryOut = new RussWire();
		this.result = new RussWire();
		this.addResult =  new RussWire();
		
		//utils
		this.mux = new Sim3_MUX_8by1();
		this.add = new Sim2_FullAdder();
		this.muxInvert = new Sim3_MUX_8by1();
		
	}
	/**
	 * execute_pass1()
	 * simulate the first pass of the ALU, where all operations are perform except for less
	 * after execution, AND, OR, XOR, and ADD have been performed
	 */
	public void execute_pass1() {
	for (int i=0; i<3; i++) {
		mux.control[i].set(aluOp[i].get());
		}
	// AND
	mux.data0.set(a.get()&&b.get());
	// OR
	mux.data1.set(a.get() || b.get());
	// ADD
		// bInvert logic
	muxInvert.data0.set(b.get());
	muxInvert.data1.set(Logic.NOT(b.get()));
	muxInvert.control[0].set(bInvert.get());
	muxInvert.control[1].set(false);
	muxInvert.control[2].set(false);
	
	// in courtesy of the Spec, and in courtesy of Russ
	// couldve been a for loop...
	muxInvert.data2.set(false);
	muxInvert.data3.set(false);
	muxInvert.data4.set(false);
	muxInvert.data5.set(false);
	muxInvert.data6.set(false);
	muxInvert.data7.set(false);
	
	muxInvert.execute();
		// do ADD
	add.a.set(a.get());
	add.b.set(muxInvert.out.get());
	add.carryIn.set(carryIn.get());
	add.execute();
	carryOut.set(add.carryOut.get());
	mux.data2.set(add.sum.get());
	addResult.set(add.sum.get());
	//Less
	
	// XOR
	mux.data4.set(Logic.XOR(a.get(), b.get()));
	mux.data5.set(false);
	mux.data6.set(false);
	mux.data7.set(false);
	}
	/**
	 * execute_pass2()
	 * simulates the 2nd pass of the ALU, mostly contains logic for slt
	 */
	public void execute_pass2() {
		//less
		mux.data3.set(less.get());
		mux.execute();
		result.set(mux.out.get());	
	}
	
	
}
