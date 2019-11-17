package com.camoga.examples.optimizesphere;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.gfx.Sprite;
import com.camoga.engine.model.Material;
import com.camoga.engine.model.Wireframe;
import com.camoga.engine.model.models.Sphere;

public class PointsArrangement extends Engine {

	ArrayList<Point> points = new ArrayList<>();
	
	Wireframe spherepoints = new Wireframe(1, 0, 0xffff0000);
	Sphere sphere = new Sphere(0.9, 20, 20, 1, null, new Sprite(1, 1, 0xcc0000ff), Material.plastic);
	
	public boolean connectvertices = true;
	public double connectionthreshold = 1.51;
	
	public static int NUMOFPOINTS = 3;
	public static double VISCOUSFRICTION = 5.0;
	public static double REPULSIONFORCE = 0.05;
	
	public PointsArrangement() {
		super();
		UPS = 500;
		for(int i = 0; i < NUMOFPOINTS; i++) {
			points.add(new Point());
			spherepoints.addVertex(points.get(i).pos.x, points.get(i).pos.y, points.get(i).pos.z);
		}
		scene.add(spherepoints);
//		scene.add(sphere);
	}
	
	public static void main(String[] args) {
		new PointsArrangement().start();
	}

	long timer = 500;
	
	public void tick(double dt) {
		super.tick(dt);
		if(timer > 0.5*UPS) {
			if(key.ENTER) {
				points.add(new Point());
				timer = 0;
			} else if(key.T) {
				points.remove(points.size()-1);
				timer = 0;
			}			
		}
		
		for(Point p1 : points) {
			for(Point p2 : points) {
				if(p1.equals(p2)) continue;
				p1.repulse(p2);
			}
		}
		
		spherepoints.clear();
		
		for(Point p : points) {
			p.move(1/5.0);
			spherepoints.addVertex(p.pos.x, p.pos.y, p.pos.z);
		}
		
		if(connectvertices && points.size() > 1) {
			double[][] distance = new double[points.size()][points.size()];
			
			double[] mindist = new double[points.size()];
			for(int i = 0; i < mindist.length; i++) {
				mindist[i] = 200;
			}
			
			for(int i = 0; i < points.size(); i++) {
				for(int j = i; j < points.size(); j++) {
					distance[i][j] = Vec3.modSq(Vec3.sub(points.get(i).pos, points.get(j).pos));
					distance[j][i] = distance[i][j];
					if(i!=j) {
						if(mindist[i] > distance[i][j]) {
							mindist[i] = distance[i][j];
						}
						if(mindist[j] > distance[i][j]) {
							mindist[j] = distance[i][j];
						}
					}
				}
			}	
			
			for(int i = 0; i < distance.length; i++) {
				for(int j = 0; j < distance.length; j++) {
					distance[i][j] /= mindist[i];
				}
			}
			
//			System.out.println(Arrays.deepToString(distance).replace("],", "]\n"));
//			System.out.println();
			
			ArrayList<double[]>[] distances = new ArrayList[distance.length]; // distancias ordenadas    [index, distance]
			
			for(int i = 0; i < distances.length; i++) {
				distances[i] = new ArrayList<>();
				for(int j = 0; j < distance[i].length; j++) {
					distances[i].add(new double[] {j, distance[i][j]});
				}
				distances[i].sort(Comparator.comparing(e -> e[1]));
			}
			
//			System.out.println(Arrays.deepToString(distances[1].toArray()));
			
			for(int i = 0; i < distances.length; i++) {
				int j = 1;
				while(j < distances.length && distances[i].get(j)[1] < connectionthreshold) {
					spherepoints.addEdge(i, (int)distances[i].get(j)[0]);
					j++;
				}
			}
			
		}
		
		timer++;
	}
	
	public void predraw(Screen screen) {	}

	public void postdraw(Graphics g) {	
		g.setFont(new Font("Verdana", Font.BOLD, 18));
		g.drawString("Num of points: " + points.size(), 10, 260);
		g.drawString("Connection threshold: " + connectionthreshold, 10, 280);
	}
}