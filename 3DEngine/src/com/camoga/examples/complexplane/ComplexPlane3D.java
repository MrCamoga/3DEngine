package com.camoga.examples.complexplane;

import java.awt.Graphics;

import com.camoga.complex.Complex;
import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.lighting.DirectionalLight;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;
import com.camoga.engine.model.models.Mesh;
import com.camoga.examples.complexplane.grapher.Plotter;

public class ComplexPlane3D extends Engine {
	
	public Model mesh;
	private int width = 101, height = 101;
	
	public ComplexPlane3D() {
		super();
		scene.add(new DirectionalLight(0, 1, 0, new Vec3(100,100,100)));
		UPS = 60;
		cam.rot.x = Math.PI+0.4;
		
		cam.pos.x = 0;
		cam.pos.y = 0;
		cam.pos.z = -50;
		
		mesh = new Mesh(width, height, 0.5, Material.metallic);
		scene.add(mesh);		
		
		Plotter.plot((Complex z) -> Complex.mul(Complex.ln(z), Complex.valueOf(10)), 
				width, height, -0.5, -1, 1.5, 1, 0x00);
		

		for(int i = 0; i < mesh.vertexColor.length; i++) {
			mesh.vertexColor[i] = Plotter.vertexColor[i];
		}
		
		for(int i = 0; i < mesh.vertices.length; i++) {
			mesh.vertices[i].y = -Plotter.vertexHeight[i];
			System.out.println(Plotter.vertexHeight[i]);
		}
	}
	
	public static void main(String[] args) {
		new ComplexPlane3D().start();
	}
	
	public void tick(double dt) {
		super.tick(dt);
	}

	public void predraw(Screen screen) {
//		screen.fillTriangle(new Vec3[] {new Vec3(0,0,1), new Vec3(1000,0,3), new Vec3(500,730,5)}, 
//				new Vec3[] {new Vec3(0,0,1), new Vec3(1000,0,3), new Vec3(500,730,5)}, 
//				null, null, null, null, null, 
//				new Vec3(1,0,0), new Vec3(0,1,0), new Vec3(0,0,1), 0, false, true, Material.plastic, false);
////			
//		screen.fillTriangle(new Vec3[] {new Vec3(0,200,1), new Vec3(200,200,3), new Vec3(100,346,5)}, 
//				new Vec3[] {new Vec3(0,200,1), new Vec3(200,200,3), new Vec3(100,346,5)}, 
//				null, null, null, null, null, 
//				new Vec3(1,0,0), new Vec3(0,1,0), new Vec3(0,0,1), 0, false, false, Material.plastic, true);
	}

	public void postdraw(Graphics g) {
		
	}
}