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
import com.camoga.test4d.model.Polytope;

/**
 * This class is used to load, transform and pass the models to the renderer
 * 
 * @author MrCamoga
 *
 */
public class Scene {

	private Camera cam;
	private Engine main;
	private List<HollowModel> hmodels = new ArrayList<HollowModel>();
	private List<Model> polyhedra = new ArrayList<Model>();
	private List<Polytope> polytope = new ArrayList<>();
	private List<LightSource> lights = new ArrayList<LightSource>();
	
	public Scene(Camera cam, Engine main) {
		this.cam = cam;
		this.main = main;
		lights.add(new LightSource(0, 1, -1));
	}
	
	/**
	 * Add a model
	 * @param model
	 */
	public void add(HollowModel model) {
		hmodels.add(model);
	}
	
	/**
	 * Add a model
	 * @param p
	 */
	public void add(Polytope p) {
		polytope.add(p);
	}
	
	/**
	 * Add a model
	 * @param model
	 */
	public void add(Model model) {
		polyhedra.add(model);
	}
	
	/**
	 * Transforms the vertices of the models according to the camera, position,...
	 * 
	 */
	public void tick() {
		for(HollowModel m:hmodels) {
			for(int i = 0; i < m.vertices.size(); i++) {
				m.transform.set(i, rotX(-cam.rot.x)
						.multiply(rotY(-cam.rot.y))
						.multiply(rotZ(-cam.rot.z))
						.multiply(scale(m.scale))
//						.multiply(translate(-cam.pos.x, -cam.pos.y, -cam.pos.z))
						.multiply(m.vertices.get(i)));
			}
		}
		
		for(LightSource light : lights) {
//			Vec4d t = rotX(-cam.rot.x)
//					.multiply(rotY(-cam.rot.y))
//					.multiply(rotZ(-cam.rot.z))
//					.multiply(light.getDir());
//			light.transform = new Vec3(t.x, t.y, t.z);
		}
		
		for(Model m:polyhedra) {
			for(int i = 0; i < m.vertices.length; i++) {
				m.transform[i] = rotX(-cam.rot.x)
						.multiply(rotY(-cam.rot.y))
						.multiply(rotZ(-cam.rot.z))
						.multiply(scale(m.scale))
//						.multiply(translate(-cam.pos.x, -cam.pos.z, -cam.pos.z))
//						.multiply(translate(m.pos.x, m.pos.y, m.pos.z))
						.multiply(m.vertices[i]);
			}
//						.multiply(translate(-cam.pos.x, -cam.pos.y, -cam.pos.z))
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
	
	public Matrix4d scale(double s) {
		return new Matrix4d(new double[][]{
			{s,0,0,0},
			{0,s,0,0},
			{0,0,s,0},
			{0,0,0,1}
		});
	}
	
	public Matrix4d translate(double dx, double dy, double dz) {
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
		for(HollowModel m: hmodels) {
			main.renderPoint(g,m.transform.toArray(new Vec4d[] {}), m.dotSize, m.color);	
		}
		for(Model p: polyhedra) {
//			main.renderPolygons(g, p.transform, p.faces, p.textureCoords, p.color);
			p.render(main, g);
		}
	}
	
	public List<LightSource> getLights() {
		return lights;
	}
}