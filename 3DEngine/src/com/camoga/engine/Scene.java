package com.camoga.engine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.camoga.engine.geom.Matrix4d;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.input.Camera;
import com.camoga.engine.model.HollowModel;
import com.camoga.engine.model.Model;

/**
 * This class is used to load, transform and pass the models to the renderer
 * 
 * @author MrCamoga
 *
 */
public class Scene {

	public Camera cam;
	private Engine main;
	private List<Renderable> models = new ArrayList<Renderable>();
	
	private List<LightSource> lights = new ArrayList<LightSource>();
	
	public Scene(Camera cam, Engine main) {
		this.cam = cam;
		this.main = main;
		lights.add(new LightSource(0, -2, 0));
	}
	
	/**
	 * Add a model
	 * @param model
	 */
	public void add(Renderable model) {
		models.add(model);
	}
	
	/**
	 * Transforms the vertices of the models according to the camera, position,...
	 * 
	 */
	public void tick() {
		Matrix4d rotation = rotX(-cam.rot.x)
				.multiply(rotY(-cam.rot.y))
				.multiply(rotZ(-cam.rot.z));
		
		for(Renderable r: models) {
			r.transform(rotation, this);
		}
		
		for(LightSource light : lights) {
//			Vec4d t = rotation
//					.multiply(light.getDir());
//			light.transform = new Vec3(t.x, t.y, t.z);
		}
	}
	
	//TODO use TestHighDim.java methods
	public Matrix4d rotX(double x) {
		return new Matrix4d(new double[][]{
			{1,	0,			0,				0},
			{0,	Math.cos(x),-Math.sin(x),	0},
			{0,	Math.sin(x),Math.cos(x),	0},
			{0,	0,			0,				1}
		});
	}
	
	public Matrix4d rotY(double y) {
		return new Matrix4d(new double[][]{
			{Math.cos(y),0,Math.sin(y),0},
			{0,1,0,0},
			{-Math.sin(y),0,Math.cos(y),0},
			{0,0,0,1}
		});
	}
	
	public Matrix4d rotZ(double z) {
		return new Matrix4d(new double[][]{
			{Math.cos(z),-Math.sin(z),0,0},
			{Math.sin(z),Math.cos(z),0,0},
			{0,0,1,0},
			{0,0,0,1}
		});
	}
	
	public static Matrix4d scale(double s) {
		return new Matrix4d(new double[][]{
			{s,0,0,0},
			{0,s,0,0},
			{0,0,s,0},
			{0,0,0,1}
		});
	}
	
	public static Matrix4d translate(double dx, double dy, double dz) {
		return new Matrix4d(new double[][] {
			{1,0,0,dx},
			{0,1,0,dy},
			{0,0,1,dz},
			{0,0,0,1}
		});
	}
	
	/**
	 * Passes the models to the renderer
	 * @param g 
	 */
	public void render(Graphics g) {
		for(Renderable m: models) {
			m.render(main);
		}
	}
	
	public List<LightSource> getLights() {
		return lights;
	}
}