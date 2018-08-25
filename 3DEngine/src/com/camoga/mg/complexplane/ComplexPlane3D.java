package com.camoga.mg.complexplane;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;
import com.camoga.engine.model.models.Mesh;
import com.camoga.mg.complexplane.grapher.Plotter;
import com.camoga.mg.complexplane.grapher.functions.Mandelbrot;

public class ComplexPlane3D extends Engine {
	
	public Model mesh;
	private int width = 80, height = 80;
	
	public ComplexPlane3D() {
		super();
		UPS = 60;
		cam.rot.x = Math.PI+0.4;
		
		cam.pos.x = 0;
		cam.pos.y = 0;
		cam.pos.z = -50;
		
		mesh = new Mesh(width, height, 0.5, Material.brick);
		scene.add(mesh);
		
		
		Plotter.plot(new Mandelbrot(100), 
				width, height, -0.925-0.032, 0.266-0.032, -0.925+0.032, 0.266+0.032, 0x00);
		

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
//		screen.fillTriangle(new Vec3[] {new Vec3(0,0,0), new Vec3(1000,0,0), new Vec3(500,730,0)}, new Vec3[] {new Vec3(0,0,0), new Vec3(100,0,0), new Vec3(50,73,0)}, null, null, null, null, null, 
//		new Vec3(1,0,0.789), new Vec3(1,0,0.2156), new Vec3(1,0,0.1568), 0, false, Material.plastic);
	}

	public void postdraw(Graphics g) {
		
	}
}