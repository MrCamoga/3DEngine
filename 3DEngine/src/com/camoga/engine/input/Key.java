package com.camoga.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.camoga.engine.Engine;

public class Key implements KeyListener {

	/**
	 * Creates Keyboard object
	 * @param main
	 */
	public Key(Engine main) {
		main.addKeyListener(this);
	}

	public boolean[] keys = new boolean[255];
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean in;
	public boolean out;
	public boolean Q;
	public boolean W;
	public boolean E;
	public boolean A;
	public boolean S;
	public boolean D;
	public boolean I;
	public boolean H;
	public boolean J;
	public boolean K;
	public boolean L;
	public boolean N;
	public boolean T;
	public boolean ENTER;
	
	/**
	 * Updates key states 
	 */
	public void tick() {
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		in = keys[KeyEvent.VK_NUMPAD4];
		out = keys[KeyEvent.VK_NUMPAD1];
		
		Q = keys[KeyEvent.VK_Q];
		W = keys[KeyEvent.VK_W];
		E = keys[KeyEvent.VK_E];
		A = keys[KeyEvent.VK_A];
		S = keys[KeyEvent.VK_S];
		D = keys[KeyEvent.VK_D];
		
		I = keys[KeyEvent.VK_I];
		H = keys[KeyEvent.VK_H];
		J = keys[KeyEvent.VK_J];
		K = keys[KeyEvent.VK_K];
		L = keys[KeyEvent.VK_L];
		N = keys[KeyEvent.VK_N];
		
		T = keys[KeyEvent.VK_T];
		
		ENTER = keys[KeyEvent.VK_ENTER];
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
}