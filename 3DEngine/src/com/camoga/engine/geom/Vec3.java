package com.camoga.engine.geom;

import static java.lang.Math.*;

public class Vec3 {

	public double x, y, z;
	
	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(Vec4d v4) {
		x = v4.x;
		y = v4.y;
		z = v4.z;
	}
	public Vec3(int[] pos) {
		this(pos[0], pos[1], pos[2]);
	}
	
	public Vec3(double[] pos) {
		this(pos[0], pos[1], pos[2]);
	}
	

	
	public Vec3() {}

	public Vec3 add(Vec3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public static Vec3 add(Vec3 v, Vec3 u) {
		return new Vec3(v.x+u.x,v.y+u.y,v.z+u.z);
	}
	
	public Vec3 sub(Vec3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public Vec3 sub(double v) {
		x -= v;
		y -= v;
		z -= v;
		return this;
	}
	
	public Vec3 div(double s) {
		x /= s;
		y /= s;
		z /= s;
		return this;
	}
	
	public static Vec3 div(Vec3 v, double s) {
		Vec3 result = new Vec3();
		result.x = v.x/s;
		result.y = v.y/s;
		result.z = v.z/s;
		return result;
	}
	
	public static Vec3 sub(Vec3 a, Vec3 b) {
		return new Vec3(b.x-a.x, b.y-a.y, b.z-a.z);
	}
	
	public Vec3 mul(double s) {
		return new Vec3(x*s, y*s, z*s);
	}
	
	public static Vec3 mul(Vec3 v, double s) {
		return new Vec3(v.x*s, v.y*s, v.z*s);
	}
	
	/**
	 * 
	 * @param a point
	 * @param b point
	 * @param c point
	 * @return cross product given three points
	 */
	public static Vec3 cross(Vec3 a, Vec3 b, Vec3 c) {
		Vec3 u = sub(b, a);
		Vec3 v = sub(a, c);
		return new Vec3(u.y*v.z-u.z*v.y,u.z*v.x-u.x*v.z,u.x*v.y-u.y*v.x);
	}
	
	public static Vec3 crossNorm(Vec3 a, Vec3 b, Vec3 c) {
		Vec3 u = sub(c, a);
		Vec3 v = sub(b,a);
		return cross(u, v).normalize();
	}
	
	public static Vec3 cross(Vec3 u, Vec3 v) {
		return new Vec3(u.y*v.z-u.z*v.y,u.z*v.x-u.x*v.z,u.x*v.y-u.y*v.x);
	}
	
	public static Vec3 crossNorm(Vec3 u, Vec3 v) {
		return cross(u, v).normalize();
	}
	
	public static double dot(Vec3 u, Vec3 v) {
		return u.x*v.x+u.y*v.y+u.z*v.z;
	}
	
	public static Vec3 normalize(Vec3 u) {
		double mod = u.mod();
		return div(u, mod);
	}
	
	public Vec3 normalize() {
		div(mod());
		return this;
	}
	
	public double mod() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public static double dotNorm(Vec3 u, Vec3 v) {
		return dot(u, v)/Math.sqrt(modSq(u)*modSq(v));
	}
	
	public static double modSq(Vec3 a) {
		return (a.x*a.x+a.y*a.y+a.z*a.z);
	}
	
	public String toString() {
		return "x: " + x+", y: " + y + ", z: " + z; 
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
	
	public double getDistance(Vec3 point) {
		return sqrt(getDistanceSq(point));
	}
}