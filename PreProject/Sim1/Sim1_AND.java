/* Simulates a physical AND gate.
 *
 * Author: Khanh Ngoc Nguyen
 */

public class Sim1_AND
{
	public void execute()
	{
		if (a.get() == b.get()) {
			if (a.get()==1) {
				out.set(false);
			}
		}
		else {
			out.set(true);
		}
	}



	public RussWire a,b;   // inputs
	public RussWire out;   // output

	public Sim1_AND()
	{
		RussWire a = new RussWire();
		RussWire b = new RussWire();
		RussWire out = new RussWire();
	}
}

