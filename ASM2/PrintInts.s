.text
.globl studentMain
studentMain:
	addiu	$sp, $sp, -24 # allocate stack space -- default of 24 here
	sw	$fp, 0($sp) # save frame pointer of caller
	sw	$ra, 4($sp) # save return address
	addiu	$fp, $sp, 20 # setup frame pointer of main
.data
newFeed:	.asciiz "\n"
.text
taskPrintInts:
# Task 1: Print Ints
# This section prints to standard IO the content of a string. 
# There are 3 different modes that the content is printed
	la	$t1, printInts	# t1 = &printInts
	lw	$s0, 0($t1)	# s0 = printInts

	la 	$t1, printInts_howToFindLen	# t1 = &printInts_howToFindLen
	lw	$s1, 0($t1)	# s1 = printInts_howToFindLen

	beq	$s0, $zero, taskPrintWords	# if (printInts==0) -> Skip Task, go to task 2

	addi 	$t1, $zero, 2	# t1 = 2
	
	la	$s2, intsArray	# s2 = &intsArray[0]
	
	beq 	$s1, $t1, printInts_NullTerminator	# if printInts_howToFindLen == 2, -> Null terminator



	beq	$s1, $zero, Mode_Array_len	#if printInts_howToFindLen == 0, -> Array_len
# Mode  intsArray_END - intsArray
	la	$t2, intsArray_END	# t2 = &intsArray_END

	sub	$t3, $t2, $s2	# count = t3 = intsArrayEND - intsArray
	sra	$t3, $t3, 2	# count = count/4, 
	j printInts_printByLen

Mode_Array_len:
	la	$t1, intsArray_len	# t1 = &intsArray_len
	lw	$t3, 0($t1)		# t3 = count

# At this point, t3 holds count
printInts_printByLen:
.data
printIntsMessage1:	.asciiz "printInts: About to print "
printIntsMessage2:	.asciiz " elements.\n"

.text
# printf("printInts: About to print %d elements.\n", count);
	la	$a0, printIntsMessage1	# print "printInts: About to print "
	addi	$v0, $zero, 4		# print "printInts: About to print " 50%
	syscall				# print "printInts: About to print " 50%

	add	$a0, $zero, $t3		# print count
	addi	$v0, $zero, 1		# print count 50%
	syscall				# print count 50%
	
	la	$a0, printIntsMessage2	# print " elements.\n"
	addi	$v0, $zero, 4		# print " elements.\n" 50%
	syscall				# print " elements.\n" 50%

#Loop
# for (int i =0; i<count; i++)
#	printf("%d\n", intsArray[i]

	add	$t4, $zero, $zero	# t4 = i =0
printInts_forLoop:
	slt	$t5, $t4, $t3	# t5 = (i < count)
	beq	$t5, $zero, taskPrintWords  # if false, task finished -> task 2
	# calculate index address
	add	$t1, $zero, $t4	# t1 = i
	sll	$t1, $t1, 2	# t1 = <<i
	# print
	add	$t6, $t1, $s2
	lw	$a0, 0($t6)	# print intsArray[i]
	addi	$v0, $zero, 1	# print intsArray[i] 50%
	syscall			# print intsArray[i] 50%
	la	$a0, newFeed	# print "\n"
	addi	$v0, $zero, 4		# print "\n" 50%
	syscall				# print "\n" 50%
	
	addi 	$t4, $t4, 1
	j	printInts_forLoop

printInts_NullTerminator:
	# $s2 = &intsArray[0]
.data
printIntsMessage4:	.asciiz	"printInts: About to print an unknown number of elements. Will stop at a zero element.\n"
.text
	la	$a0, printIntsMessage4 # printf("printInts: About to print an unknown number of elements. "	
	addi	$v0, $zero, 4			# 		"Will stop at a zero element.\n");
	syscall				
printInts_whileLoop:
#Loop
#while (*cur != 0)
#{
#printf("%d\n", *cur);
#cur++;
#}
	lw	$t1, 0($s2)		# t1 = *cur
	beq	$t1, $zero, taskPrintWords	# print *cur
	add	$a0, $zero, $t1		# print *cur 50%
	addi	$v0, $zero, 1		# print *cur 50%
	syscall
	
	la	$a0, newFeed	# print "\n"
	addi	$v0, $zero, 4		# print "\n" 50%
	syscall				# print "\n" 50%
	
	addi 	$s2, $s2, 4		# cur++
	j printInts_whileLoop
taskPrintWords:

lw $ra, 4($sp) # get return address from stack
lw $fp, 0($sp) # restore the frame pointer of caller
addiu $sp, $sp, 24 # restore the stack pointer of caller
jr $ra # return to code of caller

	

