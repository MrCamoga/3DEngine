package com.camoga.engine.model.models;

import com.camoga.engine.model.Wireframe;

public class CubeFrame extends Wireframe {

	public CubeFrame(double scale, double dotSize, int color) {
		super(Cube.Vertices(), Cube.Edges(), scale, dotSize, color);
	}
}
