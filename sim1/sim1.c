/* Implementation of a 32-bit adder in C.
	
 * File name: sim.c
 * This file implement a 32-bit adder using bit-wise operations
 * allows for subtraction
 * Author: Khanh Nguyen
 */


#include "sim1.h"



void execute_add(Sim1Data *obj)
{
		obj->sum =0;
		int subtract = obj->isSubtraction;
		int carryIn;
		if (subtract==1){
			carryIn=1;
		}else{
			carryIn=0;
		}
	for (int i=0; i<32; i++){	//adder loop
		int aBitCurr = (obj->a>>i) &  0x1;
		int bBitCurr = (obj->b>>i) & 0x1;
		
		if (i==31){
			obj->aNonNeg = (~aBitCurr) & 0x1;
			obj->bNonNeg = (~bBitCurr) & 0x1;
		}
		if (subtract==1){
			bBitCurr= (~bBitCurr)&0x1;
		}
		
		//half adder a b
		int carry1 = aBitCurr & bBitCurr;
		int tempSum = aBitCurr ^ bBitCurr;
	
		//half adder tempSum carryIn
		if (tempSum ^ carryIn == 1){
		obj->sum = obj->sum | (0x1<<i);		//set ith bit to sum
		}
		
		// the following logic set carryOut after completing addition of msb
		// but also preserves the carryIn of msb addition
		if (i==31){
		obj->carryOut= (carryIn & tempSum) | carry1;
		}
		else{
			carryIn = (carryIn & tempSum) | carry1;
		}
	}
		
	// Get msb of sum, flip for sumNonNeg
		obj->sumNonNeg = ((~obj->sum>>31) & 0x1);
		
		//overflow logic
		//aNonNeg and b//NonNeg is ~aMSB and ~bMSB 
		obj->overflow = (carryIn^((~obj->aNonNeg)&0x1)) & (carryIn^((~obj->bNonNeg)&0x1));

}

