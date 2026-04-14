.text
.globl studentMain
studentMain:
	addiu 	$sp, $sp, -24 # allocate stack space -- default of 24 here
	sw 	$fp, 0($sp) # save frame pointer of caller
	sw 	$ra, 4($sp) # save return address
	addiu 	$fp, $sp, 20 # setup frame pointer of main
###########################################################################
	la 	$t1, math	# t1 = $math
	lw 	$s6, 0($t1)	# s6 = math
	
	la 	$t1, addUp	# t1 = &addUp
	lw 	$s7, 0($t1)	# s1 = addUp
	
	la 	$t1, big	# t1 = &big
	lw 	$t8, 0($t1)	# t8 = big
	
	la 	$t1, print	# t1 = &print
	lw 	$t9, 0($t1)	# t9 = print
	
taskHub:
	la 	$t1, alpha	# t1 = &alpha
	lw 	$s1, 0($t1)	# s1 = alpha
	
	la 	$t1, beta	# t1 = &beta
	lw 	$s2, 0($t1) 	# s2 = beta
	
	la 	$t1, gamma	# t1 = &gamma
	lw 	$s3, 0($t1) 	# s3 = gamma
	
	la 	$t1, delta	# t1 = &delta
	lw 	$s4, 0($t1) 	# s4 = delta
	
	la 	$t1, epsilon	# t1 = &epsilon
	lw 	$s5, 0($t1) 	# s5 = epsilon

	bne 	$s6, $zero, mathTask
	bne	$s7, $zero, addUpTask
	bne	$t8, $zero, bigTask
	bne	$t9, $zero, printTask
	
.data 
memoryVarDump:	.asciiz "Testcase Variable Dump:"
.text
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall	
	
	la 	$a0, memoryVarDump
	addi 	$v0, $zero, 4
	syscall	
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 32
	syscall
	
	addi 	$v0, $zero, 1
	add	$a0, $zero, $s1
	syscall	
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 32
	syscall
	
	addi 	$v0, $zero, 1
	add	$a0, $zero, $s2
	syscall
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 32
	syscall 
	
	addi 	$v0, $zero, 1
	add	$a0, $zero, $s3
	syscall
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 32
	syscall 
	
	addi 	$v0, $zero, 1
	add	$a0, $zero, $s4
	syscall
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 32
	syscall 
	
	addi 	$v0, $zero, 1
	add	$a0, $zero, $s5
	syscall
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall				# print new linefeed
	
	addi $v0, $zero, 10
	syscall			#exit
	
	

# This Section implement Task 1
# $s1 = alpha
# $s2 = beta
# $s3 = gamma
# $s4 = delta
# $s5 = epsilon
mathTask:
# Switch (delta){
#	case "+" -> addition
#	case "-" -> subtraction
#	case "&" -> bitwiseAND
#	case "|" -> addition 
#	}
	addi 	$t1, $zero, 43
	beq 	$s4, $t1, OP2Addition		# if (delta == "+"), then go to addition
	
	addi 	$t1, $zero, 45
	beq	$s4, $t1, OP2Subtraction	# if (delta == "-", then go to subtraction
	
	addi 	$t1, $zero, 38
	beq	$s4, $t1, OP2BitwiseAND		# if (delta == "&", then go to bitwiseAND
	
	addi 	$t1, $zero, 124
	beq	$s4, $t1, OP2BitwiseOR		# if (delta == "-", then go to bitwiseOR
	
# Set t3 = gamma OP2 epsilon
OP2Addition:
	add 	$t3, $s3, $s5		# t3 = gamma + epsilon
	j OP1
	
OP2Subtraction:
	sub	$t3, $s3, $s5		# t3 = gamma - epsilon
	j OP1

OP2BitwiseAND:
	and 	$t3, $s3, $s5		# t3 = gamma AND epsilon
	j OP1

OP2BitwiseOR:
	or 	$t3, $s3, $s5		# t3 = gamma OR epsilon
	j OP1

OP1:

# Switch (beta){
#	case "+" -> addition
#	case "-" -> subtraction
#	case "&" -> bitwiseAND
#	case "|" -> addition
#	}
	addi 	$t1, $zero, 43
	beq 	$s2, $t1, OP1Addition		# if (beta == "+"), then go to addition
	
	addi 	$t1, $zero, 45
	beq	$s2, $t1, OP1Subtraction	# if (beta == "-", then go to subtraction
	
	addi 	$t1, $zero, 38
	beq	$s2, $t1, OP1BitwiseAND		# if (beta == "&", then go to bitwiseAND
	
	addi 	$t1, $zero, 124
	beq	$s2, $t1, OP1BitwiseOR		# if (beta == "-", then go to bitwiseOR
	
# syscall mathPrint "ERROR"
.data
errorMSG:	.asciiz "ERROR\n"

.text
	la	$a0, errorMSG			# t2 = &errorMSG
	addi	$v0, $zero, 4
	syscall					# print "ERROR"
	add 	$s6, $zero, $zero		# Adjust task1 Flag to 0	
	j taskHub				# return to taskHub
	
# Set t4 = alpha OP1 (gamma OP2 epsilon)
OP1Addition:
	add 	$t4, $s1, $t3		# t3 = aplha + (gamma OP2 epsilon)
	j mathPrint
	
OP1Subtraction:
	sub	$t4, $s1, $t3		# t3 = alpha - (gamma OP2 epsilon)
	j mathPrint

OP1BitwiseAND:
	and 	$t4, $s1, $t3		# t3 = alpha AND (gamma OP2 epsilon)
	j mathPrint

OP1BitwiseOR:
	or 	$t4, $s1, $t3		# t3 = alpha OR (gamma OP2 epsilon)
	j mathPrint

mathPrint:
	addi	$v0, $zero, 1
	add	$a0, $zero, $t4
	syscall					# print aplha OP1 (gamma OP2 epsilon)
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall					# print new linefeed
	add 	$s6, $zero, $zero		# Adjust task1 Flag to 0
	j taskHub
	
	

# This Section implement Task 2
# $s1 = alpha
# $s2 = beta
# $s3 = gamma
# $s4 = delta
# $s5 = epsilon
addUpTask:
	add	$t1, $s1,$s2	# t1 = alpha + beta
	la	$t2, beta	# t2 = &beta
	sw	$t1, 0($t2)		# beta = alpha + beta
	
	add	$t1, $t1, $s3	# t1 = alpha + beta + gamma
	la	$t2, gamma	# t2 = &gamma
	sw	$t1, 0($t2)		# gamma = alpha + beta + gamma
	
	add	$t1, $t1, $s4	# t1 = alpha + beta + gamma + delta
	la	$t2, delta	# t2 = &delta
	sw	$t1, 0($t2)		# delta = alpha + beta + gamma + delta
	
	add	$t1, $t1, $s5	# t1 = alpha + beta + gamma + delta + epsilon
	la	$t2, epsilon	# t2 = &epsilon
	sw	$t1, 0($t2)		# epsilon = alpha + beta + gamma + epsilon
	
#addUp Print
.data
addUpMSG:	.asciiz "ADD UP COMPLETE\n"

.text
	la	$a0, addUpMSG				
	addi	$v0, $zero, 4
	syscall					# print "ADD UP COMPLETE"
	add 	$s7, $zero, $zero		# Adjust task2 Flag to 0
	j taskHub				# return to taskHub

# This Section implement Task 3
# $s1 = alpha
# $s2 = beta
# $s3 = gamma
# $s4 = delta
# $s5 = epsilon
bigTask:
.data
bigStartMSG:	.asciiz "BIG START\n"

.text
	la	$a0, bigStartMSG	
	addi	$v0, $zero, 4
	syscall				# print "BIG START"
	
	addi $t1, $zero, 10
	slt $t2,  $t1,  $s1		# t2 = (10 < alpha)
	bne $t2, $zero, printAlpha	# if (10<alpha) -> printAlpha
	j bigContinueBeta		# else -> continue beta
	
printAlpha:
	addi	$v0, $zero, 1
	add	$a0, $zero, $s1
	syscall				# print alpha
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall				# print new linefeed		
	j bigContinueBeta

bigContinueBeta:
	slt $t2,  $t1,  $s2		# t2 = (10 < beta)
	bne $t2, $zero, printBeta	# if (10<beta) -> printBeta
	j bigContinueGamma		# else -> continue gamma
printBeta:
	addi	$v0, $zero, 1
	add	$a0, $zero, $s2	
	syscall				# print beta
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall				# print new linefeed
	j bigContinueGamma

bigContinueGamma:
	slt $t2,  $t1,  $s3		# t1 = (10 < gamma)
	bne $t2, $zero, printGamma	# if (10<gamma) -> printGamma
	j bigContinueDelta		# else -> continue delta
printGamma:
	addi	$v0, $zero, 1
	add	$a0, $zero, $s3
	syscall				# print gamma
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall				# print new linefeed
	j bigContinueDelta

bigContinueDelta:
	slt $t2,  $t1,  $s4		# t1 = (10 < delta)
	bne $t2, $zero, printDelta	# if (10<delta) -> printDelta
	j bigContinueEpsilon		# else -> continue epsilon
printDelta:
	addi	$v0, $zero, 1
	add	$a0, $zero, $s4
	syscall				# print Delta
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall				# print new linefeed
	j bigContinueEpsilon

bigContinueEpsilon:
	slt $t2,  $t1,  $s5		# t1 = (10 < epsilon)
	bne $t2, $zero, printEpsilon	# if (10<epsilon) -> printDelta
	j printBigEnd			# else -> printBigEnd
printEpsilon:
	addi	$v0, $zero, 1
	add	$a0, $zero, $s5
	syscall				# print Epsilon
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall				# print new linefeed
	j printBigEnd

printBigEnd:
.data
bigEndMSG:	.asciiz "BIG END\n"

.text
	la	$a0, bigEndMSG
	addi	$v0, $zero, 4
	syscall				# print "BIG END"
	add 	$t8, $zero, $zero	# Adjust task3 Flag to 0
	j taskHub			# return to taskHub

# This Section implement Task 4
# $s1 = alpha
# $s2 = beta
# $s3 = gamma
# $s4 = delta
# $s5 = epsilon
printTask:
.data
stringAlpha:	.asciiz "alpha  : "
stringBeta:	.asciiz "beta   : "
stringGamma:	.asciiz "gamma  : "
stringDelta:	.asciiz "delta  : "
stringEpsilon:	.asciiz "epsilon: "

.text
# print alpha
	la	$a0, stringAlpha	
	addi	$v0, $zero, 4
	syscall				# print "alpha  : "
	
	addi	$v0, $zero, 1
	add	$a0, $zero, $s1
	syscall				# print alpha
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall				# print new linefeed
# print beta
# Uses Same Structure as print alpha above
	la	$a0, stringBeta		
	addi	$v0, $zero, 4
	syscall				
	
	addi	$v0, $zero, 1
	add	$a0, $zero, $s2
	syscall				
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall		
# print gamma
	la	$a0, stringGamma		
	addi	$v0, $zero, 4
	syscall				
	
	addi	$v0, $zero, 1
	add	$a0, $zero, $s3
	syscall				
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall			
# print delta
	la	$a0, stringDelta	
	addi	$v0, $zero, 4
	syscall				
	
	addi	$v0, $zero, 1
	add	$a0, $zero, $s4
	syscall				
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall	
# print epsilon
	la	$a0, stringEpsilon		
	addi	$v0, $zero, 4
	syscall				
	
	addi	$v0, $zero, 1
	add	$a0, $zero, $s5
	syscall				
	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall	
	addi	$v0, $zero, 11
	addi	$a0, $zero, 10
	syscall	
	
	add 	$t9, $zero, $zero		# Adjust task4 Flag to 0
	j taskHub
	
	
	 

	

	
	
