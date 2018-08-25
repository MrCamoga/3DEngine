package com.camoga.mg.complexplane.grapher;

import com.camoga.complex.Complex;

public interface IFunction {
	public Complex evaluate(Complex z);
	
	public String toString();
}