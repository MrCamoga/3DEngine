package com.camoga.engine;

public class Maths {
	
	private Maths() {}
	
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
	

	
	public static int factorial(int n) {
		int result = 1;
		while(n > 1) {
			result*=n;
			n--;
		}
		return result;
	}
}
