/**
 * @author Khanh Ngoc Nguyen
 * filename: Sim3_ALU.java
 * 
 * implements the simulation of an n-bit ALU
 * 
 */
public class Sim3_ALU {
	// component
	Sim3_ALUElement[] atlus;
	// in 
	RussWire[] a;
	RussWire[] b;
	// configuration
	RussWire bNegate;
	RussWire[] aluOp;
	// out
	RussWire[] result;
	/**
	 * Constructor initializes the wire objects, the individual ALU elements in an ALU
	 * @param bit the number of bit of the ALU
	 */
	public Sim3_ALU(int bit) {
		atlus = new Sim3_ALUElement[bit];
		this.a= new RussWire[bit];
		this.b = new RussWire[bit];
		this.result = new RussWire[bit];
		this.aluOp = new RussWire[3];
		
		for (int i=0; i<3;i++) {
			this.aluOp[i]= new RussWire();
		}
		for (int i=0; i<bit;i++) {
			this.a[i] = new RussWire();
			this.b[i] = new RussWire();
			this.result[i] = new RussWire();
			this.atlus[i]= new Sim3_ALUElement();
		}
		bNegate = new RussWire();
	}
	
	/**
	 * execute the ALU with the current configuration and input
	 */
	public void execute() {
		// set operation control bit
		for (int i = 0; i<3; i++) {
			for (int j=0; j<atlus.length; j++) {
				atlus[j].aluOp[i].set(aluOp[i].get());
			}
		}
		// negation logic
		atlus[0].carryIn.set(bNegate.get());
		atlus[0].bInvert.set(bNegate.get());
		atlus[0].a.set(a[0].get());
		atlus[0].b.set(b[0].get());
		atlus[0].execute_pass1();
		for (int i=1; i<atlus.length; i++) {
			atlus[i].bInvert.set(bNegate.get());
			atlus[i].a.set(a[i].get());
			atlus[i].b.set(b[i].get());
			atlus[i].carryIn.set(atlus[i-1].carryOut.get());
			atlus[i].execute_pass1();
		}
		
		atlus[0].less.set(atlus[atlus.length-1].addResult.get());
		atlus[0].execute_pass2();
		this.result[0].set(atlus[0].result.get());
		for (int i=1; i<atlus.length; i++) {
			atlus[i].less.set(false);
			atlus[i].execute_pass2();
			this.result[i].set(atlus[i].result.get());
		}
	}
	
}
