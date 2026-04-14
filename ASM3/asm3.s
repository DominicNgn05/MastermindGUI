# @author	Khanh Ngoc Nguyen
# fileName	asm3.s
# course	CSC 252
# This file inplements 4 functions
# collatz_line: continuously divide a number by 2 until it becomes odd
# collatz: preform the procedure 3x+1 on a number
# percentSearch: search for the first "%" in an array
# letterTree: print a specific pattern of characters
.data
newFeed:	.asciiz	"\n"
space:	.asciiz	" "
debug: .asciiz "got HEREEEEEEEEEEEEEEEE"
.text
# collatz_line (int val)
# This function takes a positive integer input 
# while the integer is even:
# divide it by two until it becomes an odd number
# and return that value 
# @param val the positive integer
# @return the odd nummber after dividing the input by 2 repeatedly. 
.globl collatz_line
collatz_line:
# Function Prologue
	addiu	$sp, $sp, -24	# allocate 24 bit stack space
	sw	$fp, 0($sp)	# save fp
	sw	$ra, 4($sp)	# save ra
	addiu	$fp, $sp, 20	# adjust new frame pointer

# Function Body

# if value is odd, print and return
# the parity is checked by masking off bit 1 - 31, keeping bit 0(lsb)
# this is done via mask 0x0001

	add	$t0, $zero, $a0	# t0 = cur = val
	andi	$t2, $a0, 1	# mask off bit 1-31 of a0, save to t2
	beq	$t2, $zero, collatz_line_even		# if val is even, jump to collatz_line_even
	
	addi	$v0, $zero, 1	# print val
	syscall			# print cur 50%
	
	addi	$v0, $zero, 4	# print newFeed
	la	$a0, newFeed	# print newFeed 50%
	syscall			# print newFeed 50%
	
	add	$v0, $zero, $t0	# return val
	j	collatz_line_Epilogue
collatz_line_even:
# if val is even
	addi	$v0, $zero, 1	# print val
	syscall			# print val 50%

# while (cur % 2 == 0)
# {
# cur /= 2;
# printf(" %d", cur);
# }
###
# REGISTER:
# t0 = cur
collatz_line_loop:
	bne	$t2, $zero, collatz_line_return		# if cur is odd, return
	srl	$t0, $t0, 1	# cur /=2
	andi	$t2, $t0, 1	# t2 = lsb of cur
	
	addi	$v0, $zero, 4	# print space
	la	$a0, space	# print space 50%
	syscall			# print space 50%
	
	addi	$v0, $zero, 1	# print cur
	add	$a0, $zero, $t0	# print cur 50%
	syscall			# print cur 50%
	j	collatz_line_loop
	

collatz_line_return:
	addi	$v0, $zero, 4	# print newFeed
	la	$a0, newFeed	# print newFeed 50%
	syscall			# print newFeed 50%
	
	add	$v0, $zero, $t0	# return cur

# Function Epilogue
collatz_line_Epilogue:	
	lw $ra, 4($sp) # get ra from stack
	lw $fp, 0($sp) # get fp from stack
	addiu $sp, $sp, 24 # deallocate stack space	
	jr $ra # return to code of caller
	
	
	
	
# collatz (int val)
# this function performs 3x+1 conjecture on an integer
# and print out the value after every step

.globl collatz
collatz: 
# Function Prologue
	addiu	$sp, $sp, -24	# allocate 24 bit stack space
	sw	$fp, 0($sp)	# save fp
	sw	$ra, 4($sp)	# save ra
	addiu	$fp, $sp, 20	# adjust new frame pointer
# save s0, s1, s1, s3
	addiu	$sp, $sp, -4	# save s0 to stack
	sw	$s0, 0($sp) 
	
	addiu	$sp, $sp, -4	# save s1 to stack
	sw	$s1, 0($sp) 
	
	addiu	$sp, $sp, -4	# save s2 to stack
	sw	$s2, 0($sp) 
	
	addiu	$sp, $sp, -4	# save s3 to stack
	sw	$s3, 0($sp) 
# Function Body
# REGISTER:
# cur = s0
# calls = s1
	add	$s3, $a0, $zero	# s3 = val
	add	$s0, $zero, $a0	# cur = val
	addi	$s1, $zero, 0	# calls = 1
	
	add	$a0, $s0, $zero	# pass parameter collatz_line(cur)
	jal	collatz_line	# call collatz_line(cur)
	add	$s0, $v0, $zero	# cur = collatz_line(cur)
	
	addi	$s2, $zero, 1	# s2 =1
# while (cur != 1)
# {
# cur = 3*cur+1;
# cur = collatz_line(cur);
# calls++;
# }
collatz_loop:
	beq	$s0, $s2, collatz_print
	add	$t0, $s0, $s0	# cur+=cur
	add	$s0, $t0, $s0	# cur = 3*cur
	addi	$s0, $s0, 1	# cur = 3*cur +1
	
	add	$a0, $s0, $zero	# pass parameter collatz_line(cur)
	jal	collatz_line	# call collatz_line(cur)
	add	$s0, $v0, $zero	# cur = collatz_line(cur)
	
	addi	$s1, $s1, 1	# calls++
	j	collatz_loop
	
collatz_print:
.data
collatz_text_1:	.asciiz	"collatz("
collatz_text_2:	.asciiz	") completed after "
collatz_text_3: .asciiz	" calls to collatz_line().\n"
.text
	addi	$v0, $zero, 4	# print "collatz("
	la	$a0, collatz_text_1	# "collatz(" 50%
	syscall	# print "collatz(" 50%
	
	addi	$v0, $zero, 1	# print val
	add	$a0, $s3, $zero	# print val 50%
	syscall	# print val 50%
	
	addi	$v0, $zero, 4	# print ") completed after "
	la	$a0, collatz_text_2	# ") completed after " 50%
	syscall	# print ") completed after " 50%
	
	addi	$v0, $zero, 1	# print calls
	add	$a0, $s1, $zero	# print calls 50%
	syscall	# print calls 50%
	
	addi	$v0, $zero, 4	# print " calls to collatz_line().\n"
	la	$a0, collatz_text_3	# " calls to collatz_line().\n" 50%
	syscall	# print " calls to collatz_line().\n" 50%
	
# Function Epilogue
	# restore s3, s2, s1, s0
	lw	$s3, 0($sp)	# restore s3
	addiu	$sp, $sp, 4 
	
	lw	$s2, 0($sp)	# restore s2
	addiu	$sp, $sp, 4 
	
	lw	$s1, 0($sp)	# restore s1
	addiu	$sp, $sp, 4 
	
	lw	$s0, 0($sp)	# restore s0
	addiu	$sp, $sp, 4 

	lw $ra, 4($sp) # get ra from stack
	lw $fp, 0($sp) # get fp from stack
	addiu $sp, $sp, 24 # deallocate stack space	
	jr $ra # return to code of caller


# percentSearch()
# this function takes a String as parameter
# and search the String for the first instance of the character "%"
# returns the index of that instance
# returns -1 if not found
.globl percentSearch
percentSearch:
# Function Prologue
	addiu	$sp, $sp, -24	# allocate 24 bit stack space
	sw	$fp, 0($sp)	# save fp
	sw	$ra, 4($sp)	# save ra
	addiu	$fp, $sp, 20	# adjust new frame pointer
	
# Function Body
.data
percentSign:	.ascii	"%"
nullTerm:	.asciiz	""
.text
	la	$t0, percentSign
	lb	$t0, 0($t0)	# t0 = "%"
	
	la	$t1, nullTerm
	lb	$t1, 0($t1)	# t1 = nullTerminator

	add	$t2, $zero, $zero	# t2 = index = 0
percentSearch_loop:
	lb	$t3, 0($a0)	# t3 = string[index]
	beq	$t3, $t1, percentSearch_fail	# if string[index] == null -> fail
	beq	$t3, $t0, percentSearch_success	# if string[index] == "%" -> Success
	addi	$t2, $t2, 1	# index += 1
	addi	$a0, $a0, 1	# &array += 1
	j	percentSearch_loop
	
percentSearch_fail:
	addi	$v0, $zero, -1
	j	percentSearch_epilogue
percentSearch_success:
	add	$v0, $zero, $t2
	j	percentSearch_epilogue

# Function Epilogue
percentSearch_epilogue:
	lw $ra, 4($sp) # get ra from stack
	lw $fp, 0($sp) # get fp from stack
	addiu $sp, $sp, 24 # deallocate stack space	
	jr $ra # return to code of caller
	
# letterTree(int step)
# this function print out a pattern of letters where
# the first character is printed once, and each subsequent characters
# are printed one additional time compared to the previous, 
# the printed characters are chosen from an array of character
# starting at index 0, and increases each time by the parameter step

.globl letterTree
letterTree:
# Function Prologue
	addiu	$sp, $sp, -24	# allocate 24 bit stack space
	sw	$fp, 0($sp)	# save fp
	sw	$ra, 4($sp)	# save ra
	addiu	$fp, $sp, 20	# adjust new frame pointer
	
# save s registers 0, 1, 2
	addiu	$sp, $sp, -4	# decrement stack pointer by 1 byte
	sw	$s0, 0($sp)	# store s0 to stack
	
	addiu	$sp, $sp, -4	# decrement stack pointer by 1 byte
	sw	$s1, 0($sp)	# store s0 to stack
	
	addiu	$sp, $sp, -4	# decrement stack pointer by 1 byte
	sw	$s2, 0($sp)	# store s0 to stack
	

# function body
.data
zero:	.ascii	"\0"
.text
	add	$s0, $a0, $zero	# s0 = step
	add	$s1, $zero, $zero	# s1 = count = 0
	add	$s2, $zero, $zero	# s2 = pos = 0
	
#while (true) 
#{
#char c = getNextLetter(pos);
#if (c == ’\0’) // this is literally *ZERO*
#if (c == ’z’) count += 3; break;
#for (int i=0; i<=count; i++)
#printf("%c", c); // use syscall 11
#printf("\n");
#count++;
#pos += step;
#}
letterTree_while_loop:
	add	$a0, $zero, $s2	# pass parameter pos to getNextLetter(int)
	jal	getNextLetter	# call getNextLetter(pos)
	
	la	$t0, zero	# t0 = &zero
	lb	$t0, 0($t0)	# t0 = "\0"
	
	add	$t1, $zero, $v0	# t1 = c = getNextLetter(pos)
	beq	$t1, $t0, letterTree_return
	
	add	$t2, $zero, $zero	# t2 = i
letterTree_for_Loop:
	slt	$t3, $s1, $t2	# t3 = count < i
	beq	$t3, $zero, letterTree_for_loop_body 	# if (count <i) is false -> enter loop
	j	letterTree_while_loop_increment
letterTree_for_loop_body:
	addi	$v0, $zero, 11	# print c
	add	$a0, $zero, $t1	# print c 50%
	syscall			# print c 50%
	
	addi	$t2, $t2, 1	# i ++
	j 	letterTree_for_Loop
letterTree_while_loop_increment:
	addi	$v0, $zero, 4	# print newFeed
	la	$a0, newFeed	# print newFeed 50%
	syscall			# print newFeed 50%
	
	addi	$s1, $s1, 1	# count ++
	add	$s2, $s2, $s0	# pos += step
	j	letterTree_while_loop
letterTree_return:
	add	$v0, $zero, $s2	# return pos
# Function Epilogue
	lw	$s2, 0($sp)	# restore s2
	addiu	$sp, $sp, 4	# increment stack pointer
	
	lw	$s1, 0($sp)	# restore s1
	addiu	$sp, $sp, 4	# increment stack pointer
	
	lw	$s0, 0($sp)	# restore s0
	addiu	$sp, $sp, 4	# increment stack pointer
	
	
	lw $ra, 4($sp) # get ra from stack
	lw $fp, 0($sp) # get fp from stack
	addiu $sp, $sp, 24 # deallocate stack space	
	jr $ra # return to code of caller
	

	
