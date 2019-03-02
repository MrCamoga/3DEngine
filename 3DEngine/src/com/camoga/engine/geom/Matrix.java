package com.camoga.engine.geom;

public class Matrix {

//	public static final Matrix ID = new Matrix(new double[][]{{1,0,0},{0,1,0},{0,0,1}});
	
	public double matrix[][];
	
	public Matrix(double[][] matrix) {
		this.matrix = matrix.clone();
	}
	
	public Matrix(Vec3 A, Vec3 B, Vec3 C) {
		this.matrix = new double[][] {
			{A.x,B.x,C.x},
			{A.y,B.y,C.y},
			{A.z,B.z,C.z},
		};
	}
	
	public Matrix multiply(Matrix m) {
		return multiply(m.matrix);
	}
	
	public Vec3 multVec(Vec3 v) {
		return new Vec3(matrix[0][0]*v.x+matrix[0][1]*v.y+matrix[0][2]*v.z,
				matrix[1][0]*v.x+matrix[1][1]*v.y+matrix[1][2]*v.z, 
				matrix[2][0]*v.x+matrix[2][1]*v.y+matrix[2][2]*v.z);
	}
	
	public Vec3 vecMult(Vec3 v) {
		return new Vec3(matrix[0][0]*v.x+matrix[1][0]*v.y+matrix[2][0]*v.z,
				matrix[0][1]*v.x+matrix[1][1]*v.y+matrix[2][1]*v.z,
				matrix[0][2]*v.x+matrix[1][2]*v.y+matrix[2][2]*v.z);
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

	public Matrix multiply(double[][] m) {
		double[][] result = new double[matrix.length][m[0].length];
		for(int y = 0; y < m.length; y++) {
			for(int x = 0; x < m[0].length; x++) {
				for(int j = 0; j < matrix[0].length; j++) {
					result[y][x] += matrix[y][j]*m[j][x];
				}
			}
		}
		return new Matrix(result);
	}
	
	public Matrix transpose() {
		double[][] result = new double[matrix[0].length][matrix.length];
		for(int i = 0; i < result.length; i++) {
			for(int j = 0; j < result[i].length; j++) {
				result[i][j] = matrix[j][i];
			}
		}
		return new Matrix(result);
	}
	
	public Matrix scale(double s) {
		return multiply(new Matrix(new double[][]{
			{s,0,0,0},
			{0,s,0,0},
			{0,0,s,0},
			{0,0,0,1}
		}));
	}
	
	public static Matrix ID(int n) {
		Matrix ID = new Matrix(new double[n][n]);
		for(int i = 0; i < n; i++) {
			ID.matrix[i][i] = 1;
		}
		return ID;
	}
}