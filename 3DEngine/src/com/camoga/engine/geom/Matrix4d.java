package com.camoga.engine.geom;

public class Matrix4d {

//	public static final Matrix ID = new Matrix(new double[][]{{1,0,0},{0,1,0},{0,0,1}});
	
	public double matrix[][];
	
	public Matrix4d(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public Matrix4d multiply(Matrix4d m) {
		return multiply(m.matrix);
	}
	
	public Vec4d multiply(Vec4d vec4) {
		double[][] m = multiply(new double[][] {
			{vec4.x},
			{vec4.y},
			{vec4.z},
			{vec4.w0},
		}).matrix;
		
		return new Vec4d(m[0][0],m[1][0],m[2][0],m[3][0]);
	}

	public Matrix4d multiply(double[][] m) {
		double[][] result = new double[matrix.length][m[0].length];
		for(int y = 0; y < m.length; y++) {
			for(int x = 0; x < m[0].length; x++) {
				for(int j = 0; j < matrix[0].length; j++) {
					result[y][x] += matrix[y][j]*m[j][x];
				}
			}
		}
		return new Matrix4d(result);
	}
	
	public static Matrix4d ID(int n) {
		Matrix4d ID = new Matrix4d(new double[n][n]);
		for(int i = 0; i < n; i++) {
			ID.matrix[i][i] = 1;
		}
		return ID;
	}
}