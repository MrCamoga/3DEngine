package com.camoga.engine;

import com.camoga.engine.geom.Matrix4d;

public interface Renderable {
	public void render(Engine main);

	public void transform(Matrix4d rotation, Scene scene);
}
