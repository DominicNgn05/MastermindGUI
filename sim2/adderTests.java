import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Should handle every possible case (within reason) for our three classes. The AdderX tests print out 
 * information that should help you deduce where the problem in your code lies. The other two types do not, 
 * as I feel it is easy enough to figure that out on your own and don't wish to clog your console with junk.
 * 
 * @author Logan P. Culliton
 */
class adderTests {
	
	/**
	 * Tests if the halfAdder works with two true inputs. 
	 * Output should be a false sum and a true carry out.
	 */
	@Test
	void halfAdderTest1() {
		Sim2_HalfAdder ha = new Sim2_HalfAdder();
		ha.a.set(true);
		ha.b.set(true);
		ha.execute();
		
		assertTrue(ha.sum.get() == false);
		assertTrue(ha.carryOut.get() == true);
	}
	
	/**
	 * Tests if the halfAdder works with A being a true input and B be being a false input. 
	 * Output should be a true sum and a false carry out.
	 */
	@Test
	void halfAdderTest2() {
		Sim2_HalfAdder ha = new Sim2_HalfAdder();
		ha.a.set(true);
		ha.b.set(false);
		ha.execute();
		
		assertTrue(ha.sum.get() == true);
		assertTrue(ha.carryOut.get() == false);
	}
	
	/**
	 * Tests if the halfAdder works with B being a true input and A be being a false input. 
	 * Output should be a true sum and a false carry out.
	 */
	@Test
	void halfAdderTest3() {
		Sim2_HalfAdder ha = new Sim2_HalfAdder();
		ha.a.set(false);
		ha.b.set(true);
		ha.execute();
		
		assertTrue(ha.sum.get() == true);
		assertTrue(ha.carryOut.get() == false);
	}
	
	/**
	 * Tests if the halfAdder works with two false inputs. 
	 * Output should be a false sum and a false carry out.
	 */
	@Test
	void halfAdderTest4() {
		Sim2_HalfAdder ha = new Sim2_HalfAdder();
		ha.a.set(false);
		ha.b.set(false);
		ha.execute();
		
		assertTrue(ha.sum.get() == false);
		assertTrue(ha.carryOut.get() == false);
	}
	
	/**
	 * Tests if the fullAdder works with two true inputs and a true carryIn.
	 * Output should be a true sum and a true carry out
	 */
	@Test
	void fullAdderTest1() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(true);
		fa.b.set(true);
		fa.carryIn.set(true);
		fa.execute();
		
		assertTrue(fa.sum.get() == true);
		assertTrue(fa.carryOut.get() == true);
	}
	
	/**
	 * Tests if the fullAdder works with A being a true input, B being a false input, and a true carryIn.
	 * Output should be a false sum and a true carry out
	 */
	@Test
	void fullAdderTest2() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(true);
		fa.b.set(false);
		fa.carryIn.set(true);
		fa.execute();
		
		assertTrue(fa.sum.get() == false);
		assertTrue(fa.carryOut.get() == true);
	}
	
	/**
	 * Tests if the fullAdder works with B being a true input, A being a false input, and a true carryIn.
	 * Output should be a false sum and a true carry out
	 */
	@Test
	void fullAdderTest3() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(false);
		fa.b.set(true);
		fa.carryIn.set(true);
		fa.execute();
		
		assertTrue(fa.sum.get() == false);
		assertTrue(fa.carryOut.get() == true);
	}
	
	/**
	 * Tests if the fullAdder works with two false inputs, and a true carryIn.
	 * Output should be a true sum and a false carry out
	 */
	@Test
	void fullAdderTest4() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(false);
		fa.b.set(false);
		fa.carryIn.set(true);
		fa.execute();
		
		assertTrue(fa.sum.get() == true);
		assertTrue(fa.carryOut.get() == false);
	}
	
	/**
	 * Tests if the fullAdder works with two true inputs and a false carryIn.
	 * Output should be a false sum and a true carry out
	 */
	@Test
	void fullAdderTest5() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(true);
		fa.b.set(true);
		fa.carryIn.set(false);
		fa.execute();
		
		assertTrue(fa.sum.get() == false);
		assertTrue(fa.carryOut.get() == true);
	}
	
	/**
	 * Tests if the fullAdder works with A being a true input, B being a false input, and a false carryIn.
	 * Output should be a true sum and a false carry out
	 */
	@Test
	void fullAdderTest6() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(true);
		fa.b.set(false);
		fa.carryIn.set(false);
		fa.execute();
		
		assertTrue(fa.sum.get() == true);
		assertTrue(fa.carryOut.get() == false);
	}
	
	/**
	 * Tests if the fullAdder works with B being a true input, A being a false input, and a false carryIn.
	 * Output should be a true sum and a false carry out
	 */
	@Test
	void fullAdderTest7() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(false);
		fa.b.set(true);
		fa.carryIn.set(false);
		fa.execute();
		
		assertTrue(fa.sum.get() == true);
		assertTrue(fa.carryOut.get() == false);
	}
	
	/**
	 * Tests if the fullAdder works with two false inputs, and a false carryIn.
	 * Output should be a false sum and a false carry out
	 */
	@Test
	void fullAdderTest8() {
		Sim2_FullAdder fa = new Sim2_FullAdder();
		fa.a.set(false);
		fa.b.set(false);
		fa.carryIn.set(false);
		fa.execute();
		
		assertTrue(fa.sum.get() == false);
		assertTrue(fa.carryOut.get() == false);
	}
	
	/**
	 * Tests a relatively small number operation which causes a carry out, but not overflow.
	 * We are adding 11100 and 00111 (-4 and 7) which should result in 00011 (3).
	 * NOTE: The first index of the array should line up with the least significant bit. If your code does 
	 * not have the least significant bit at index 0, I cannot promise the efficacy of these test cases.
	 */
	@Test
	void adderXTest1() {
		// Set up how many bits you want to add together.
		int x = 5;
		// Which test number is this? (Needed for accurate printing of test number)
		int testNumber = 1;
		// Set up two numbers being added.
		boolean[] aInputs = {true, true, true, false, false}; // 00111
		boolean[] bInputs = {false, false, true, true, true}; // 11100
		// Insert what the answer *should* be if calculation is correct.
		boolean[] answer = {true, true, false, false, false}; // 00011
		
		Sim2_AdderX addX = new Sim2_AdderX(x);
		for(int i = 0; i < x; i++) {
			addX.a[i].set(aInputs[i]);
			addX.b[i].set(bInputs[i]);
		}
		
		addX.execute();
		
		boolean[] results = new boolean[x];
		
		for(int i = 0; i < x; i++) {
			results[i] = addX.sum[i].get();
		}
		System.out.print(printOperation(aInputs, bInputs, answer, results, addX, x, testNumber));
		System.out.println("Carry out: " + addX.carryOut.get());
		System.out.println("Overflow: " + addX.overflow.get() + "\n");
		assertArrayEquals(results, answer);
		assertTrue(addX.carryOut.get() == true);
		assertTrue(addX.overflow.get() == false);
	}
	
	/**
	 * Tests a relatively small number operation which causes overflow, but not carry out.
	 * We are adding 01100 and 01110 (12 and 14) which should result in 11010 (-6) due to overflow.
	 * NOTE: The first index of the array should line up with the least significant bit. If your code does 
	 * not have the least significant bit at index 0, I cannot promise the efficacy of these test cases.
	 */
	@Test
	void adderXTest2() {
		// Set up how many bits you want to add together.
		int x = 5;
		// Which test number is this? (Needed for accurate printing of test number)
		int testNumber = 2;
		// Set up two numbers being added.
		boolean[] aInputs = {false, false, true, true, false}; // 01100
		boolean[] bInputs = {false, true, true, true, false}; // 01110
		// Insert what the answer *should* be if calculation is correct.
		boolean[] answer = {false, true, false, true, true}; // 11010
		
		Sim2_AdderX addX = new Sim2_AdderX(x);
		for(int i = 0; i < x; i++) {
			addX.a[i].set(aInputs[i]);
			addX.b[i].set(bInputs[i]);
		}
		
		addX.execute();
		
		boolean[] results = new boolean[x];
		
		for(int i = 0; i < x; i++) {
			results[i] = addX.sum[i].get();
		}
		System.out.print(printOperation(aInputs, bInputs, answer, results, addX, x, testNumber));
		System.out.println("Carry out: " + addX.carryOut.get());
		System.out.println("Overflow: " + addX.overflow.get() + "\n");
		assertArrayEquals(results, answer);
		assertTrue(addX.carryOut.get() == false);
		assertTrue(addX.overflow.get() == true);
	}
	
	/**
	 * Tests a relatively small number operation which causes overflow AND carry out
	 * We are adding 10111 and 10110 (-9 and -10) which should result in 01101 (13) due to overflow and carry out.
	 * NOTE: The first index of the array should line up with the least significant bit. If your code does 
	 * not have the least significant bit at index 0, I cannot promise the efficacy of these test cases.
	 */
	@Test
	void adderXTest3() {
		// Set up how many bits you want to add together.
		int x = 5;
		// Which test number is this? (Needed for accurate printing of test number)
		int testNumber = 3;
		// Set up two numbers being added.
		boolean[] aInputs = {true, true, true, false, true}; // 10111
		boolean[] bInputs = {false, true, true, false, true}; // 10110
		// What the answer *should* be if calculation is correct.
		boolean[] answer = {true, false, true, true, false}; // 01101
		
		Sim2_AdderX addX = new Sim2_AdderX(x);
		for(int i = 0; i < x; i++) {
			addX.a[i].set(aInputs[i]);
			addX.b[i].set(bInputs[i]);
		}
		
		addX.execute();
		
		boolean[] results = new boolean[x];
		
		for(int i = 0; i < x; i++) {
			results[i] = addX.sum[i].get();
		}
		System.out.print(printOperation(aInputs, bInputs, answer, results, addX, x, testNumber));
		System.out.println("Carry out: " + addX.carryOut.get());
		System.out.println("Overflow: " + addX.overflow.get() + "\n");
		assertArrayEquals(results, answer);
		assertTrue(addX.carryOut.get() == true);
		assertTrue(addX.overflow.get() == true);
	}
	
	/**
	 * Tests a relatively small number operation which causes neither overflow or carry out.
	 * We are adding 00011 and 00101 (3 and 5) which should result in 01000 (8).
	 * NOTE: The first index of the array should line up with the least significant bit. If your code does 
	 * not have the least significant bit at index 0, I cannot promise the efficacy of these test cases.
	 */
	@Test
	void adderXTest4() {
		// Set up how many bits you want to add together.
		int x = 5;
		// Which test number is this? (Needed for accurate printing of test number)
		int testNumber = 4;
		// Set up two numbers being added.
		boolean[] aInputs = {true, true, false, false, false}; // 00011
		boolean[] bInputs = {true, false, true, false, false}; // 00101
		// What the answer *should* be if calculation is correct.
		boolean[] answer = {false, false, false, true, false}; // 01000
		
		Sim2_AdderX addX = new Sim2_AdderX(x);
		for(int i = 0; i < x; i++) {
			addX.a[i].set(aInputs[i]);
			addX.b[i].set(bInputs[i]);
		}
		
		addX.execute();
		
		boolean[] results = new boolean[x];
		
		for(int i = 0; i < x; i++) {
			results[i] = addX.sum[i].get();
		}
		System.out.print(printOperation(aInputs, bInputs, answer, results, addX, x, testNumber));
		System.out.println("Carry out: " + addX.carryOut.get());
		System.out.println("Overflow: " + addX.overflow.get() + "\n");
		assertArrayEquals(results, answer);
		assertTrue(addX.carryOut.get() == false);
		assertTrue(addX.overflow.get() == false);
	}
	
	/**
	 * Tests a relatively large number operation which causes neither overflow or carry out. This test 
	 * should be redundant, as if your program is designed correctly the size of the numbers should not 
	 * matter, but I figure it is worth having in just in case.
	 * We are adding 0000111100010001 and 0101110000011110 (3,857 and 23,582),
	 * which should result in 0110101100101111 (27,439).
	 * NOTE: The first index of the array should line up with the least significant bit. If your code does 
	 * not have the least significant bit at index 0, I cannot promise the efficacy of these test cases.
	 */
	@Test
	void adderXTest5() {
		// Set up how many bits you want to add together.
		int x = 16;
		// Which test number is this? (Needed for accurate printing of test number)
		int testNumber = 5;
		// Set up two numbers being added.
		boolean[] aInputs = {true, false, false, false, true, false, false, false, true, true, true, true, false, false, false, false}; // 0000111100010001
		boolean[] bInputs = {false, true, true, true, true, false, false, false, false, false, true, true, true, false, true, false}; // 0101110000011110
		// What the answer *should* be if calculation is correct.
		boolean[] answer = {true, true, true, true, false, true, false, false, true, true, false, true, false, true, true, false}; // 0110101100101111
		
		Sim2_AdderX addX = new Sim2_AdderX(x);
		for(int i = 0; i < x; i++) {
			addX.a[i].set(aInputs[i]);
			addX.b[i].set(bInputs[i]);
		}
		
		addX.execute();
		
		boolean[] results = new boolean[x];
		
		for(int i = 0; i < x; i++) {
			results[i] = addX.sum[i].get();
		}
		System.out.print(printOperation(aInputs, bInputs, answer, results, addX, x, testNumber));
		System.out.println("Carry out: " + addX.carryOut.get());
		System.out.println("Overflow: " + addX.overflow.get() + "\n");
		assertArrayEquals(results, answer);
		assertTrue(addX.carryOut.get() == false);
		assertTrue(addX.overflow.get() == false);
	}
	
	/**
	 * Tests that your adder can handle adding two zeroes (if the initial carry value is correct).
	 * We are adding 00000 and 00000 (0 and 0) which should result in 00000 (0).
	 * NOTE: The first index of the array should line up with the least significant bit. If your code does 
	 * not have the least significant bit at index 0, I cannot promise the efficacy of these test cases.
	 */
	@Test
	void adderXTest6() {
		// Set up how many bits you want to add together.
		int x = 5;
		// Which test number is this? (Needed for accurate printing of test number)
		int testNumber = 6;
		// Set up two numbers being added.
		boolean[] aInputs = {false, false, false, false, false}; // 00000
		boolean[] bInputs = {false, false, false, false, false}; // 00000
		// What the answer *should* be if calculation is correct.
		boolean[] answer = {false, false, false, false, false}; // 00000
		
		Sim2_AdderX addX = new Sim2_AdderX(x);
		for(int i = 0; i < x; i++) {
			addX.a[i].set(aInputs[i]);
			addX.b[i].set(bInputs[i]);
		}
		
		addX.execute();
		
		boolean[] results = new boolean[x];
		
		for(int i = 0; i < x; i++) {
			results[i] = addX.sum[i].get();
		}
		System.out.print(printOperation(aInputs, bInputs, answer, results, addX, x, testNumber));
		System.out.println("Carry out: " + addX.carryOut.get());
		System.out.println("Overflow: " + addX.overflow.get() + "\n");
		assertArrayEquals(results, answer);
		assertTrue(addX.carryOut.get() == false);
		assertTrue(addX.overflow.get() == false);
	}
	
	public String printOperation(boolean[] aInputs, boolean[] bInputs, boolean[] answer, boolean[] result, Sim2_AdderX addX, int x, int testNum) {
		String a = "A: ";
		String b = "B: ";
		String answerStr = "Expected Answer: ";
		String resultStr = "Actual Answer: ";
		String output = "";
		// Gets A into a nice readable format
		for(int i = (x-1); i >= 0; i--) {
			if(aInputs[i] == false) {
				a += "0";
			} else {
				a += "1";
			}
		}
		a += "\n";
		// Gets B into a nice readable format
		for(int i = (x-1); i >= 0; i--) {
			if(bInputs[i] == false) {
				b += "0";
			} else {
				b += "1";
			}
		}
		b += "\n";
		// Gets Answer into a nice readable format
		for(int i = (x-1); i >= 0; i--) {
			if(answer[i] == false) {
				answerStr += "0";
			} else {
				answerStr += "1";
			}
		}
		answerStr += "\n";
		// Gets result into a nice readable format
		for(int i = (x-1); i >= 0; i--) {
			if(result[i] == false) {
				resultStr += "0";
			} else {
				resultStr += "1";
			}
		}
		resultStr += "\n";
		// Combine Everything
		output += "adderXTest" + testNum + ": \n";
		output += a + b;
		output += "-".repeat(x + 3) + "\n";
		output += answerStr + resultStr + "\n";
		return output;
	}

}
