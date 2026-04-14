# @author	Khanh Ngoc Nguyen
# fileName	asm2.s
# course	CSC 252
# This file inplements code that reads 3 control variable and performs the 3 tasks accordingly
# task1: Print Ints
# task2: print Words
# task3: Bubble Sort
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
	lb	$s0, 0($t1)	# s0 = printInts

	la 	$t1, printInts_howToFindLen	# t1 = &printInts_howToFindLen
	lh	$s1, 0($t1)	# s1 = printInts_howToFindLen

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
# Task 2: Print Words
# This section convert all spaces in a string to null terminators
# and print the string in reverse to the null terminator
	la 	$t1, printWords	#t1 = &printWords
	lb 	$t2, 0($t1)		# t2 = printWords
	
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
	addi	$s7, $zero, 0x00	# s7 = \0
	beq	$t1, $s7, printWordsReverse	# if *cur == "\0" -> skip to print reverse
	addi	$s6, $zero, 0x20	# s6 = " "
	bne	$t1, $s6, printWordsLoop1Increment	# if *cur != " ", continue "
	sb	$s7, 0($s2)				# else	*cur ="\0
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
# Task 3 Bubble Sort:
# Sort an array of integer using bubble sort
	la	$t0, bubbleSort	# t1 = &bubbleSort
	lb	$t1, 0($t0)	# t1 = bubbleSort
	
	beq	$t1, $zero, end	# if bubbleSort ==0, skip to end
	
	la	$t1, intsArray_len	# t1 = &intsArray_len
	lw	$s1, 0($t1)	# s1 = intsArray_len
	la	$s2, intsArray	# s2 = &intArray[0]
	
	addi	$t0, $zero, 1	# t0 = 1
	sub	$t4, $s1, $t0	# t4 = intsArray - 1
	add	$t2, $zero, $zero	# t2 = i
.data
taskBubbleSortMessage:	.asciiz	"Swap at: "
.text
taskBubbleSortLoopi:
#for (int i=0; i<intsArray_len; i++)
#for (int j=0; j<intsArray_len-1; j++)
#if (intsArray[j] > intsArray[j+1])
#{
#printf("Swap at: %d\n", j);
#int tmp = intsArray[j];
#intsArray[j] = intsArray[j+1];
#intsArray[j+1] = tmp;
#}
	slt	$t1, $t2, $s1	# t1 = (i<intsArray_len)
	beq	$t1, $zero, end	# i< intsArray_len is false -> end
	 
	add	$t3, $zero, $zero	# t3 = j
taskBubbleSortLoopj:
#for (int j=0; j<intsArray_len-1; j++)
#if (intsArray[j] > intsArray[j+1])
#{
#printf("Swap at: %d\n", j);
#int tmp = intsArray[j];
#intsArray[j] = intsArray[j+1];
#intsArray[j+1] = tmp;
#}
	slt	$t1, $t3, $t4	# t1 = j<intsArray_len-1
	beq	$t1, $zero, taskBubbleSortLoopiIncrement	# j<intsArray_len-1 is false -> increment i
	
	sll	$t5, $t3, 2	# t5 = 4*j
	add	$t0, $t5, $s2	# t0 = &intsArray[j]
	addi	$t1, $t0, 4	# t1 = $intsArray[j+1]
	
	lw	$t6, 0($t0)	# t6 = intsArray[j]
	lw	$t7, 0($t1)	# t7 = intsArray[j+1]
	
	slt	$t5, $t7, $t6	# t5 = intsArray[j+1]< intsArray[j]
	beq	$t5, $zero, taskBubbleSortLoopjIncrement	# if intsArray[j+1]< intsArray[j] is false, skip to taskBubbleSortLoopjIncrement
	
	la	$a0, taskBubbleSortMessage	# print "Swap at: "
	addi	$v0, $zero, 4	# print "Swap at: " 50%
	syscall			# print "Swap at: " 50%
	
	add	$a0, $zero, $t3	# print j
	addi	$v0, $zero, 1	# print j 50%
	syscall			# print j 50%
	
	la	$a0, newFeed	# print newFeed
	addi	$v0, $zero, 4	# print newFeed 50%
	syscall			# print newFeed 50%
	
	sw	$t7, 0($t0)	# intsArray[j] <- intsArray[j+1]
	sw	$t6, 0($t1)	# intsArray[j+1] <- intsArray[j]
taskBubbleSortLoopjIncrement:
	addi	$t3, $t3, 1	# j++
	j	taskBubbleSortLoopj
taskBubbleSortLoopiIncrement:
	addi	$t2, $t2, 1	# i++
	j	taskBubbleSortLoopi

end:
lw $ra, 4($sp) # get return address from stack
lw $fp, 0($sp) # restore the frame pointer of caller
addiu $sp, $sp, 24 # restore the stack pointer of caller
jr $ra # return to code of caller

		
	
	
	
	

	
	

