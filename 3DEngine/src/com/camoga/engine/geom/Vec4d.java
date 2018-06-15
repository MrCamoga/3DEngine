package com.camoga.engine.geom;

public class Vec4d {
	
	public double x, y, z, w0;
	
	public Vec4d() {
		this(0, 0, 0);
	}
	
	public Vec4d(double x, double y, double z) {
		this(x, y, z, 1);
	}
	
	public Vec4d(double x, double y, double z, double w0) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w0 = w0;
	}
	
//	public Vec4d add(double magnitud, double angle) {
//		x += magnitud*Math.cos(angle);
//		z += magnitud*Math.sin(angle);
//		return this;
//	}
	
	public double getMagnitud() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public Vec4d normalize() {
		this.x /= w0;
		this.y /= w0;
		this.z /= w0;
		this.w0 = 1;
		return this;
	}
	
	public String toString() {
		return "x: " + x + ", y: " + y + ", z: " + z + ", w0: " + w0;
	}
}
