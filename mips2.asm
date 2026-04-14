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
taskBubbleSort:
	la	$t1, bubbleSort	# t1 = &bubbleSort
	lw	$t1, 0($t1)	# t1 = bubbleSort
	
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
	addi	$t1, $t6, 4	# t1 = $intsArray[j+1]
	
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
	
	sw	$t7, 0($t0)	# intsArray[j] <- intsArray[j+1]
	sw	$t6, 0($t1)	# intsArray[j+1] <- intsArray[j]
taskBubbleSortLoopjIncrement:
	addi	$t3, $t3, 1
	j	taskBubbleSortLoopj
taskBubbleSortLoopiIncrement:
	addi	$t2, $t2, 1
	j	taskBubbleSortLoopi

end:
	lw $ra, 4($sp) # get return address from stack
	lw $fp, 0($sp) # restore the frame pointer of caller
	addiu $sp, $sp, 24 # restore the stack pointer of caller
	jr $ra # return to code of caller

		
	
	
	
	

	
	

