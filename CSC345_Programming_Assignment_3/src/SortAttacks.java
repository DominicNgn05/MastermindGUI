/**
 * @author Khanh Ngoc Nguyen
 * @course CSC345
 * @instructor Hudson Lynam 
 * @assignment Programming Project 3
 * @TA Anna Yami
 * @due March 26th, 3:30pm
 * 
 * filename SortAttacks.java
 * 
 * This file implements static methods to sort arrays of Attack objects based on
 * 
 */

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author Khanh Nguyen
 * Class SortAttacks
 * This class implements static methods to sort arrays of Attack objects
 * 
 * @method sortByDamage((Attack[] array, int glyph)) sort the array by damage. Uses radix sort
 * @method sortBySpeed(Attack[] array, int low, int high) sort the array by speed. Uses Quicksort
 */
public class SortAttacks {

	private static int exponential(int base, int power) {
		int ret = 1;
		for (int i = 0; i < power; i++) {
			ret *= base;
		}
		return ret;
	}


	private static int getPosition(int n, int position) {
		return (n / (exponential(10, position-1))) % 10;
	}
	/**
	 * sortByDamage(Attack[] array, int glyph)
	 * 
	 * sort the parameter array by Damage value. Uses radix sort.
	 * 
	 * @param array: the array to be sorted
	 * @param glyph: log_10(maximum damage value in array)
	 * @return the sorted list
	 */
	public static Attack[] sortByDamage(Attack[] array, int glyph) {
		// initializing the queues
		List<ArrayList<LinkedList<Attack>>> glyphQueues = new ArrayList<ArrayList<LinkedList<Attack>>>();
		for (int i = 0; i < glyph; i++) {
			ArrayList<LinkedList<Attack>> curGlyph = new ArrayList<LinkedList<Attack>>();
			for (int j = 0; j < 10; j++) {
				LinkedList<Attack> curQueue = new LinkedList<Attack>();
				curGlyph.add(curQueue);
			}
			glyphQueues.add(curGlyph);
		}
		// enqueue based on 1's position
		for (Attack i : array) {
			glyphQueues.get(0).get(getPosition(i.damage(), 1)).offer(i);
		}
		// dequeue from previous position and enqueue accordingly
		for (int i = 1; i < glyph; i++) {
			for (int j = 0; j < 10; j++) {
				while (!glyphQueues.get(i-1).get(j).isEmpty()) {
					Attack cur = glyphQueues.get(i-1).get(j).poll();	// dequeue
					int nextPosition=getPosition(cur.damage(), i+1);		// get next glyph of next position
					glyphQueues.get(i).get(nextPosition).offer(cur); 	// enqueue into appropriate queue
				}
			}
		}
		Attack[] ret = new Attack[array.length];
		int index=0;
		// final dequeue to get sorted List
			for (int j = 0; j < 10; j++) {
				while (!glyphQueues.get(glyph-1).get(j).isEmpty()) {
					ret[index]=glyphQueues.get(glyph-1).get(j).poll();
					index++;
				}
			}
			array = ret;
		return ret;
	}
	
	
	/**
	 * sortBySpeed(Attack[] array, int low, int high) 
	 * 
	 * sort a sub array from index (low,high) the parameter array by speed value.
	 * Uses quicksort.
	 * if array size is less than 5, uses insertionSort
	 * @param array: the array to be sorted
	 * @param low: the starting index of the sub array
	 * @param high: the last index of the sub array
	 */
	public static void sortBySpeed(Attack[] array, int low, int high) {
		// on small-ish sublist, use insertion sort. arbitrarily set cutoff to 5. 
		if (high-low < 5) {
			sortSpeedSimple(array, low, high);
			return;
		}
		
		// median of 3
		int mid = low + (high-low)/2;
		// Use Attack object as a wrapper to carry both the index and value
		// the damage field holds the index
		Attack[] medianList = new Attack[3];
		medianList[0] = new Attack(0, low, array[low].speed());
		medianList[1] = new Attack(0, mid, array[mid].speed());
		medianList[2] = new Attack(0, high, array[high].speed());
		sortSpeedSimple(medianList,0,2);
		int pivot = medianList[1].damage();
		
		// move pivot to index 0
		Attack temp = array[pivot];
		array[pivot] = array[low];
		array[low] = temp;
		
		pivot = sortSpeedPartition(array, low, high);
		sortBySpeed(array, low, pivot-1);
		sortBySpeed(array, pivot+1, high);
	}

	/**
	 * sortSpeedPartition(Attack[] array, int low, int high)
	 * this function implements the partitioning subroutine of Quicksort
	 * gets called by sortBySpeed()
	 * @param array: the array to be sorted
	 * @param low: the starting index of the sub array
	 * @param high: the last index of the sub array
	 * @return the final index of the pivot after partitioning
	 */
	private static int sortSpeedPartition(Attack[] array, int low, int high) {
		int L = low+1; 
		int R = high;
		while (L<=R) {
			boolean flagReady = true;
			
			if (L<=R && array[L].speed()<=array[low].speed()) {
				L++;	// if L needs to move, it is not ready to swap
				flagReady = false;	// set flag to false
			}
			if (L<=R && array[R].speed()>=array[low].speed()) {
				R--;	// if R needs to move, it is not ready to swap
				flagReady = false;	// set flag to false
			}
			
			if (flagReady) {	// if both L and R are ready to swap
				//swap L and R
				Attack temp = array[L];
				array[L] = array[R];
				array[R] = temp;
				L++;
				R--;
			}
		}
		// swap pivot and R
		Attack temp = array[R];
		array[R] = array[low];
		array[low] = temp;
		return R;	// return final index of pivot
	}
	
	/**
	 * sortSpeedSimple(Attack[] array, int low, int high)
	 * sort a sub array array of Attack object using insertionSort
	 * @param array the array to be sorted
	 * @param low the starting index of sub array
	 * @param high the last index of sub array
	 */
	private static void sortSpeedSimple(Attack[] array, int low, int high) {
	// simple insertion sort	
		for (int i = low+1; i<=high; i++) {
			int j = i;	
			Attack cur = array[i];
			while ( j>low && cur.speed()<array[j-1].speed()) {
				array[j] = array[j-1];
				j--;
			}
			array[j] = cur;
		}
	}	
	
}
