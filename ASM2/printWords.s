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
taskPrintWords:
# Task 2: Print Words
# This section convert all spaces in a string to null terminators
# and print the string in reverse to the null terminator
	la 	$t1, printWords	#t1 = &printWords
	lw 	$t2, 0($t1)		# t2 = printWords
	
	beq	$t2, $zero, taskBubbleSort	#if printWords == 0, skip to task 3
	
	la 	$s1, theString	# s1 = start
	add	$s2, $zero, $s1	# s2 = cur
	
	addi	$t3, $zero, 1	# t3 = count
printWordsLoop1:
#while (*cur != ’\0’) 
#{
#if (*cur == ’ ’)
#{
#*cur = ’\0’;
#count++;
#}
#cur++;
#}
# register:	s2 = cur	s1 = start
	lb	$t1, 0($s2)	# t1 = *cur
	beq	$t1, 0x00, printWordsReverse	# if *cur == "\0" -> skip to print reverse
	bne	$t1, 0x20, printWordsLoop1Increment	# if *cur != " ", continue 
	addi	$t1, $zero, 0x00			# else	*cur ="\0"
	sb	$t1, 0($s2)				# write change of *cur to memory
	addi	$t3, $t3, 1	# count+=1
printWordsLoop1Increment:
	addi	$s2,$s2, 1	# Move cur to the next char of theString
	j printWordsLoop1
	
printWordsReverse:
.data
printWordsMessage1:	.asciiz	"printWords: There were "
printWordsMessage2:	.asciiz	" words.\n"
.text
	la	$a0, printWordsMessage1	# print "printWords: There were "
	addi	$v0, $zero, 4		# print "printWords: There were " 50%
	syscall				# print "printWords: There were " 50%

	add	$a0, $zero, $t3		# print count
	addi	$v0, $zero, 1		# print count 50%
	syscall				# print count 50%
	
	la	$a0, printWordsMessage2	# print " words.\n"
	addi	$v0, $zero, 4		# print " words.\n" 50%
	syscall				# print " words.\n" 50%

printWordsLoop2:
	addi	$t4, $zero, 1	# t4 = 1
	slt	$t1, $s2, $s1 # t1 = cur<start
	beq	$t1, $zero, printWordsLoop2Body	# if cur<start is false, goto loop body
	j	taskBubbleSort			# else exit and goto task 3
printWordsLoop2Body:
	beq	$s1, $s2, printWordsCompoundStatementTrue	
	sub	$t5, $s2, $t4	# t5 = cur[-1]
	lb	$t5, 0($t5)
	addi	$t6, $zero, 0x00
	beq	$t5, $t6, printWordsCompoundStatementTrue
printWordsLoop2BodyDecrement:
	sub	$s2, $s2, $t4	# cur--
	j printWordsLoop2
printWordsCompoundStatementTrue:
	add	$a0, $zero, $s2
	addi	$v0, $zero, 4
	syscall
	
	la	$a0, newFeed
	addi	$v0, $zero, 4
	syscall
	
	j printWordsLoop2BodyDecrement

taskBubbleSort:

lw $ra, 4($sp) # get return address from stack
lw $fp, 0($sp) # restore the frame pointer of caller
addiu $sp, $sp, 24 # restore the stack pointer of caller
jr $ra # return to code of caller

		
	
	
	
	

	
	

