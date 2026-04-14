.data

.globl  math
.globl  addUp
.globl  big
.globl  print

.globl  alpha
.globl  beta
.globl  gamma
.globl  delta
.globl  epsilon

gamma:    .word   27
beta:     .word   '|'
epsilon:  .word   817
alpha:    .word   705
delta:    .word   '&'

math :    .word   1
addUp:    .word   1
big  :    .word   1
print:    .word   1


.text

.globl	main

main:
	jal studentMain


	# dump out the 4 values.
.data
TESTCASE_MSG:	.asciiz	"\n\nTestcase Variable Dump: "
.text
	addi	$v0, $zero, 4		# print_str(TESTCASE_MSG)
	la	$a0, TESTCASE_MSG
	syscall

	addi	$v0, $zero, 1		# print_int(alpha)
	la	$a0, alpha
	lw	$a0, 0($a0)
	syscall

	addi	$v0, $zero,11		# print_char(' ')
	addi	$a0, $zero,0x20
	syscall

	addi	$v0, $zero, 1		# print_int(beta)
	la	$a0, beta
	lw	$a0, 0($a0)
	syscall

	addi	$v0, $zero,11		# print_char(' ')
	addi	$a0, $zero,0x20
	syscall

	addi	$v0, $zero, 1		# print_int(gamma)
	la	$a0, gamma
	lw	$a0, 0($a0)
	syscall

	addi	$v0, $zero,11		# print_char(' ')
	addi	$a0, $zero,0x20
	syscall

	addi	$v0, $zero, 1		# print_int(delta)
	la	$a0, delta
	lw	$a0, 0($a0)
	syscall

	addi	$v0, $zero,11		# print_char(' ')
	addi	$a0, $zero,0x20
	syscall

	addi	$v0, $zero, 1		# print_int(epsilon)
	la	$a0, epsilon
	lw	$a0, 0($a0)
	syscall

	addi	$v0, $zero,11		# print_char('\n')
	addi	$a0, $zero,0xa
	syscall


	# terminate the program
	addi	$v0, $zero, 10		# syscall_exit
	syscall


