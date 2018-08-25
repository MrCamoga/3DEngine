package com.camoga.engine;

import com.camoga.engine.geom.Matrix;

public interface Renderable {
	public void render(Engine main);

	public void transform(Matrix rotation, Scene scene);
	
	public default int[][] getFaces() {
		return new int[][] {};
	}
}
