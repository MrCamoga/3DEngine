package com.camoga.examples;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.lighting.DirectionalLight;
import com.camoga.engine.gfx.lighting.LightSource;
import com.camoga.engine.gfx.lighting.PointLight;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Model;
import com.camoga.engine.model.models.Mesh;

/**
 * This is another example. I've rendered a square mesh.
 * You can change the y coordinate and draw functions dependent of time.
 * 
 * @author MrCamoga
 *
 */
public class MeshTest extends Engine {

	public Model mesh;
	
	int width = 100;
	int height = 100;
	
	public MeshTest() {
		super();
		UPS = 60;
		cam.rot.x = Math.PI+0.4;
		
		cam.pos.x = 0;
		cam.pos.y = 0;
		cam.pos.z = -120;		
		
		mesh = new Mesh(width, height, 0.5, new Vec3(1,0.4,0.4), Material.brick);
		scene.add(mesh);
		scene.add(new DirectionalLight(0, 2, 0, new Vec3(0.8,0.8,0.8)));
	}
	
	public void tick(double dt) {
		super.tick(dt);
		
		//Draw function which may be dependent of time (using Engine.time)
		for(int y = 0; y < height; y++) {
			double yo = (y-height/2)/3.0;
			for(int x = 0; x < width; x++) {
				double xo = (x-width/2)/3.0;
//				mesh.vertices[x+y*width].y = Math.cos(y+Engine.time)+0.5*Math.cos(2*x+Engine.time)+0.333333333*Math.cos(3*x+Engine.time);
//				+Math.sin(i+Engine.time)+0.5*Math.sin(2*i+Engine.time)+0.333333333*Math.sin(3*i+Engine.time);
				double r = Math.sqrt(xo*xo+yo*yo);
				if(r!=0) mesh.vertices[x+y*width].y = 12*Math.sin(4*time-r)*Math.sin(r)/r;
				else mesh.vertices[x+y*width].y = 12*Math.sin(4*time-r);
				r = Math.sqrt((xo+6)*(xo+6)+yo*yo);
				if(r!=0) mesh.vertices[x+y*width].y += 12*Math.sin(4*time-r)*Math.sin(r)/r;
				else mesh.vertices[x+y*width].y += 12*Math.sin(4*time-r);
				r = Math.sqrt((xo-6)*(xo-6)+yo*yo);
				if(r!=0) mesh.vertices[x+y*width].y += 12*Math.sin(4*time-r)*Math.sin(r)/r;
				else mesh.vertices[x+y*width].y += 12*Math.sin(4*time-r);
				//hyperbolic paraboloid
				//mesh.vertices[x+y*width].y = xo*xo/25-yo*yo/25;
				
				//semisphere
//				mesh.vertices[x+y*width].y = -Math.sqrt(625-xo*xo-yo*yo);
				
//				mesh.vertices[x+y*width].y = Math.exp(-xo*xo-yo*yo)*30;
				
			}
		}
	}
	
	public static void main(String[] args) {
		new MeshTest().start();
	}

	@Override
	public void predraw(Screen screen) {
		
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}