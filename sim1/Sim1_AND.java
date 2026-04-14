/* Simulates a physical AND gate.
 *	takes 2 1-bit input a b, output a AND b
 * Author: Khanh Nguyen
 * 
 * 
 */

public class Sim1_AND
{
	public void execute()
	{
		if (a.get()==b.get()) {
			if (a.get()==true) {
				out.set(true);
			}
			else {
				out.set(false);
			}
		}
		else {
			out.set(false);
		}
	}



	public RussWire a,b;   // inputs
	public RussWire out;   // output

	public Sim1_AND()
	{
		a= new RussWire();
		b= new RussWire();
		out= new RussWire();
	}
	
	/*public static void main(String[] args) {
		Sim1_AND and = new Sim1_AND();
		and.a.set(false);
		and.b.set(false);
		and.execute();
		System.out.print(and.out);
		
	*/
}

