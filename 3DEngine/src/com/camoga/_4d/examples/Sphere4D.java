package com.camoga._4d.examples;

import java.awt.Graphics;

import com.camoga._4d.geom.MatrixNd;
import com.camoga._4d.geom.VecNd;
import com.camoga._4d.model.NSphere;
import com.camoga._4d.model.Polytope;
import com.camoga.engine.Engine;
import com.camoga.engine.Maths;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.input.Key;

public class Sphere4D extends Engine {
	NSphere sphere = new NSphere(null, null, null, 19, 1, 0xffff0000);

	public Sphere4D() {
		super();
		
		scene.add(sphere);
	}
	
	int N = 4;
	double[] planes = new double[Maths.choose(N, 2)-3];
	
	public static void main(String[] args) {
		new Sphere4D().start();
	}
	
	public void key(Key input) {
		if(input.I) planes[0] += 0.01;
		if(input.K) planes[0] -= 0.01;
		if(input.J) planes[1] -= 0.01;
		if(input.L) planes[1] += 0.01;
		if(input.H) planes[2] += 0.01;
		if(input.N) planes[2] -= 0.01;
		
		if(input.T) {
			for(int i = 0; i < planes.length; i++) {
				planes[i] = Math.random()*2*Math.PI;
			}
		}
	}
	
	@Override
	public void tick(double delta) {
		key(key);
		super.tick(delta);
		transformPolytope(sphere);
		for(int i = 0; i < sphere.transform4d.length; i++) {
			sphere.transform4d[i] = vec4projection(sphere.transform5d[i], 0,1,2);
		}
	}
	
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
			
			p.transform5d[i] = m.multiply(p.vertices[i]);;
		}
	}
	
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
	
	public Vec4d vec4projection(VecNd vecN, Integer axis1, Integer axis2, Integer axis3) {
		Vec4d v4 = rot(-cam.rot.x, 1, 2, 4)
				.multiply(rot(-cam.rot.y, 0, 2, 4))
				.multiply(rot(-cam.rot.z, 0, 1, 4))
				.multiply(new Vec4d(axis1 == null ? 0:vecN.xs[axis1], axis2==null ? 0:vecN.xs[axis2], axis3 == null ? 0:vecN.xs[axis3], vecN.w0));
		return v4;
	}
	
	public void predraw(Screen screen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postdraw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	
}
