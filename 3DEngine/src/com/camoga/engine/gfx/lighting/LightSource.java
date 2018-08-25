package com.camoga.engine.gfx.lighting;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;

public abstract class LightSource {
	
	public enum LightType {
		DIRECTIONAL, POINT;
	}
	
	public Vec3 rot;
	public Vec4d pos;
	public Vec4d transform;
	private LightType type;
	private Vec3 colorIntensity;
	
	//TODO implement colored light and light intensity
	//DONE Add point light
	public LightSource(double x, double y, double z, double dx, double dy, double dz, Vec3 I, LightType type) {
		rot = new Vec3(dx, dy, dz);
		pos = new Vec4d(x,y,z);
		transform = new Vec4d(x, y, z);
		this.type = type;
		this.colorIntensity = I;
	}
	
	public LightType getType() {
		return type;
	}
	
	public Vec3 getIntensity() {
		return colorIntensity;
	}

	public void render(Engine main) {
		main.renderPoint(new Vec4d[] {transform}, 300, 0xffffffff);
//		System.out.println("light");
	}
}