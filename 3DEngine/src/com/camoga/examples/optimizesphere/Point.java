package com.camoga.examples.optimizesphere;

import com.camoga.engine.geom.Vec3;

public class Point {
	public Vec3 pos;
	public Vec3 vel = new Vec3();
	public Vec3 accel = new Vec3();
	
	public Point() {
		pos = new Vec3(Math.random()*2-1,Math.random()*2-1,Math.random()*2-1).normalize();
	}
	
	public void move(double dt) {
		this.accel.sub(Vec3.mul(vel, vel.mod()*PointsArrangement.VISCOUSFRICTION));
		vel.add(Vec3.mul(accel, dt));
		pos.add(Vec3.mul(vel, dt));
		accel = new Vec3();
		pos.normalize();
	}
	
	public void repulse(Point p) {
		Vec3 r = Vec3.sub(p.pos, pos);
		Vec3 a = r.mul(PointsArrangement.REPULSIONFORCE/Math.pow(Vec3.modSq(r),1.5));
		this.accel.add(a);
	}
}