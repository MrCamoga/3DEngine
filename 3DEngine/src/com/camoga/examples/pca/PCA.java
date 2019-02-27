package com.camoga.examples.pca;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;

import com.camoga._4d.geom.MatrixNd;
import com.camoga._4d.geom.VecNd;
import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.Wireframe;

public class PCA extends Engine {

	Wireframe datapoints;
	Wireframe vectors;
	
	static VecNd[] points = new VecNd[30000];
	
	Random r = new Random(3);
	
	public PCA() {
		super();
		cam.pos = new Vec3(0,0,-3);
		
		generate();
	}
	
	public void generate() {
		datapoints = new Wireframe(1, 2, 0x8fff0000);
		vectors = new Wireframe(1, 5, 0xff005cff);
		
		Vec3 ax1 = new Vec3(r.nextDouble()*4-2,r.nextDouble()*4-2,r.nextDouble()*4-2);
		Vec3 ax2 = new Vec3(r.nextDouble()*4-2,r.nextDouble()*4-2,r.nextDouble()*4-2);
		Vec3 ax3 = new Vec3(r.nextDouble()*4-2,r.nextDouble()*4-2,r.nextDouble()*4-2);
		
		randomGaussian(ax1,ax2,ax3);
		
		double[][] ev = pca();
		
		VecNd v1 = new VecNd(ev, 0);
		VecNd v2 = new VecNd(ev, 1);
		VecNd v3 = new VecNd(ev, 2);
		vectors.addVertex(0, 0, 0);
		vectors.addVertex(v1.xs[0], v1.xs[1], v1.xs[2]);
		vectors.addVertex(v2.xs[0], v2.xs[1], v2.xs[2]);
		vectors.addVertex(v3.xs[0], v3.xs[1], v3.xs[2]);

		vectors.addEdge(0, 1);
		vectors.addEdge(0, 2);
		vectors.addEdge(0, 3);
	}
	
	public static double[][] pca() {
		double[][] covarianceMatrix = covarianceMatrix(points);
		System.out.println(Arrays.deepToString(covarianceMatrix));
		return eigenvectors(covarianceMatrix);
//		System.out.println(Arrays.deepToString(ev));
	}
	
	public void tick(double delta) {
		super.tick(delta);
		if(key.ENTER) {
			scene.remove(datapoints);
			scene.remove(vectors);
			generate();
		}
	}
	
	public static double[][] eigenvectors(double[][] A) {
		double[][][] QR = QRfact(A);
		double[][] X = null;
		for(int i = 0; i < 200; i++) {
			X = new MatrixNd(A).multiply(QR[0]).matrix;		
			QR = QRfact(X);
//			System.out.println(Arrays.deepToString(X));
		}
		return X;
	}
	
	public static double[][][] QRfact(double[][] A) {		
		VecNd[] a = new VecNd[A[0].length];
		for(int i  = 0; i < a.length; i++) {
			a[i] = new VecNd(A, i);
		}

		VecNd[] e = new VecNd[A[0].length];
		VecNd[] u = new VecNd[A[0].length];
		
		for(int i = 0; i < u.length; i++) {
			u[i] = a[i].clone();
			for(int j = 0; j < i; j++) {
				u[i].sub(VecNd.mul(e[j],VecNd.dot(a[i], e[j])));
			}
			e[i] = VecNd.normalize(u[i]);
		}
		
		
		MatrixNd Q = new MatrixNd(e);
		
		double[][] r = new double[A.length][A[0].length];
		
		for(int i = 0; i < r.length; i++) {
			for(int j = i; j < r[i].length; j++) {
				r[i][j] = VecNd.dot(a[j], e[i]);
			}
		}
		
		return new double[][][] {Q.matrix, r};
	}
	
	public static double[][] covarianceMatrix(VecNd data[]) {
		int N = data[0].xs.length;
		double[][] covarianceMatrix = new double[N][N];
		
		double[] mean = new double[N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < data.length; j++) {
				mean[i] += data[j].xs[i];
			}
			mean[i] /= data.length;
		}
		
		for(int i = 0; i < N; i++) {
			for(int j = i; j < N; j++) {
				for(int k = 0; k < data.length; k++) {
					covarianceMatrix[i][j] += (data[k].xs[i]-mean[i])*(data[k].xs[j]-mean[j]);				
				}
				covarianceMatrix[i][j] /= data.length;
			}
		}
		for(int j = 1; j < N; j++) {
			for(int i = 0; i < j; i++) {
				covarianceMatrix[j][i] = covarianceMatrix[i][j];
			}
		}
		return covarianceMatrix;
	}

	public void randomGaussian(Vec3 ax1, Vec3 ax2, Vec3 ax3) {

		double x0, x1, x2;
		for(int i = 0; i < points.length; i++) {
			x0 = r.nextGaussian();
			x1 = r.nextGaussian();
			x2 = r.nextGaussian();
			Vec3 p = Vec3.mul(ax1, x0).add(Vec3.mul(ax2, x1)).add(Vec3.mul(ax3, x2));
			datapoints.addVertex(p.x, p.y, p.z);
			points[i] = new VecNd(p.x,p.y,p.z);
		}
		scene.add(datapoints);
		scene.add(vectors);
	}
	
	public static void main(String[] args) {
		new PCA().start();
	}
	
	public void predraw(Screen screen) {
		
	}

	public void postdraw(Graphics g) {
		
	}
}