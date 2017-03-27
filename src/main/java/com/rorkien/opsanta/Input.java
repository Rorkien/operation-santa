package com.rorkien.opsanta;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Input implements KeyListener, MouseListener, MouseMotionListener, FocusListener {
	public int mx, my;
	public int lmx, lmy;
	public char lkey;
	public boolean mb1, mb2, mb3;
	public boolean left, right, up, down, action, action2;
	public boolean w, s, a, d;
	public boolean keys[] = new boolean[65535];
	
	public Input(Game target) {
		target.addKeyListener(this);
		target.addMouseListener(this);
		target.addMouseMotionListener(this);
		target.addFocusListener(this);
	}
	
	public void tick() {
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		action = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_A];
		action2 = keys[KeyEvent.VK_CONTROL];
		
		//w = keys[KeyEvent.VK_W];
		//s = keys[KeyEvent.VK_S];
		//a = keys[KeyEvent.VK_A];
		//d = keys[KeyEvent.VK_D];
		
		
	}

	public void mouseMoved(MouseEvent e) {
		lmx = mx;
		lmy = my;
		
		mx = e.getX();
		my = e.getY();		
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);		
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) mb1 = true;
		else if (e.getButton() == MouseEvent.BUTTON2) mb2 = true;
		else if (e.getButton() == MouseEvent.BUTTON3) mb3 = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) mb1 = false;
		else if (e.getButton() == MouseEvent.BUTTON2) mb2 = false;
		else if (e.getButton() == MouseEvent.BUTTON3) mb3 = false;		
	}

	public void keyPressed(KeyEvent e) {
		lkey = e.getKeyChar();

		int key = e.getKeyCode();
		if (key > 0 || key < keys.length) keys[key] = true;
	}

	public void keyReleased(KeyEvent e) {
		lkey = 0;
		
		int key = e.getKeyCode();
		if (key > 0 || key < keys.length) {
			keys[key] = false;
		}
	}
	
	public void mouseClicked(MouseEvent arg0) {
	}	

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
	
	public void keyTyped(KeyEvent e) {
	}

	public void focusGained(FocusEvent arg0) {		
	}

	public void focusLost(FocusEvent arg0) {	
	}
}
