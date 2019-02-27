package com.camoga.examples.complexplane.grapher;

import com.camoga.complex.Complex;

public class Main {
	public Main(int WIDTH, int HEIGHT, IFunction f, int coloring) {
	
//		double b = -1;
//		while(true) {
//			b+=0.02;
			Plotter.plot((Complex z) -> Complex.exp(Complex.pow(z, Complex.valueOf(2))), 
					WIDTH, HEIGHT, -10, -10, 10, 10, coloring);
//		}
	}
	
	public static void main(String[] args) {
		new Main(200,200, (Complex z) -> Complex.pow(z, z), 0b11);
	}
}