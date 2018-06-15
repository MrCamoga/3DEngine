package com.camoga.mg;

import java.awt.Graphics;

import com.camoga.engine.Engine;
import com.camoga.engine.Sprite;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.HollowModel;
import com.camoga.engine.model.Polyhedron;

public class Test extends Engine {

	public Polyhedron cube;
	
	public Test() {
		super();
		UPS = 60;
		
//		double[][] vertices = new double[][] {
//			{1,1,1},{1,1,-1},
//			{1,-1,1},{1,-1,-1},
//			{-1,1,1},{-1,1,-1},
//			{-1,-1,1},{-1,-1,-1},
//		};
//		
//		int[][] faces = new int[][] {
//			{5,1,3,7},{1,0,2,3},{0,4,6,2},{4,5,7,6},{4,0,1,5},{7,3,2,6}
//		};
//		
//		double[][] tc = new double[][] {
//			{0,0},{1,0},{1,1},{0,1},
//			{0,0},{1,0},{1,1},{0,1},
//			{0,0},{1,0},{1,1},{0,1},
//			{0,0},{1,0},{1,1},{0,1},
//			{0,0},{1,0},{1,1},{0,1},
//			{0,0},{1,0},{1,1},{0,1}
//		};
//		
//		Sprite sprite = new Sprite("/wic.png");
//		cube = new Polyhedron(vertices, faces, tc, 1, sprite);
//		scene.add(cube);
		
		double sin60 = Math.sin(Math.PI/3);
		double sin30 = 0.5;
		
		double[][] vertices = new double[][]{
			{0,1,0},
			{0,sin60,sin30},{sin30*sin30,sin60,sin30*sin60},{sin30*sin60,sin60,sin30*sin30},
			{sin30,sin60,0},{sin30*sin60,sin60,-(sin30*sin30)},{sin30*sin30,sin60,-sin30*sin60},
			{0,sin60,-sin30},{-(sin30*sin30),sin60,-sin30*sin60},{-sin30*sin60,sin60,-(sin30*sin30)},
			{-sin30,sin60,0},{-sin30*sin60,sin60,sin30*sin30},{-(sin30*sin30),sin60,sin30*sin60},
			
			//13
			{0,sin30,sin60},{sin30*sin60,sin30,sin60*sin60},{sin60*sin60,sin30,sin30*sin60},
			{sin60,sin30,0},{sin60*sin60,sin30,-sin30*sin60},{sin30*sin60,sin30,-sin60*sin60},
			{0,sin30,-sin60},{-sin30*sin60,sin30,-sin60*sin60},{-sin60*sin60,sin30,-sin30*sin60},
			{-sin60,sin30,0},{-sin60*sin60,sin30,sin30*sin60},{-sin30*sin60,sin30,sin60*sin60},
			
			//25
			{0,0,1},{sin30,0,sin60},{sin60,0,sin30},
			{1,0,0},{sin60,0,-sin30},{sin30,0,-sin60},
			{0,0,-1},{-sin30,0,-sin60},{-sin60,0,-sin30},
			{-1,0,0},{-sin60,0,sin30},{-sin30,0,sin60},
			
			//37
			{0,-sin30,sin60},{sin30*sin60,-sin30,sin60*sin60},{sin60*sin60,-sin30,sin30*sin60},
			{sin60,-sin30,0},{sin60*sin60,-sin30,-sin30*sin60},{sin30*sin60,-sin30,-sin60*sin60},
			{0,-sin30,-sin60},{-sin30*sin60,-sin30,-sin60*sin60},{-sin60*sin60,-sin30,-sin30*sin60},
			{-sin60,-sin30,0},{-sin60*sin60,-sin30,sin30*sin60},{-sin30*sin60,-sin30,sin60*sin60},
			
			//49
			{0,-sin60,sin30},{sin30-sin30*sin30,-sin60,sin30*sin60},{sin30*sin60,-sin60,sin30-sin30*sin30},
			{sin30,-sin60,0},{sin30*sin60,-sin60,-(sin30-sin30*sin30)},{sin30-sin30*sin30,-sin60,-sin30*sin60},
			{0,-sin60,-sin30},{-(sin30-sin30*sin30),-sin60,-sin30*sin60},{-sin30*sin60,-sin60,-(sin30-sin30*sin30)},
			{-sin30,-sin60,0},{-sin30*sin60,-sin60,sin30-sin30*sin30},{-(sin30-sin30*sin30),-sin60,sin30*sin60},
			{0,-1,0}
		};
		
		
		
		int[][] faces = {
				{0,0,2,1},{0,0,3,2},{0,0,4,3},{0,0,5,4},{0,0,6,5},{0,0,7,6},
				{0,0,8,7},{0,0,9,8},{0,0,10,9},{0,0,11,10},{0,0,12,11},{0,0,1,12},
				
				{1,2,14,13},{2,3,15,14},{3,4,16,15},{4,5,17,16},{5,6,18,17},{6,7,19,18},{7,8,20,19},
				{8,9,21,20},{9,10,22,21},{10,11,23,22},{11,12,24,23},{12,1,13,24},
				
				{13,14,26,25},{14,15,27,26},{15,16,28,27},{16,17,29,28},{17,18,30,29},{18,19,31,30},
				{19,20,32,31},{20,21,33,32},{21,22,34,33},{22,23,35,34},{23,24,36,35},{24,13,25,36},
				
				{25,26,38,37},{26,27,39,38},{27,28,40,39},{28,29,41,40},{29,30,42,41},{30,31,43,42},
				{31,32,44,43},{32,33,45,44},{33,34,46,45},{34,35,47,46},{35,36,48,47},{36,25,37,48},
				
				{37,38,50,49},{38,39,51,50},{39,40,52,51},{40,41,53,52},{41,42,54,53},{42,43,55,54},
				{43,44,56,55},{44,45,57,56},{45,46,58,57},{46,47,59,58},{47,48,60,59},{48,37,49,60},
				
				{49,50,61},{50,51,61},{51,52,61},{52,53,61},{53,54,61},{54,55,61},
				{55,56,61},{56,57,61},{57,58,61},{58,59,61},{59,60,61},{60,49,61},
		};
		
		double[][] textureCoords = {
				{1,0},{11/12.0,0},{11/12.0,1/6.0},{1,1/6.0},
				{11/12.0,0},{10/12.0,0},{10/12.0,1/6.0},{11/12.0,1/6.0},
				{10/12.0,0},{9/12.0,0},{9/12.0,1/6.0},{10/12.0,1/6.0},
				{9/12.0,0},{8/12.0,0},{8/12.0,1/6.0},{9/12.0,1/6.0},
				{8/12.0,0},{7/12.0,0},{7/12.0,1/6.0},{8/12.0,1/6.0},
				{7/12.0,0},{6/12.0,0},{6/12.0,1/6.0},{7/12.0,1/6.0},
				{6/12.0,0},{5/12.0,0},{5/12.0,1/6.0},{6/12.0,1/6.0},
				{5/12.0,0},{4/12.0,0},{4/12.0,1/6.0},{5/12.0,1/6.0},
				{4/12.0,0},{3/12.0,0},{3/12.0,1/6.0},{4/12.0,1/6.0},
				{3/12.0,0},{2/12.0,0},{2/12.0,1/6.0},{3/12.0,1/6.0},
				{2/12.0,0},{1/12.0,0},{1/12.0,1/6.0},{2/12.0,1/6.0},
				{1/12.0,0},{0,0},{0,1/6.0},{1/12.0,1/6.0},

				{1,1/6.0},{11/12.0,1/6.0},{11/12.0,1/3.0},{1,1/3.0},
				{11/12.0,1/6.0},{10/12.0,1/6.0},{10/12.0,1/3.0},{11/12.0,1/3.0},
				{10/12.0,1/6.0},{9/12.0,1/6.0},{9/12.0,1/3.0},{10/12.0,1/3.0},
				{9/12.0,1/6.0},{8/12.0,1/6.0},{8/12.0,1/3.0},{9/12.0,1/3.0},
				{8/12.0,1/6.0},{7/12.0,1/6.0},{7/12.0,1/3.0},{8/12.0,1/3.0},
				{7/12.0,1/6.0},{6/12.0,1/6.0},{6/12.0,1/3.0},{7/12.0,1/3.0},
				{6/12.0,1/6.0},{5/12.0,1/6.0},{5/12.0,1/3.0},{6/12.0,1/3.0},
				{5/12.0,1/6.0},{4/12.0,1/6.0},{4/12.0,1/3.0},{5/12.0,1/3.0},
				{4/12.0,1/6.0},{3/12.0,1/6.0},{3/12.0,1/3.0},{4/12.0,1/3.0},
				{3/12.0,1/6.0},{2/12.0,1/6.0},{2/12.0,1/3.0},{3/12.0,1/3.0},
				{2/12.0,1/6.0},{1/12.0,1/6.0},{1/12.0,1/3.0},{2/12.0,1/3.0},
				{1/12.0,1/6.0},{0,1/6.0},{0,1/3.0},{1/12.0,1/3.0},
				
				{1,1/3.0},{11/12.0,1/3.0},{11/12.0,0.5},{1,0.5},
				{11/12.0,1/3.0},{10/12.0,1/3.0},{10/12.0,0.5},{11/12.0,0.5},
				{10/12.0,1/3.0},{9/12.0,1/3.0},{9/12.0,0.5},{10/12.0,0.5},
				{9/12.0,1/3.0},{8/12.0,1/3.0},{8/12.0,0.5},{9/12.0,0.5},
				{8/12.0,1/3.0},{7/12.0,1/3.0},{7/12.0,0.5},{8/12.0,0.5},
				{7/12.0,1/3.0},{6/12.0,1/3.0},{6/12.0,0.5},{7/12.0,0.5},
				{6/12.0,1/3.0},{5/12.0,1/3.0},{5/12.0,0.5},{6/12.0,0.5},
				{5/12.0,1/3.0},{4/12.0,1/3.0},{4/12.0,0.5},{5/12.0,0.5},
				{4/12.0,1/3.0},{3/12.0,1/3.0},{3/12.0,0.5},{4/12.0,0.5},
				{3/12.0,1/3.0},{2/12.0,1/3.0},{2/12.0,0.5},{3/12.0,0.5},
				{2/12.0,1/3.0},{1/12.0,1/3.0},{1/12.0,0.5},{2/12.0,0.5},
				{1/12.0,1/3.0},{0,1/3.0},{0,0.5},{1/12.0,0.5},
				
				{1,0.5},{11/12.0,0.5},{11/12.0,2/3.0},{1,2/3.0},
				{11/12.0,0.5},{10/12.0,0.5},{10/12.0,2/3.0},{11/12.0,2/3.0},
				{10/12.0,0.5},{9/12.0,0.5},{9/12.0,2/3.0},{10/12.0,2/3.0},
				{9/12.0,0.5},{8/12.0,0.5},{8/12.0,2/3.0},{9/12.0,2/3.0},
				{8/12.0,0.5},{7/12.0,0.5},{7/12.0,2/3.0},{8/12.0,2/3.0},
				{7/12.0,0.5},{6/12.0,0.5},{6/12.0,2/3.0},{7/12.0,2/3.0},
				{6/12.0,0.5},{5/12.0,0.5},{5/12.0,2/3.0},{6/12.0,2/3.0},
				{5/12.0,0.5},{4/12.0,0.5},{4/12.0,2/3.0},{5/12.0,2/3.0},
				{4/12.0,0.5},{3/12.0,0.5},{3/12.0,2/3.0},{4/12.0,2/3.0},
				{3/12.0,0.5},{2/12.0,0.5},{2/12.0,2/3.0},{3/12.0,2/3.0},
				{2/12.0,0.5},{1/12.0,0.5},{1/12.0,2/3.0},{2/12.0,2/3.0},
				{1/12.0,0.5},{0,0.5},{0,2/3.0},{1/12.0,2/3.0},
				
				{1,2/3.0},{11/12.0,2/3.0},{11/12.0,5/6.0},{1,5/6.0},
				{11/12.0,2/3.0},{10/12.0,2/3.0},{10/12.0,5/6.0},{11/12.0,5/6.0},
				{10/12.0,2/3.0},{9/12.0,2/3.0},{9/12.0,5/6.0},{10/12.0,5/6.0},
				{9/12.0,2/3.0},{8/12.0,2/3.0},{8/12.0,5/6.0},{9/12.0,5/6.0},
				{8/12.0,2/3.0},{7/12.0,2/3.0},{7/12.0,5/6.0},{8/12.0,5/6.0},
				{7/12.0,2/3.0},{6/12.0,2/3.0},{6/12.0,5/6.0},{7/12.0,5/6.0},
				{6/12.0,2/3.0},{5/12.0,2/3.0},{5/12.0,5/6.0},{6/12.0,5/6.0},
				{5/12.0,2/3.0},{4/12.0,2/3.0},{4/12.0,5/6.0},{5/12.0,5/6.0},
				{4/12.0,2/3.0},{3/12.0,2/3.0},{3/12.0,5/6.0},{4/12.0,5/6.0},
				{3/12.0,2/3.0},{2/12.0,2/3.0},{2/12.0,5/6.0},{3/12.0,5/6.0},
				{2/12.0,2/3.0},{1/12.0,2/3.0},{1/12.0,5/6.0},{2/12.0,5/6.0},
				{1/12.0,2/3.0},{0,2/3.0},{0,5/6.0},{1/12.0,5/6.0},
				
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
				{0,sin60/2},{1,sin60/2},{1,0},
		};
		Polyhedron sphere = new Polyhedron(vertices, faces, textureCoords, 2, new Sprite("/earth.jpg"));
		scene.add(sphere);
		

//		Skeleton rhombic_dodecahedron = new Skeleton(new double[][]{
//			{1,1,1},{1,1,-1},{1,-1,1},{1,-1,-1},
//			{-1,1,1},{-1,1,-1},{-1,-1,1},{-1,-1,-1},
//			{2,0,0},{-2,0,0},{0,2,0},{0,-2,0},
//			{0,0,2},{0,0,-2}
//		}, 1, 20, 0xff0000aa);
//		
//		scene.add(rhombic_dodecahedron);
		
	}
	
	public void tick(double dt) {
		super.tick(dt);
	}
	
	public static void main(String[] args) {
		new Test().start();
	}

	@Override
	public void predraw(Screen screen) {
		
	}

	@Override
	public void postdraw(Graphics g) {
		
	}
}