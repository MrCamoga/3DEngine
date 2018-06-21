package com.camoga._4d.examples;

import java.awt.Graphics;

import com.camoga._4d.geom.MatrixNd;
import com.camoga._4d.geom.VecNd;
import com.camoga._4d.model.NSphere;
import com.camoga._4d.model.Polytope;
import com.camoga.engine.Engine;
import com.camoga.engine.Maths;
import com.camoga.engine.Sprite;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.input.Key;
/**
 * This is an example of a program made using this motor.
 * The objective of this little program is to optimize the three dimensional shadow
 * of an hypercube using the shadow theorem.
 * You can get more information here: <a href="https://www.youtube.com/watch?v=cEhLNS5AHss">Mathologer's Video</a>
 * 
 * Most of the code in here can be used to manipulate even higher dimensional polytopes.
 * However, I've still haven't been able to find a way of calculating the area/volume of a projection; 
 * so it cannot optimize the volume of a 5-cube, 6-cube, and so on.
 * 
 * 
 * @author MrCamoga
 *
 */
public class TestHighDim extends Engine {

	private static final long serialVersionUID = 1L;
	Polytope tesseract;
	Sprite sprite;
	
	//DONE method to create planes of rotation
	
	//Dimension
	public int N = 4;

	double[] planes = new double[Maths.choose(N, 2)-3];
	
	public TestHighDim() {
		super();
		UPS = 60D;
		
//		cam = new Camera4d(key);
		
//		tesseract = new NCube(4, 25, 1, 0xffff0000);
		tesseract = new NSphere(null, null, null, 5, 1, 0xffff0000);
		
	}

	private double lambda = 0.01;
	
	boolean optimize = false;
	
	public void key(Key input) {
		if(input.I) planes[0] += 0.01;
		if(input.K) planes[0] -= 0.01;
		if(input.J) planes[1] -= 0.01;
		if(input.L) planes[1] += 0.01;
		if(input.H) planes[2] += 0.01;
		if(input.N) planes[2] -= 0.01;
	}
	
	@Override
	public void tick(double dt) {
		super.tick(dt);
		this.key(super.key);
		if(key.ENTER) optimize = !optimize;
		if(optimize)
		for(int j = 0; j < 3; j++) {
			
			transformPolytope(tesseract);
			
			double w = projLength(tesseract, 3);
			
			double[] pPlanes = new double[planes.length];

			for(int i = 0; i < pPlanes.length; i++) {
				pPlanes[i] = (2*Math.random()-1)*lambda;
			}
			
			for(int i = 0; i < pPlanes.length; i++) {
				planes[i] += pPlanes[i];
			}
			
			transformPolytope(tesseract);
			
			if(projLength(tesseract, 3) < w) {
				for(int i = 0; i < pPlanes.length; i++) {
					planes[i] -= pPlanes[i];
				}
				transformPolytope(tesseract);
			}
			
			else System.out.println(projLength(tesseract, 3));
		}
		else transformPolytope(tesseract);
		
		//Transform vec5d to vec4d so it can be rendered
		for(int i = 0; i < tesseract.transform4d.length; i++) {
			tesseract.transform4d[i] = vec4projection(tesseract.transform5d[i], 0,1,2);
		}
	}
	
	/**
	 * @param p model
	 * @param axis projection 
	 * @return the projected length of the polytope
	 */
	public double projLength(Polytope p, int axis) {
		double min = 0;
		double max = 0;
		for(VecNd vecN : p.transform5d) {
			if(vecN.xs[axis] < min) min = vecN.xs[axis];
			else if(vecN.xs[axis] > max) max = vecN.xs[axis];
		}
		
		return max-min;
	}
	
	//TODO find the area of a 2D projection
	/**
	 * Area found using the shoelace algorithm
	 * @param p model
	 * @param axis1 <b>axis2</b> projected axes
	 * @return area of projection
	 */
	public static double areaProj(Polytope p, int axis1, int axis2) {
		double A = 0;
		
		for(int i=0; i < (p.transform5d.length-1); i++) {
			A += p.transform5d[i].xs[axis1]*p.transform5d[i+1].xs[axis2];
			A -= p.transform5d[i+1].xs[axis1]*p.transform5d[i].xs[axis2];
		}

		return Math.abs(A/2);
	}
	
	/**
	 * Apply the rotations and scaling
	 * @param p model
	 */
	public void transformPolytope(Polytope p) {
		for(int i = 0; i < p.transform5d.length; i++) {
			int index = 0;
			MatrixNd m = MatrixNd.ID(p.N+1);
			
			for(int j = 0; j < p.N; j++) {
				for(int k = 3; k < p.N; k++) {
					if(k==j) continue;
					if(j > k) break;
					m = m.multiply(rot(planes[index], j, k, p.N+1));
					index++;
				}
			}
			
			p.transform5d[i] = m.multiply(scale(p.scale, p.N+1)).multiply(p.vertices[i]);;
		}
	}
	
	/**
	 * 
	 * @param angle by which the plane is rotated
	 * @param axis1 <b>axis2</b> two axis of a plane
	 * @param N number of dimensions
	 * @return rotation transformation
	 */
	public MatrixNd rot(double angle, int axis1, int axis2, int N) {
		MatrixNd base = MatrixNd.ID(N);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		base.matrix[axis1][axis1] = cos;
		base.matrix[axis1][axis2] = -sin;
		base.matrix[axis2][axis1] = sin;
		base.matrix[axis2][axis2] = cos;
		
		return base;
	}
	
	/**
	 * 
	 * @param scale factor of scaling
	 * @param N dimension of the matrix
	 * @return scaling transformation
	 */
	public MatrixNd scale(double scale, int N) {
		MatrixNd base = MatrixNd.ID(N);
		for(int i = 0; i < N-1; i++) {
			base.matrix[i][i] = scale;
		}
		return base;
	}
	
	/**
	 * 
	 * @param vecN to project onto R3
	 * @param axis1 <b>axis2 axis3</b> axes to be projected
	 * @return homogeneous vector of projection
	 */
	public Vec4d vec4projection(VecNd vecN, Integer axis1, Integer axis2, Integer axis3) {
		Vec4d vec = rot(-cam.rot.x, 1, 2, 4)
				.multiply(rot(-cam.rot.y, 0, 2, 4))
				.multiply(rot(-cam.rot.z, 0, 1, 4))
				.multiply(new Vec4d(axis1 == null ? 0:vecN.xs[axis1], axis2==null ? 0:vecN.xs[axis2], axis3 == null ? 0:vecN.xs[axis3], vecN.w0));
		return vec;
	}
	
	public static void main(String[] args) {
		new TestHighDim().start();
	}
	
	@Override
	public void predraw(Screen screen) {
		tesseract.render(this);
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}
