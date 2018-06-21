package com.camoga.engine;

import com.camoga.engine.geom.Vec3;
import com.camoga.engine.geom.Vec4d;

public class LightSource {
	public Vec4d dir;
	public Vec3 transform;
	
	//TODO implement colored light
	public LightSource(double x, double y, double z) {
		dir = new Vec4d(x, y, z,1);
		transform = new Vec3(x, y, z);
	}
	
	public Vec4d getDir() {
		return dir;
	}
}