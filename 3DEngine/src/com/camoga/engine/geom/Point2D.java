package com.camoga.engine.geom;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Point2D {

	public double x, y;
	
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D() { }
	
	/**
	 * Add a vector
	 * @param v
	 * @return 
	 */
	public Point2D add(Vec4d v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	/**
	 * Divide vector by a scalar
	 * @param s
	 * @return
	 */
	public Point2D div(double s) {
		x /= s;
		y /= s;
		return this;
	}
	
	/**
	 * Creates a vector with an array
	 * @param pos
	 */
	public Point2D(double[] pos) {
		this(pos[0], pos[1]);
	}
	
	public static Point2D sub(Point2D a, Point2D b) {
		return new Point2D(b.x-a.x, b.y-a.y);
	}
	
	/**
	 * 
	 * @param point
	 * @return distance between Point2D
	 */
	public double getDistance(Point2D point) {
		return (double)sqrt(pow(point.x-x,2)+pow(point.y-y,2));
	}
	
	/**
	 * 
	 * @param point
	 * @return distance between Point2D and array
	 */
	public double getDistance(double[] point) {
		return (double)sqrt(pow(point[0]-x,2)+pow(point[1]-y,2));
	}
	
	/**
	 * 
	 * @param point
	 * @return distance squared
	 */
	public double getDistanceSq(double[] point) {
		return (double)(pow(point[0]-x,2)+pow(point[1]-y,2));
	}
	
	/**
	 * 
	 * @param point
	 * @return distance squared
	 */
	public double getDistanceSq(Point2D point) {
		return (double)(pow(point.x-x,2)+pow(point.y-y,2));
	}
}