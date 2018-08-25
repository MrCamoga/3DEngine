package com.camoga.engine.model;

public class Material {
	
	public double Ka, Kd, Ks, alpha;

	public static final Material brick = new Material(0.5, 0.1, 0.1, 256);
	public static final Material plastic = new Material(0.5, 0.6, 0.04, 128);
	
	public Material(double Kd, double Ka, double Ks, int alpha) {
		this.Ka = Ka;
		this.Kd = Kd;
		this.Ks = Ks;
		this.alpha = alpha;
	}
}