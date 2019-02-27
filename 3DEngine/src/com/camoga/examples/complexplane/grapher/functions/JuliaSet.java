package com.camoga.examples.complexplane.grapher.functions;

import com.camoga.complex.Complex;
import com.camoga.examples.complexplane.grapher.IFunction;

public class JuliaSet implements IFunction {

	private Complex c;
	private int iterations;
	
	public JuliaSet(Complex c, int it) {
		this.c = c;
		this.iterations = it;
	}
	
	public Complex evaluate(Complex z) {
		Complex it = Complex.add(Complex.pow(z, 2), c);
		for(int i = 0; i < iterations; i++) {
			it = Complex.add(Complex.pow(it, 2), c);
		}
		return it;
	}

	public String toString() {
		return "z^2 + " + c;
	}
}
