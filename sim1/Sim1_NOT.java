/* Simulates a physical NOT gate.
 *
 * Author: Khanh Nguyen
 */

public class Sim1_NOT
{
	public void execute()
	{
		if (in.get()==true) {
			out.set(false);
		}
		else {
			out.set(true);
		}
	}



	public RussWire in;    // input
	public RussWire out;   // output

	public Sim1_NOT()
	{
		in = new RussWire();
		out= new RussWire();
	}
}

