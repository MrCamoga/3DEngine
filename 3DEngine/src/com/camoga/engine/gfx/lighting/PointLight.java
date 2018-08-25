package com.camoga.engine.gfx.lighting;

import com.camoga.engine.geom.Vec3;

public class PointLight extends LightSource {

	public PointLight(double x, double y, double z, Vec3 I) {
		super(x, y, z, 0,0,0, I, LightType.POINT);
	}
}