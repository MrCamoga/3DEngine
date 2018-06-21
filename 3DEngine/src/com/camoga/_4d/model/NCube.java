package com.camoga._4d.model;

import com.camoga.engine.Maths;

public class NCube extends Polytope {
	
	public NCube(int N, double dotSize, double scale, int color) {
		super(NdimCubeVertices(N), NdimCubeEdges(N), NdimCubeFaces(N), NdimCubeTC(N), dotSize, scale, color);
		this.N = N;
	}	
	
	/**
	 * 
	 * @param n dimension
	 * @return array of texture coordinates for every face on the N-cube
	 */
	private static double[][] NdimCubeTC(int n) {
		double[][] tc = new double[(int) (Math.pow(2, n-2)*Maths.factorial(n)/Maths.factorial(n-2)/Maths.factorial(2))*4][];
		for(int i = 0; i < tc.length; i+=4) {
			tc[i] = new double[]{0,0};
			tc[i+1] = new double[]{1,0};
			tc[i+2] = new double[]{1,1};
			tc[i+3] = new double[]{0,1};
		}
		return tc;
	}

	/**
	 * @param n
	 * @return array of edges for the configuration of vertices arranged in binary 
	 */
	private static int[][] NdimCubeEdges(int n) {
		int[][] edges = new int[(int) (Math.pow(2, n-1)*n)][];
		int index = 0;
		for(int i = 0; i < (1<<n); i++) {
			for(int j = 0; j < n; j++) {
				if((i & (1<<j) ) == (1<<j)) continue;
				edges[index] = new int[] {i,(1<<j)+i};
				index++;
			}
		}
		return edges;
	}
	
	/**
	 * 
	 * @param n dimension
	 * @return array of faces of a N-cube
	 */
	private static int[][] NdimCubeFaces(int n) {
		int numOfFaces = (int) (Math.pow(2, n-2)*Maths.factorial(n)/Maths.factorial(n-2)/Maths.factorial(2));
		int[][] faces = new int[numOfFaces][];
		
		int index = 0;
		
		for(int i = 0; i < 1<<n; i++) {
			for(int j = 0; j < n; j++) {
				int jp = (1<<j);
				if((i & jp) == jp) continue;
				

				for(int k = j+1; k < n; k++) {
					int kp = (1<<k);
					if((i & kp) == kp) continue;

					faces[index] = new int[] {i,i+jp,i+kp+jp,i+kp};
					index++;
				}
			}
		}
		
		return faces;
	}
	
	/**
	 * 
	 * @param n dimension
	 * @return array of vertices of a N-Cube
	 */
	private static double[][] NdimCubeVertices(int n) {
		double[][] vertices = new double[(int) Math.pow(2, n)][n];
		for(int i = 0; i < vertices.length; i++) {
			for(int j = 0; j < n; j++) {
				
				vertices[i][n-j-1] = (i & (1<<j)) != 0 ? -1:1;
			}
		}
		return vertices;
	}

}
