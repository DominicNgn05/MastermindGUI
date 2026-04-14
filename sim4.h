#ifndef __SIM3_H__INCLUDED__
#define __SIM3_H__INCLUDED__


typedef int WORD;	//4 byte


typedef struct InstructionFields
{
	// THESE ARE NOT ACTUALLY CONTROL BITS
	//
	// However, you have to fill them out.  Fill out *all* of these fields
	// for *all* instructions, even if it doesn't use that particular
	// field.  For instance, opcode 0 means that it is an R format
	// instruction - but you *still* must set the proper values for imm16,
	// imm32, and address.
	//
	// NOTE: imm16 is the field from the instruction,  imm32 is the
	//       sign-extended version.
	int opcode;
	int rs;
	int rt;
	int rd;
	int shamt;
	int funct;
	int imm16, imm32;
	int address;		// this is the 26 bit field from the J format
} InstructionFields;


typedef struct CPUControl
{
	// THESE ARE THE REAL CONTROL BITS
	//
	// For the ALU control bits, we will ignore the fact that the "main"
	// control produces a 2-bit value, which is combined with the funct
	// field to produce a 3-bit control (bNegate+ALUop).  We'll ignore the
	// 2-bit from the main control; we will *only* report the 3-bit
	// control that comes out of the ALU Control field.
	int ALUsrc;
	struct {
		int op;
		int bNegate;
	} ALU;

	int memRead;	//lw
	int memWrite;	//sw
	int memToReg;	//lw

	int regDst;	// rd	rt
	int regWrite;	// 0	sw beq j

	int branch; 
	int jump;	


	// EXTRA WORDS
	//
	// You are not required to use these fields.  But if you want to
	// support other instructions, some additional control bits might be
	// necessary.  You may use these fields for anything you want; the
	// testcases will ignore them.
	WORD extra1, extra2, extra3;
} CPUControl;



typedef struct ALUResult
{
	WORD result;
	int zero;

	WORD extra;
} ALUResult;

typedef struct MemResult
{
	int readVal;
} MemResult;



WORD getInstruction(WORD curPC, WORD *instructionMemory){
	return instructionMemory[curPC/4];
}
	
// TODO for Milestone 1
void extract_instructionFields(WORD instruction, InstructionFields *fieldsOut){
	fieldsOut-> opcode = (instruction>>26) & 0b111111;
	fieldsOut-> rs	= (instruction>>21)	& 0b11111;
	fieldsOut-> rt = (instruction>>16) & 0b11111;
	fieldsOut-> rd = (instruction>>11) & 0b11111;
	fieldsOut-> shamt = (instruction>>6) & 0b11111;
	fieldsOut-> funct = instruction & 0b111111;
	fieldsOut-> imm16 = instruction & 0xffff;
	fieldsOut-> imm32 = signExtend16to32(fieldsOut->imm16);
	fieldsOut-> address = instruction & 0x3FFFFFF;	//26 bits
}

// TODO for Milestone 1
int  fill_CPUControl(InstructionFields *fields, CPUControl *controlOut){
// ----------------	R-instructions	---------------------
// add	addu	sub	subu	and	or	xor	slt	
if (fields->opcode==0){
	controlOut->ALUsrc = 0;
	switch (fields->funct)
	{
	case 32:{	//add
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		break;
	}
	case 33:{	//addu
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		break;
	}
	case 34:{	//sub
		controlOut->ALU.bNegate=1;
		controlOut->ALU.op=2;
		break;
	}
	case 35:{	//subu
		controlOut->ALU.bNegate=1;
		controlOut->ALU.op=2;
		break;
	}
	case 36:{	//and
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=0;
		break;
	}
	case 37:{	// or
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=1;
		break;
	}
	case 38:{	// xor
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=4;
		break;
	}
	case 42:{	// slt
		controlOut->ALU.bNegate=1;
		controlOut->ALU.op=3;

		break;
	}
	default:{	// not recognized
		return 0;
		break;
	}
	}
		// R instruction don't care about memControl
	controlOut->memToReg=0;
	controlOut->memRead=0;
	controlOut->memWrite=0;
	// R instruction always read from rd
	controlOut->regDst = 1;
	// R instruction always set regWrite to 1
	controlOut->regWrite = 1;
	// R instruction don't care about branch and jump
	controlOut->branch=0;
	controlOut->jump=0;
}
// -------------------	I or J instruction	----------------------
else{
//	--------------------	I instruction	----------------
	switch (fields->opcode)
	{
	case 8:{	// addi
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		controlOut->ALUsrc=1;
		// does not interact with memory
		controlOut->memRead=0;
		controlOut->memWrite=0;
		controlOut->memToReg=0;
		// addi writes to rt
		controlOut->regDst = 0;
		controlOut->regWrite = 1;
		// addi does not interact with jump or branch
		controlOut->jump=0;
		controlOut->branch=0;
		break;
	}
	case 9:{	// addiu
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		controlOut->ALUsrc=1;
		// does not interact with memory
		controlOut->memRead=0;
		controlOut->memWrite=0;
		controlOut->memToReg=0;
		// addiu writes to register
		controlOut->regDst = 0;
		// store to rt
		controlOut->regWrite = 1;
		// addiu does not interact with jump or branch
		controlOut->jump=0;
		controlOut->branch=0;
		break;
	}
	case 10:{	// slti
		controlOut->ALU.bNegate=1;
		controlOut->ALU.op=3;
		controlOut->ALUsrc=1;
		// does not interact with memory
		controlOut->memRead=0;
		controlOut->memWrite=0;
		controlOut->memToReg=0;
		// slti writes to register
		controlOut->regDst = 0;
		controlOut->regWrite = 1;
		// slti does not interact with jump or branch
		controlOut->jump=0;
		controlOut->branch=0;
		break;
	}
	case 23:{	//lw
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		controlOut->ALUsrc=1;
		// read from memory
		controlOut->memRead=1;
		controlOut->memWrite=0;
		controlOut->memToReg=1;
		// lw writes to register
		// but takes it from rt, not rd
		controlOut->regDst=0;
		controlOut->regWrite = 1;
		// lw does not interact with jump or branch
		controlOut->jump=0;
		controlOut->branch=0;
		break;
	}
	case 43:{	//sw
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		controlOut->ALUsrc=1;
		//write to memory
		controlOut->memRead=0;
		controlOut->memWrite=1;
		controlOut->memToReg=0;
		// sw does not write to register
		controlOut->regDst=0;	
		controlOut->regWrite=0;
		// sw does not interact with jump or branch
		controlOut->jump=0;
		controlOut->branch=0;
		break;
	}
	case 4:{	// beq
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		controlOut->ALUsrc=0;
		//does not interact with memory
		controlOut->memRead=0;
		controlOut->memWrite=0;
		controlOut->memToReg=0;
		//does not write to register
		controlOut->regDst=0;	
		controlOut->regWrite=0;
		// interact with branch
		controlOut->branch=1;
		// does not interact with jump
		controlOut->jump = 0;
		break;
	}
	//	---------------- J instructions	-------------
	case 2:{	// j
		controlOut->ALU.bNegate=0;
		controlOut->ALU.op=2;
		controlOut->ALUsrc=1;

		//does not interact with memory
		controlOut->memRead=0;
		controlOut->memWrite=0;
		controlOut->memToReg=0;

		//does not write to register
		controlOut->regDst=0;	
		controlOut->regWrite=0;

		// does not interact with branch
		controlOut->branch=0;

		// interact with jump
		controlOut->jump = 1;
		break;
	}
	default:{ // not recognized
		return 0;
	}

	}
}
return 1;
}


WORD getALUinput1(CPUControl *controlIn,
                  InstructionFields *fieldsIn,
                  WORD rsVal, WORD rtVal, WORD reg32, WORD reg33,
                  WORD oldPC){
					// ALU input 1 always read from rs
						return rsVal;
				  }
WORD getALUinput2(CPUControl *controlIn,
                  InstructionFields *fieldsIn,
                  WORD rsVal, WORD rtVal, WORD reg32, WORD reg33,
                  WORD oldPC){
					// R format reads from rt
					// branch instruction also reads from rt
					if(controlIn->ALUsrc==0){
						return rtVal;
					}
					// I format reads from imm32
					else{
						return fieldsIn->imm32;
					}
				  }
				  

void execute_ALU(CPUControl *controlIn,
                 WORD input1, WORD input2,
                 ALUResult  *aluResultOut){
					if (controlIn->ALU.op==0){	// and
						aluResultOut->result= input1 & input2;
						aluResultOut->zero=0;
					}
					if (controlIn->ALU.op==1){	// or
						aluResultOut->result= input1 | input2;
						aluResultOut->zero=0;
					}
					if (controlIn->ALU.op==2){	// add
						aluResultOut->result= input1 + input2 - 2*input2*(controlIn->ALU.bNegate);
					}
					if (controlIn->ALU.op==3){
						aluResultOut->result= (input1-input2)>>31 &1;
						aluResultOut->zero=0;
					}
					if (controlIn->ALU.op==4){
						aluResultOut->result = (input1 | input2) - (input1 & input2);
						aluResultOut->zero=0;

					}
					if (aluResultOut->result==0){
						aluResultOut->zero=1;
					}
					else{
						aluResultOut->zero=0;
					}
				 }

void execute_MEM(CPUControl *controlIn,
                 ALUResult  *aluResultIn,
                 WORD        rsVal, WORD rtVal,
                 WORD       *memory,
                 MemResult  *resultOut)
				 {
					if (controlIn->memRead == 1){
						resultOut->readVal = memory[aluResultIn->result>>2];
					}

					else if (controlIn->memWrite==1){
						resultOut->readVal = 0;
						memory[aluResultIn->result>>2] = rtVal;
					}
				 }

WORD getNextPC(InstructionFields *fields, CPUControl *controlIn, int aluZero,
               WORD rsVal, WORD rtVal,
               WORD oldPC){
				// default
				if (!(controlIn->jump==1 || controlIn->branch ==1)){
					return oldPC+4;
				}
				else{
					if (controlIn->branch==1){	// beq
						if (aluZero ==1){	// if equal, jump to branch
							return oldPC + 4 + (fields->imm32 << 2);
						}
						else{	// if not equal, continue
							return oldPC+4;
						}
					}
					else{	// not beq, then has to be a J format
						return (((fields->address)<<2) & 0x0FFFFFFF) | ((oldPC+4) & 0xF0000000);
					}
				}
			   }

void execute_updateRegs(InstructionFields *fields, CPUControl *controlIn,
                        ALUResult  *aluResultIn, MemResult *memResultIn,
                        WORD       *regs){
							WORD destination = fields->rt;
							if (controlIn->regDst==1){
								destination = fields->rd;
							}
							if (controlIn->regWrite==1){
								if(controlIn->memToReg==0){
									regs[destination]=aluResultIn->result;
								}
								else if(controlIn->memToReg==1){
									regs[destination]=memResultIn->readVal;
								}
							}
						}



/* HELPER FUNCTIONS THAT YOU CAN CALL */

static inline WORD signExtend16to32(int val16)
{
	if (val16 & 0x8000)
		return val16 | 0xffff0000;
	else
		return val16;
}


#endif

