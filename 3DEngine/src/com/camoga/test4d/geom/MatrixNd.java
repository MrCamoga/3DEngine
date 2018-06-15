package com.camoga.test4d.geom;

import com.camoga.engine.geom.Vec4d;

public class MatrixNd {

//	public static final Matrix ID = new Matrix(new double[][]{{1,0,0},{0,1,0},{0,0,1}});
	
	public double matrix[][];
	
	public MatrixNd(double[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * 
	 * @param m matrix
	 * @return multiplication of two matrices
	 */
	public MatrixNd multiply(MatrixNd m) {
		return multiply(m.matrix);
	}
	
	/**
	 * 
	 * @param vecN N-dimensional vector
	 * @return transformed vecN
	 */
	public VecNd multiply(VecNd vecN) {
		//create 1xN matrix
		double[][] m1 = new double[vecN.xs.length+1][1];
		for(int i = 0; i < m1.length-1; i++) {
			m1[i][0] = vecN.xs[i];
		}
		m1[m1.length-1][0] = vecN.w0;
		
		//Multiply matrices
		double[][] m = multiply(m1).matrix;
		
		//Convert new 1xN matrix to VecNd
		double[] xs = new double[m.length-1];
		for(int i = 0; i < xs.length; i++) {
			xs[i] = m[i][0];
		}
		
		VecNd vec = new VecNd(xs);
		vec.w0 = m[m.length-1][0];
		return vec;
	}
	
	/**
	 * 
	 * @param vec4 homogeneous 3D vector 
	 * @return transformed vector
	 */
	public Vec4d multiply(Vec4d vec4) {
		double[][] m = multiply(new double[][] {
			{vec4.x},
			{vec4.y},
			{vec4.z},
			{vec4.w0},
		}).matrix;
		return new Vec4d(m[0][0], m[1][0], m[2][0], m[3][0]);
	}

	/**
	 * 
	 * @param m array of matrix values
	 * @return multiplication
	 */
	public MatrixNd multiply(double[][] m) {
		double[][] result = new double[matrix.length][m[0].length];
		for(int y = 0; y < m.length; y++) {
			for(int x = 0; x < m[0].length; x++) {
				for(int j = 0; j < matrix[0].length; j++) {
					result[y][x] += matrix[y][j]*m[j][x];
				}
			}
		}
		return new MatrixNd(result);
	}
	
	/**
	 * 
	 * @param n matrix dimension
	 * @return nxn identity matrix
	 */
	public static MatrixNd ID(int n) {
		MatrixNd ID = new MatrixNd(new double[n][n]);
		for(int i = 0; i < n; i++) {
			ID.matrix[i][i] = 1;
		}
		return ID;
	}
}