package com.camoga.engine.input;

import java.awt.Graphics;

import com.camoga.engine.geom.Vec3;

public class Camera {

	public Key input;
	
	public Vec3 pos;
	public Vec3 rot;
	public double speed = .1;
	
	public Camera(Key input) {
		this.input = input;
		pos = new Vec3(0,0,-20);
		rot = new Vec3();
	}

	public void tick() {
		if(input.up) pos.y+=speed;
		if(input.down) pos.y-=speed;
		if(input.left) pos.x-=speed;
		if(input.right) pos.x+=speed;
		if(input.out) pos.z-=speed;
		if(input.in) pos.z+=speed;
		
		if(input.W) rot.x-=0.01;
		if(input.S) rot.x+=0.01;
		if(input.Q) rot.z+=0.01;
		if(input.E) rot.z-=0.01;
		if(input.A) rot.y-=0.01;
		if(input.D) rot.y+=0.01;
		rot.z%=Math.PI*2;
		rot.x%=Math.PI*2;
		rot.y%=Math.PI*2;
	}
	
	public void render(Graphics g) {
		g.drawString("x: " + pos.x, 10, 12);
		g.drawString("y: " + pos.y, 10, 27);
		g.drawString("z: " + pos.z, 10, 42);
		g.drawString("xθ: " + rot.x, 10, 62);
		g.drawString("yθ: " + rot.y, 10, 77);
		g.drawString("zθ: " + rot.z, 10, 92);
	}
	
	public Vec3 getPos() {
		return pos;
	}
	
	public Vec3 getRot() {
		return rot;
	}
}