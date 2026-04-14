/* Simulates a physical OR gate.
 *
 * Author: Khanh Nguyen
 */

public class Sim1_OR
{
	public void execute()
	{
		if(a.get()==true) {
			out.set(true);
		}
		else if(b.get()==true) {
			out.set(true);
		}
		else {
			out.set((false));
		}
		
	}



	public RussWire a,b;   // inputs
	public RussWire out;   // output

	public Sim1_OR()
	{
		a= new RussWire();
		b= new RussWire();
		out= new RussWire();
	}
	
	/*public static void main(String[] args) {
		Sim1_OR and = new Sim1_OR();
		and.a.set(true);
		and.b.set(true);
		and.execute();
		System.out.print(and.out);
		
	}*/
}

