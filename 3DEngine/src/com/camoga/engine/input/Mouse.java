package com.camoga.engine.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import com.camoga.engine.Engine;

public class Mouse implements MouseListener, MouseMotionListener {

	private Engine main;
	
	public Point2D pos = new Point(0,0);
	
	public Integer face = null;
	
	public Mouse(Engine main) {
		this.main = main;
		main.addMouseListener(this);
		main.addMouseMotionListener(this);
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		pos = e.getPoint();
		face = main.screen.faceIDbuffer[(int)(pos.getX()/Engine.SCALE)+(int)(pos.getY()/Engine.SCALE)*main.screen.width];
	}

}
