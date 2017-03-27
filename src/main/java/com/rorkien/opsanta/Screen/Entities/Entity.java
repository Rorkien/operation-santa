package com.rorkien.opsanta.Screen.Entities;

import java.awt.Rectangle;

import com.rorkien.opsanta.Screen.Level.Level;


public class Entity {
	public Level level;
	public float x, y;
	public int hitboxX, hitboxY;
	public int width, height;
	public boolean isExpired = false;
	public boolean isRemoved = false;
	public int type = 0;
	public int life = 1;
	
	/* Friendly to:
	 * 0 = player
	 * 1 = enemies 
	 */
	
	public int getXPos() {
		return (int) (this.x + this.hitboxX);
	}
	
	public int getYPos() {
		return (int) (this.y + this.hitboxY);
	}
		
	public boolean collides(Entity e) {
		if (e.isExpired) return false;
		Rectangle a = new Rectangle(this.getXPos(), this.getYPos(), this.width, this.height);
		Rectangle b = new Rectangle(e.getXPos(), e.getYPos(), e.width, e.height);
		
		//This is ugly
		if (a.intersects(b)) {
			return true;
		}
		else return false;
	}
	
	public boolean yCollides(Entity e) {
		if (e.isExpired) return false;
		Rectangle a = new Rectangle(0, this.getYPos(), this.width, this.height);
		Rectangle b = new Rectangle(0, e.getYPos(), e.width, e.height);
		
		//This is also ugly
		if (a.intersects(b)) {
			return true;
		}
		else return false;
	}
	
	public void kill() {
		
	}
	
	public void tick() {
		
	}
	public void render(int offsetX, int offsetY) {
		
	}

}
