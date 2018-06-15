package com.camoga.engine;

/**
 * 
 * Stores some methods used by some parts of the code
 * 
 * @author usuario
 *
 */
public class Maths {
	
	private Maths() {}
	
	//TODO test method
	/**
	 * Quick square root.
	 * Yet to be tested and implemented
	 * @param number
	 * @return
	 */
	public static double Q_rsqrt(double number) {
		long i;
		double x2, y;
		final double threehalfs = 1.5D;
		
		x2 = number * 0.5d;
		y = number;
		i = (long) y;
		i = 0x5f3759df - (i >> 1);
		y = (double) i;
		y = y*(threehalfs - (x2*y*y));
		
		return y;
	}
	

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
