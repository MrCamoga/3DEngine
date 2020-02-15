package com.camoga.examples.rubikscube;

import java.awt.Graphics;
import java.util.HashMap;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;

public class RubiksCube extends Engine {

	Cubie[] boxes = new Cubie[8];
	int[] cubies = new int[8];
	HashMap<Integer, Vec3> IDpos = new HashMap<>();
	
//	int[][] R = new int[][]{{9,3,21,27},{6,12,24,18}};
//	int[][] L = new int[][]{{9,3,21,27},{6,12,24,18}};
//	int[][] U = new int[][]{{9,3,21,27},{6,12,24,18}};
//	int[][] D = new int[][]{{9,3,21,27},{6,12,24,18}};
//	int[][] F = new int[][]{{9,3,21,27},{6,12,24,18}};
//	int[][] B = new int[][]{{9,3,21,27},{6,12,24,18}};

	int[] R = new int[]{3,1,5,7};
	int[] U = new int[]{0,1,3,2};
	int[] L = new int[]{0,2,6,4};
	int[] D = new int[]{4,5,7,6};
	int[] F = new int[]{2,3,7,6};
	int[] B = new int[]{1,0,4,5};
	
	public RubiksCube() {
		super();
		lighting = false;
		cam.omega *= 3;
		
		IDpos.put(0, new Vec3(-1.0,1.0,1.0));
		IDpos.put(1, new Vec3(1.0,1.0,1.0));
		IDpos.put(2, new Vec3(-1.0,1.0,-1.0));
		IDpos.put(3, new Vec3(1.0,1.0,-1.0));
		IDpos.put(4, new Vec3(-1.0,-1.0,1.0));
		IDpos.put(5, new Vec3(1.0,-1.0,1.0));
		IDpos.put(6, new Vec3(-1.0,-1.0,-1.0));
		IDpos.put(7, new Vec3(1.0,-1.0,-1.0));
		
		for(int i = 0; i < boxes.length; i++) {
			boxes[i] = new Cubie(i);
			boxes[i].setPos(IDpos.get(i));
		}
		
		for(Cubie c : boxes) scene.add(c.cubie);
		
		//Update position
		//Update rotation
		
		
	}
	
	boolean pressed = false;
	int timer = 0;
	public void tick(double dt) {
		super.tick(dt);
		if(timer > 10) {
			timer = 0;
			pressed = false;
		}
		timer++;
		if(key.L && !pressed) {
			pressed = true;
			timer = 0;
			perm(R, 0);
			rotate(R, 0);
		}
		if(key.J && !pressed) {
			pressed = true;
			timer = 0;
			perm(L, 3);
			rotate(L, 3);
		}
		if(key.H && !pressed) {
			pressed = true;
			timer = 0;
			perm(U, 1);
			rotate(U, 1);
		}
		if(key.N && !pressed) {
			pressed = true;
			timer = 0;
			perm(D, 4);
			rotate(D, 4);
		}
		if(key.K && !pressed) {
			pressed = true;
			timer = 0;
			perm(F, 2);
			rotate(F, 2);
		}
		if(key.I && !pressed) {
			pressed = true;
			timer = 0;
			perm(B, 5);
			rotate(B, 5);
		}
		
//		for(Cubie c : boxes) c.setPos(IDpos.get(c.id));
	}
	
	/**
	 * 
	 * @param cycle
	 * @param rot 0 = x, 1 = y, 2 = z, 3 = x', 4 = y', 5' = z'
	 */
	public void perm(int[] cycle, int rot) {
//		for(int i = 1; i < cycle.length; i++) {
//			Vec3 pos = IDpos.get(cycle[0]);
//			Vec3 pos2 = IDpos.get(cycle[i]);
//			IDpos.replace(cycle[0], pos2);
//			IDpos.replace(cycle[i], pos);
//		}
		
		//rotate
		if(rot > 2) for(int k = 0; k < 3; k++) for(int i = 0; i < cycle.length; i++) {
			int a = cubies[cycle[i]]/6;
			int b = cubies[cycle[i]]%6;
			switch(rot%3) {
			case 0:
				cubies[cycle[i]] = b*6 + (a+3)%6;
				break;
			case 1:
				cubies[cycle[i]] = a*6 + (a%2==0 ? ((b+1)%6):((b-1)%6));
				break;
			case 2:
				cubies[cycle[i]] = (b%2==0 ? ((a+1)%6):((a-1)%6)) + b;
				break;
			}
		}	
		
		//perm
		for(int i = 1; i < cycle.length; i++) {
			int p1 = cubies[cycle[0]];
			int p2 = cubies[cycle[i]];
			cubies[cycle[0]] = p2;
			cubies[cycle[i]] = p1;
		}
		
		for(int i = 1; i < cycle.length; i++) {
			int p1 = boxes[cycle[0]].id;
			int p2 = boxes[cycle[i]].id;
			boxes[cycle[0]].id = p2;
			boxes[cycle[i]].id = p1;
		}
	}
	
	public void rotate(int[] cycle, int rot) {
		for(int i = 0; i < cycle.length; i++) {
			switch(rot) {
			case 0:
				boxes[cycle[i]].cubie.rotate(boxes[cycle[i]].cubie.rot.add(new Vec3(-Math.PI/2,0,0)));
				break;
			case 1:
				boxes[cycle[i]].cubie.rotate(boxes[cycle[i]].cubie.rot.add(new Vec3(0,-Math.PI/2,0)));
				break;
			case 2:
				boxes[cycle[i]].cubie.rotate(boxes[cycle[i]].cubie.rot.add(new Vec3(0,0,-Math.PI/2)));
				break;
			case 3:
				boxes[cycle[i]].cubie.rotate(boxes[cycle[i]].cubie.rot.add(new Vec3(Math.PI/2,0,0)));
				break;
			case 4:
				boxes[cycle[i]].cubie.rotate(boxes[cycle[i]].cubie.rot.add(new Vec3(0,Math.PI/2,0)));
				break;
			case 5:
				boxes[cycle[i]].cubie.rotate(boxes[cycle[i]].cubie.rot.add(new Vec3(0,0,Math.PI/2)));
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		new RubiksCube().start();
	}
	
	public void predraw(Screen screen) {
		
	}

	public void postdraw(Graphics g) {
		
	}
}