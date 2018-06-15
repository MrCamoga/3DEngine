package com.camoga.engine.input;

import java.awt.Graphics;

import com.camoga.engine.geom.Vec3;

public class Camera {

	public Key input;
	
	public Vec3 pos;
	public Vec3 rot;
	public double speed = .1;
	
	/**
	 * Creates a camera object
	 * @param input 
	 */
	public Camera(Key input) {
		this.input = input;
		pos = new Vec3(0,0,-20);
		rot = new Vec3();
	}

	//TODO Euler angles
	/**
	 * Moves and rotates the camera
	 */
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
		
		//normalize the angles
		rot.z%=Math.PI*2;
		rot.x%=Math.PI*2;
		rot.y%=Math.PI*2;
	}
	
	/**
	 * Renders camera position and rotation on the screen
	 * @param g graphics object
	 * @param xo x coordinate to render camera info
	 * @param yo y coordinate to render camera info
	 */
	public void render(Graphics g, int xo, int yo) {
		g.drawString("x: " + pos.x, xo+10, yo+12);
		g.drawString("y: " + pos.y, xo+10, yo+27);
		g.drawString("z: " + pos.z, xo+10, yo+42);
		g.drawString("xθ: " + rot.x, xo+10, yo+62);
		g.drawString("yθ: " + rot.y, xo+10, yo+77);
		g.drawString("zθ: " + rot.z, xo+10, yo+92);
	}
	
	/**
	 * 
	 * @return position of the camera
	 */
	public Vec3 getPos() {
		return pos;
	}
	
	/**
	 * 
	 * @return rotation of the camera
	 */
	public Vec3 getRot() {
		return rot;
	}
}