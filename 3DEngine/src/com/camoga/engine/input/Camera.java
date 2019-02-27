package com.camoga.engine.input;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;

public class Camera {

	public Key input;
	
	public Vec3 pos;
	public Vec3 rot;
	public double speed = 0.1;
	public double omega = .01;
	
	/**
	 * Creates a camera object
	 * @param input 
	 */
	public Camera(Key input) {
		this.input = input;
		pos = new Vec3(0,0,-20);
		rot = new Vec3(0,0,0);
	}

	//TODO Euler angles
	/**
	 * Moves and rotates the camera
	 */
	public void tick() {
//		speed = 0.01;
//		angle = 0.001;
		if(input.up) pos.y+=speed;
		if(input.down) pos.y-=speed;
		if(input.left) pos.x-=speed;
		if(input.right) pos.x+=speed;
		if(input.out) pos.z-=speed;
		if(input.in) pos.z+=speed;
		
		if(input.W) rot.x-=omega;
		if(input.S) rot.x+=omega;
		if(input.Q) rot.z+=omega;
		if(input.E) rot.z-=omega;
		if(input.A) rot.y-=omega;
		if(input.D) rot.y+=omega;

		if(input.I) Engine.scene.getLights().get(0).pos.z += 0.1;
		if(input.K) Engine.scene.getLights().get(0).pos.z -= 0.1;
		if(input.J) Engine.scene.getLights().get(0).pos.x -= 0.1;
		if(input.L) Engine.scene.getLights().get(0).pos.x += 0.1;
		if(input.H) Engine.scene.getLights().get(0).pos.y += 0.1;
		if(input.N) Engine.scene.getLights().get(0).pos.y -= 0.1;
		
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
		if(Engine.scene.getLights().size() > 0) {
			g.drawString("Light source x: " +  Engine.scene.getLights().get(0).pos.x, xo+10, yo+107);
			g.drawString("Light source y: " +  Engine.scene.getLights().get(0).pos.y, xo+10, yo+122);
			g.drawString("Light source z: " +  Engine.scene.getLights().get(0).pos.z, xo+10, yo+137);			
		}
		g.drawString("Num of lines: " + Screen.LINE_NUM, xo+10, yo+152);
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