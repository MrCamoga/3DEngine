package com.camoga.engine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.camoga.engine.geom.Matrix;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;
import com.camoga.engine.gfx.lighting.DirectionalLight;
import com.camoga.engine.gfx.lighting.LightSource;
import com.camoga.engine.gfx.lighting.PointLight;
import com.camoga.engine.input.Camera;
import com.camoga.engine.input.Mouse;

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
	
	public static double Ia = 1;
	
	public Scene(Camera cam, Engine main) {
		this.cam = cam;
		this.main = main;
		lights.add(new PointLight(0, 10, -6.7, new Vec3(1,1,1)));
	}
	
	/**
	 * Add a model
	 * @param model
	 */
	public void add(Renderable model) {
		models.add(model);
	}
	

	//FIXME transform scene objects in render();
	/**
	 * Transforms the vertices of the models according to the camera, position,...
	 * 
	 */
	public void tick() {
		Matrix rotation = rotX(-cam.rot.x)
				.multiply(rotY(-cam.rot.y))
				.multiply(rotZ(-cam.rot.z));
		
		for(Renderable r: models) {
			r.transform(rotation, this);
		}
		
		for(LightSource light : lights) {
			Vec4d t = rotation
//					.multiply(translate(-cam.pos.x, -cam.pos.y, -cam.pos.z))
					.multiply(light.pos);
			light.transform = t;
//			System.out.println(light.transform);
		}
	}
	
	//TODO use TestHighDim.java methods
	public Matrix rotX(double x) {
		return new Matrix(new double[][]{
			{1,	0,			0,				0},
			{0,	Math.cos(x),-Math.sin(x),	0},
			{0,	Math.sin(x),Math.cos(x),	0},
			{0,	0,			0,				1}
		});
	}
	
	public Matrix rotY(double y) {
		return new Matrix(new double[][]{
			{Math.cos(y),0,Math.sin(y),0},
			{0,1,0,0},
			{-Math.sin(y),0,Math.cos(y),0},
			{0,0,0,1}
		});
	}
	
	public Matrix rotZ(double z) {
		return new Matrix(new double[][]{
			{Math.cos(z),-Math.sin(z),0,0},
			{Math.sin(z),Math.cos(z),0,0},
			{0,0,1,0},
			{0,0,0,1}
		});
	}
	
	public static Matrix translate(double dx, double dy, double dz) {
		return new Matrix(new double[][] {
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
		
		for(LightSource light : lights) {
			light.render(main);
		}
	}
	
	public int getSelectedModelID() {
		if(Mouse.face==null) return -1;
		int faceCount = 0;
		for(int i = 0; i < models.size(); i++) {
			int[][] faces = models.get(i).getFaces();
			if((faceCount += faces.length) > Mouse.face) return i;
		}
		
		return -1;
	}
	
	public List<LightSource> getLights() {
		return lights;
	}
}