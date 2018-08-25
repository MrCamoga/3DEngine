package com.camoga.engine.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import com.camoga.engine.Engine;

public class Mouse implements MouseListener, MouseMotionListener {

	private Engine main;
	
	public static Point2D pos = new Point(0,0);
	
	public static Integer face = null;
	public static boolean dragging = false;
	
	public Mouse(Engine main) {
		this.main = main;
		main.addMouseListener(this);
		main.addMouseMotionListener(this);
	}
	
	public void tick() {
		face = main.screen.faceIDbuffer[(int)(pos.getX()/Engine.SCALE)+(int)(pos.getY()/Engine.SCALE)*main.screen.width];
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		dragging = false;
		
	}

	public void mouseDragged(MouseEvent e) {
		pos = e.getPoint();
		dragging = true;		
	}

	//DONE highlighted face updates slowly at low framerate and flickers
	public void mouseMoved(MouseEvent e) {
		pos = e.getPoint();
	}

}
