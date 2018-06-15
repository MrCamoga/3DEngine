package com.camoga.test4d.geom;

public class VecNd {
	
	public double[] xs;
	public double w0;
	
	public VecNd() {
		this(0, 0, 0);
	}
	
	/**
	 * 
	 * @param x DO NOT INCLUDE HOMOGENEOUS COORDINATE IN ARRAY. INSTEAD, SET IT MANUALLY IN w0
	 */
	public VecNd(double...x) {
		this.xs = x;
		this.w0 = 1;
	}
	
	public double getMagnitud() {
		double result = 0;
		for(double x : xs) result += x*x;
		
		return Math.sqrt(result);
	}
	
	public VecNd normalize() {
		for(int i = 0; i < xs.length; i++) {
			xs[i] /= w0;
		}
		return this;
	}
}
