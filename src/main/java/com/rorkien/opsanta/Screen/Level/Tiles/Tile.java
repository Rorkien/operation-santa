package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Graphics.Image;

public class Tile {
	public boolean isTickable = false;
	public boolean isSolid = false;
	public boolean isHazard = false;
	public boolean isRemoved = false;
	public boolean isBehind = false;
	public boolean isEdge = false;
	
	public Tile(Image img, int x, int y) {
		this.img = img;
		this.x = x;
		this.y = y;
	}
	
	public Image img;
	public int x, y;
	
	public void tick() {
		
	}
	
	public void render(int offsetX, int offsetY) {
		this.img.draw(this.x - offsetX, this.y - offsetY);
	}
}
