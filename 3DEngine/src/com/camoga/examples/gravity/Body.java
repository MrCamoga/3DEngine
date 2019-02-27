package com.camoga.examples.gravity;

import com.camoga.engine.geom.Vec3;
import com.camoga.engine.model.models.Sphere;

public class Body {
	
	public Sphere sphere;
	private static final double G = Math.pow(10, -20)*6.67384;
	private double mass;
	public Vec3 pos;
	private Vec3 vel;
	
	public Body(Sphere sphere, Vec3 pos, Vec3 vel, double mass) {
		this.sphere = sphere;
		this.pos = pos;
		this.vel = vel;
		this.mass = mass;
	}
	
	public void tick(double dt, Vec3 offset) {
		pos.add(new Vec3(vel.x*dt,vel.y*dt,vel.z*dt));
		sphere.translate(new Vec3(pos).sub(offset));
	}
	
	public void attract(Body b2, double dt) {
		double distance = pos.getDistance(b2.pos);
		double acc = -G*b2.mass/Math.pow(distance, 3);
		double dv = acc*dt;
		vel.add(new Vec3((pos.x-b2.pos.x)*dv,(pos.y-b2.pos.y)*dv,(pos.z-b2.pos.z)*dv));
	}
}
