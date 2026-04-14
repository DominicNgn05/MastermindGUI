# Filename: asm4
# Course: CSC252
# This file implements 2 functions
# Merge: is the merging subroutine for MergeSort
# quicksort: sort an array using quicksort algorithm

.text
# Task 2.1: Merge Subroutine of mergeSort
.globl merge
merge:
# standard prologue for 5 parameters
	addiu	$sp, $sp, -28	# allocate 7 words stackframe
	sw	$fp, 0($sp)	# save frame pointer
	sw	$ra, 4($sp)	# save return address
	addiu	$fp, $sp, 24	# move stack pointer
# saving a registers
	sw	$a0, 8($sp)
	sw	$a1, 12($sp)
	sw	$a2, 16($sp)
	sw	$a3, 20($sp)
# body
	add	$t0, $zero, $zero	#t0 = posA
	add	$t1, $zero, $zero	#t1 = posB
#while (posA < aLen || posB < bLen) {
#if (posB == bLen || posA < aLen && a[posA] <= b[posB]) {
#out[posA+posB] = a[posA];
#posA++;
#} else {
#out[posA+posB] = b[posB];
#posB++;
#}
#merge_debug(out, posA+posB);
#}
#}
mergeLoop:
	lw 	$a0, 8($sp)	# retrieve param1 int *a from stack
	lw	$a1, 12($sp)	# retrieve param2 aLen from stack
	lw	$a2, 16($sp)
	lw	$a3, 20($sp)	# retrieve param4 bLen from stack
	slt	$t2, $t0, $a1	#t2 = posA < aLen 
	beq	$t2, $zero, mergeLoopCondition2	# posA < aLen = false, check condition 2
	j	mergeLoopBody
mergeLoopCondition2:
	slt	$t2, $t1, $a3	#t2 = posB < bLen
	beq	$t2, $zero, mergeEpilogue	# posB < bLen = false, exit loop
mergeLoopBody:
	# load a[posA]
	sll	$t2, $t0, 2	# t2 = 4*posA
	add	$t2, $t2, $a0	# t2 = *a[posA]
	lw	$t3,  0($t2)	# t3 = a[posA]
	# load b[posB]
	sll	$t2, $t1, 2	# t2 = 4*posB
	add	$t2, $t2, $a2	# t2 = *b[posB]
	lw	$t4,  0($t2)	# t4 = b[posB]
	
	#if (posB == bLen || posA < aLen && a[posA] <= b[posB])
	beq	$t1, $a3, mergeIf	# if posB==bLen, do If block
	# at this point, posB!=bLen
	slt	$t2, $t0, $a1	# t2 = posA<aLen
	beq	$t2, $zero, mergeElse	# if posA<aLen false, do Else block
	
	slt	$t2, $t4, $t3	# t2 = b[posB]< a[posA]
	beq	$t2, $zero, mergeIf 	# if b[posB]>=a[posA]
	# drop through perform Else block
mergeElse:
	lw	$t6, 24($sp)	# t6 = *out
	sll	$t2, $t0, 2	# t2 =4*posA
	add	$t6, $t6, $t2	# *out += 4*posA
	sll	$t2, $t1, 2	# t2 =4*posB
	add	$t6, $t6, $t2	# t6 += posB
	sw	$t4, 0($t6)	# out[posA+posB] = b[posB]
	addi	$t1, $t1, 1	# posB++
	j merge_debug_call
mergeIf:
	lw	$t6, 24($sp)	# t6 = *out
	sll	$t2, $t0, 2	# t2 =4*posA
	add	$t6, $t6, $t2	# *out += 4*posA
	sll	$t2, $t1, 2	# t2 =4*posB
	add	$t6, $t6, $t2	# t6 += posB
	sw	$t3, 0($t6)	# out[posA+posB] = a[posA]
	addi	$t0, $t0, 1	# posA++
merge_debug_call:
	# need to save t0 and t1
	addiu	$sp, $sp, -8
	sw	$t0, 4($sp)
	sw	$t1, 0($sp)
	
	lw 	$a0, 32($sp)	#param1 out
	add	$a1, $t0, $t1 	#param2 posA+posB
	
	jal	merge_debug	# call merge_debug
	
	lw	$t1, 0($sp)	# restore t1
	lw	$t0, 4($sp)	# restore t0
	addiu	$sp, $sp, 8	# restore stack pointer
	
	j mergeLoop	
mergeEpilogue:
	lw	$ra, 4($sp)	# restore ra
	lw	$fp, 0($sp)	# restore fp
	addiu	$sp, $sp, 28	# deallocate stack frame
	jr	$ra	# jump to ra
	
# Task 2.2: quicksort() the full algorithm, very Fun indeed
.globl quicksort
quicksort:
# prologue
	addiu	$sp, $sp, -24	# allocate 6 words stackframe
	sw	$fp, 0($sp)	# save frame pointer
	sw	$ra, 4($sp)	# save return address
	addiu	$fp, $sp, 20	# move stack pointer
# save ax registers
	sw	$a0, 8($sp)	# store a0 to stack
	sw	$a1, 12($sp)	# store a1 to stack
# save sx registers
	addiu	$sp, $sp, -4
	sw	$s0, 0($sp)	# save s0
	
	addiu	$sp, $sp, -4
	sw	$s1, 0($sp)	# save s1
# body
	slti	$t0, $a1, 2	# t0 = n < 2
	beq	$t0, $zero, quicksortBody # if n<2 false, goto Body
	j	quicksortEpilogue	# else, return
quicksortBody:
	addi	$s0, $zero, 1	# s0 = left
	addi	$s1, $a1, -1	# s1 = right


#while (left <= right) {
#quicksort_debug(data,n, left,right);
#while (left <= right && data[left] <= data[0])
#left++;
#while (left <= right && data[right] > data[0])
#right--;
#if (left < right) {
#quicksort_debug(data, n, left,right);
#int tmp = data[left];
#data[left] = data[right];
#data[right] = tmp;
#left++;
#right--;
#}
#}
quicksortLoop:
	slt	$t0, $s1, $s0	# t0 = right < left
	beq	$t0, $zero, quicksortLoopIn	# right < left false -> enter loop
	j	quicksortLoopOut	# right < left true -> exit loop
quicksortLoopIn:
	# call quicksort_debug(data, n, left,right);
	lw	$a0, 16($sp)	# param1 data
	lw	$a1, 20($sp)	# param2 n
	add	$a2, $zero, $s0	# param3 left
	add	$a3, $zero, $s1	# param4 right
	
	jal	quicksort_debug	# call quicksort_debug(data,n, left,right)
	
# while (left <= right && data[left] <= data[0])
# left++;	
quicksortLoopInLeft:
	slt	$t0, $s1, $s0	# t0 = right < left
	beq	$t0, $zero, quicksortLoopInLeftCondition2	# if right<left false, check condition 2
	j	quicksortLoopInRight	# else skip loop
quicksortLoopInLeftCondition2:
	lw	$a0, 16($sp)	# retrieves param1 data
	lw	$t2, 0($a0)	# t2 = data[0]
	sll	$t1, $s0, 2	# t1 = 4*left
	
	add	$t1, $t1, $a0	# t1 = *data + left
	lw	$t1, 0($t1)	# t1 = data[left]
	
	slt	$t0, $t2, $t1	#t0 = data[0] < data[left]
	beq	$t0, $zero, quicksortLoopInExecute	# data[0] < data[1] false -> execute loop
	j	quicksortLoopInRight	# else skip
quicksortLoopInExecute:
	addi	$s0,$s0, 1	# left ++
	j	quicksortLoopInLeft
# while (left <= right && data[right] > data[0])
# right--;
quicksortLoopInRight:
	slt	$t0, $s1, $s0	# t0 = right < left
	beq	$t0, $zero, quicksortLoopInRightCondition2
	j	quicksortLoopInIf
quicksortLoopInRightCondition2:
	lw	$a0, 16($sp)	# retrieves param1 data
	lw	$t2, 0($a0)	# t2 = data[0]
	
	sll	$t1, $s1, 2	# t1 = 4*right
	add	$t1, $t1, $a0	# t1 = *data + right
	lw	$t1, 0($t1)	# t1 = data[right]
# ---------------------RED FLAG---------------------------
	slt	$t0, $t2, $t1	#t0 = data[0] < data[right]
	beq	$t0, $zero, quicksortLoopInIf
	j 	quicksortLoopInRightExecute

quicksortLoopInRightExecute:
	addi	$s1,$s1, -1	# right --
	j	quicksortLoopInRight
quicksortLoopInIf:
	slt	$t0, $s0, $s1	#t0 = left < right
	beq	$t0, $zero, quicksortLoopIfSkip
	
	# call quicksort_debug(data, n, left,right);
	lw	$a0, 16($sp)	# param1 data
	lw	$a1, 20($sp)	# param2 n
	add	$a2, $zero, $s0	# param3 left
	add	$a3, $zero, $s1	# param4 right
	
	jal	quicksort_debug	# call quicksort_debug(data,n, left,right)
	
	# swapping
	lw	$a0, 16($sp)	# retrieves param1 data
	
	# load data[left]
	sll	$t1, $s0, 2	# t1 = 4*left
	add	$t1, $t1, $a0	# t1 = *data + left
	lw	$t2, 0($t1)	# t2 = temp
	# swapping data
	sll	$t3, $s1, 2	# t3 = 4*right
	add	$t3, $t3, $a0	# t3 = *data + right
	lw	$t4, 0($t3)	# t4 = data[right]
	
	sw	$t4, 0($t1)	# data[left] = data[right]
	sw	$t2, 0($t3)	# data[right] = temp
	
	addi	$s0, $s0, 1	# left ++
	addi	$s1, $s1, -1	# right --
quicksortLoopIfSkip:
	j	quicksortLoop
	
quicksortLoopOut:
	# call quicksort_debug(data,n, left,right)
	lw	$a0, 16($sp)	# param1 data
	lw	$a1, 20($sp)	# param2 n
	add	$a2, $zero, $s0	# param3 left
	add	$a3, $zero, $s1	# param4 right
	
	jal	quicksort_debug	# call quicksort_debug(data,n, left,right)
	
	# swapping
	lw	$a0, 16($sp)	# retrieves param1 data
	lw	$t0, 0($a0)	# t0 = temp
	addi	$t1, $s0, -1	# t1 = left -1
	sll	$t1, $t1, 2
	add	$t1, $t1, $a0	
	lw	$t2, 0($t1)	# t2 = data[left-1]
	sw	$t2, 0($a0)	# data[0] = data[left-1]
	sw	$t0, 0($t1)	# data[left-1] = temp
	
	# call quicksort_debug(data,n, -1,-1)
	lw	$a0, 16($sp)	# param1 data
	lw	$a1, 20($sp)	# param2 n
	addi	$a2, $zero, -1	# param3 -1
	addi	$a3, $zero, -1	# param4 -1
	
	jal	quicksort_debug	# call quicksort_debug(data,n, -1, -1)
	# call quicksort(data, left-1);
	lw	$a0, 16($sp)	# param1 data
	
	add	$a1, $zero, $s0	
	addi	$a1, $a1, -1	# param2 left-1
	jal	quicksort
	
	# call quicksort(data+left, n-left);
	lw	$a0, 16($sp)	
	sll	$t1, $s0, 2
	add	$a0, $a0, $t1	# param1 data + left
	
	lw	$a1, 20($sp)
	sub	$a1, $a1, $s0	# param2 n - left	
	jal	quicksort
	
	# call quicksort_debug(data,n, -1,-1)
	lw	$a0, 16($sp)	# param1 data
	lw	$a1, 20($sp)	# param2 n
	addi	$a2, $zero, -1	# param3 -1
	addi	$a3, $zero, -1	# param4 -1
	
	jal quicksort_debug
# restore s register
quicksortEpilogue:
	lw	$s1, 0($sp)
	addiu	$sp, $sp, 4	# restore s1
	
	lw	$s0, 0($sp)
	addiu	$sp, $sp, 4	# restore s0

	lw	$ra, 4($sp)
	lw	$fp, 0($sp)
	addiu	$sp, $sp, 24
	jr	$ra
	
