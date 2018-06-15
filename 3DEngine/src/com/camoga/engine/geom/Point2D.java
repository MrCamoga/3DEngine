package com.camoga.engine.geom;

import static java.lang.Math.*;

public class Point2D {

	public double x, y;
	
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D() {
		
	}
	
	public Point2D add(Vec4d v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	public Point2D div(double s) {
		x /= s;
		y /= s;
		return this;
	}
	
	public Point2D(double[] pos) {
		this(pos[0], pos[1]);
	}
	
	public double getDistance(Point2D point) {
		return (double)sqrt(pow(point.x-x,2)+pow(point.y-y,2));
	}
	
	public double getDistance(double[] point) {
		return (double)sqrt(pow(point[0]-x,2)+pow(point[1]-y,2));
	}
	
	public double getDistanceSq(double[] point) {
		return (double)(pow(point[0]-x,2)+pow(point[1]-y,2));
	}
	
	public double getDistanceSq(Point2D point) {
		return (double)(pow(point.x-x,2)+pow(point.y-y,2));
	}
}