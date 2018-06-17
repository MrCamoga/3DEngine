package com.camoga.engine;

/**
 * 
 * Stores some methods used by some parts of the code
 * 
 * @author usuario
 *
 */
public class Maths {
	
	public Maths() {}	

	/**
	 * 
	 * @param n integer
	 * @return n!
	 */
	public static int factorial(int n) {
		int result = 1;
		while(n > 1) {
			result*=n;
			n--;
		}
		return result;
	}
}