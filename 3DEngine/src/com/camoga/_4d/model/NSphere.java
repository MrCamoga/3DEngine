package com.camoga._4d.model;

import static java.lang.Math.*;

import com.camoga.engine.Engine;

public class NSphere extends Polytope {

	public NSphere(int[][] edges, int[][] faces, double[][] textureCoords, double dotSize, double scale, int color) {
		super(vertices(2000), edges, faces, textureCoords, dotSize, scale, color);
	}

	public static double[][] vertices(int num) {
		double[][] vert = new double[num][];
		double theta2 = random()*2*PI;
		for(int i = 0; i < vert.length; i++) {
			double theta0 = random()*2*PI;
			double theta1 = random()*2*PI;
//			theta0 += 0.03;
//			theta1 += 0.02;
			theta2 = Math.random()>0.5 ? 0:0.5;
			vert[i] = new double[] {sin(theta0),cos(theta0)*sin(theta1),cos(theta0)*cos(theta1)*sin(theta2),cos(theta0)*cos(theta1)*cos(theta2)};
		}
		return vert;
	}
	
	@Override
	public void render(Engine main) {
		super.render(main);
	}
}