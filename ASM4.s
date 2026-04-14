.globl bst_init_node
bst_init_node:	
# prologue
	addiu	$sp, $sp, -24
	sw	$fp, 0($sp)
	sw	$ra, 4($sp)
	addiu	$fp, $sp, 20
	
################# saving registers

	# body
	sw	$a1, 0($a0)	# node->key = key
	sw	$zero, 4($a0)	# node->left = null
	sw	$zero, 8($a0)	# node->right = null
	
# epilogue
	lw	$ra, 4($sp)	# restore ra
	lw	$fp, 0($sp)	# restore fp
	addiu	$sp, $sp, 24	# deallocate stack frame
	jr	$ra	# jump to ra
	
.globl bst_search
bst_search:
# prologue
	addiu	$sp, $sp, -24
	sw	$fp, 0($sp)
	sw	$ra, 4($sp)
	addiu	$fp, $sp, 20
# body
	add	$t0, $zero, $a0	# BSTNode cur = node

#while (cur != NULL)
#{
#if (cur->key == key)
#return cur;
#if (key < cur->key)
#cur = cur->left;
#else
#cur = cur->right;
#}
bst_search_loop:
	lw	$t1, 0($t0)	# t1 = cur->key
	lw	$t2, 4($t0)	# t2 = cur->left
	lw	$t3, 8($t0)	# t3 = cur->right
	
	beq	$t0, $zero, bst_search_loop_out	# if node == null, exit
	
	bne	$t1, $a1, bst_search_loop_condition_2	# if cur-key == key
	add	$v0, $zero, $t0	# return cur
	j	bst_search_loop_epilogue
bst_search_loop_condition_2:
	slt	$t4, $a0, $t1	# t4 = key < cur->key
	beq	$t4, $zero, bst_search_loop_condition_2_else 	# t4 not less than cur->key
	add	$t0, $zero, $t2
	j	bst_search_loop
bst_search_loop_condition_2_else:
	add	$t0, $zero, $t3
	j	bst_search_loop
bst_search_loop_out:
	add	$v0, $zero, $zero
	
# epilogue
bst_search_epilogue:
	lw	$ra, 4($sp)	# restore ra
	lw	$fp, 0($sp)	# restore fp
	addiu	$sp, $sp, 24	# deallocate stack frame
	jr	$ra	# jump to ra

.globl bst_count
bst_count:
# prologue
	addiu	$sp, $sp, -24
	sw	$fp, 0($sp)
	sw	$ra, 4($sp)
	addiu	$fp, $sp, 20
# save s registers
	addiu	$sp, $sp, -4	# save s1
	sw	$s1, 0($sp)
	
	addiu	$sp, $sp, -4	# save s2
	sw	$s2, 0($sp)
# body
	add	$s2, $a0, $zero	#s2 = node
	beq	$a0, $zero, bst_count_return_0	#if node==null, return 0
	
	# bst_count(node->left)
	lw	$a0, 4($s2)
	jal	bst_count
	add	$s1, $zero, $v0	#s1 = bst_count(node->left)
	
	# bst_count(node->right)
	lw	$a0, 8($s2)
	jal	bst_count
	
	add	$s1, $s1, $v0	# s1 = bst_count(node->left) + bst_count(node->right)
	addi	$s1, $s1, 1	# s1 +=1
	add	$v0, $zero, $s1
	j	bst_count_epilogue
	
bst_count_return_0:
	add	$v0, $zero, $zero
bst_count_epilogue:
# prologue
	lw	$s2, 0($sp)
	addiu	$sp, $sp, 4
	
	lw	$s1, 0($sp)
	addiu	$sp, $sp, 4
	
	
	lw	$ra, 4($sp)	# restore ra
	lw	$fp, 0($sp)	# restore fp
	addiu	$sp, $sp, 24	# deallocate stack frame
	jr	$ra	# jump to ra

.globl bst_in_order_traversal
bst_in_order_traversal:
	# prologue
	addiu	$sp, $sp, -24
	sw	$fp, 0($sp)
	sw	$ra, 4($sp)
	addiu	$fp, $sp, 20
	
	addi	$sp, $sp, -4
	sw	$s0, 0($sp)
	#body
	add	$s0, $a0, $zero
	beq	$a0, $zero, in_order_epilogue
	
	lw	$a0, 4($s0)
	jal	bst_in_order_traversal
	
	lw	$a0, 0($sp)
	addi	$v0, $zero, 1
	syscall
	
	addi	$a0, $zero, 10
	addi	$v0, $zero, 11
	syscall
	
	lw	$a0, 8($s0)
	syscall	
	
	
	
	