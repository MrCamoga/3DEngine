package com.camoga.engine.gfx.lighting;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;

public class DirectionalLight extends LightSource {

	//TODO test
	public DirectionalLight(double dx, double dy, double dz, Vec3 I) {
		super(dx,dy,dz,dx,dy,dz, I, LightType.DIRECTIONAL);
	}
	
	@Override
	public void render(Engine main) {
		super.render(main);
	}
}