/* Simulates a physical OR gate.
 *
 * Author: TODO
 */

public class Sim1_OR
{
	public void execute()
	{
		if (a.get()==true) {
			out.set(true)
		}
		else if(b.get()==true) {
			out.set(true)
		}
		else {
			out.set(false);
		}
	}



	public RussWire a,b;   // inputs
	public RussWire out;   // output

	public Sim1_OR()
	{
		RussWire a = new RussWire();
		RussWire b = new RussWire();
		RussWire out = new RussWire();
	}
	
	public static void main(string[] args) {
		Sim1_OR or = new Sim1_OR();
		or.a.set(true);
		or.b.set(false);
		or.execute();
		System.out.print(or.out);
	}
}

