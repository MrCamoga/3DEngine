package com.camoga.engine.geom;

import static java.lang.Math.*;

public class Vec3 {

	public double x, y, z;
	
	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3() {
		
	}
	
	public Vec3 add(Vec3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public Vec3 sub(Vec3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public Vec3 mul(double s) {
		return new Vec3(x*s, y*s, z*s);
	}
	
	public Vec3(int[] pos) {
		this(pos[0], pos[1], pos[2]);
	}
	
	public Vec3(double[] pos) {
		this(pos[0], pos[1], pos[2]);
	}
	
	public double getDistance(Vec4d point) {
		return (double)sqrt(pow(point.x-x,2)+pow(point.y-y,2)+pow(point.z-z,2));
	}
	
	public double getDistance(double[] point) {
		return (double)sqrt(pow(point[0]-x,2)+pow(point[1]-y,2)+pow(point[2]-z,2));
	}
	
	public double getDistanceSq(double[] point) {
		return (double)(pow(point[0]-x,2)+pow(point[1]-y,2)+pow(point[2]-z,2));
	}
	
	public double getDistanceSq(Vec3 point) {
		return (double)(pow(point.x-x,2)+pow(point.y-y,2)+pow(point.z-z,2));
	}
}