package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Graphics.Image;

public class RaisingTile extends Tile {
	public int life = 30;
	
	public RaisingTile(Image img, int x, int y) {
		super(img, x, y);
		this.isTickable = true;
	}
	
	public void tick() {
		this.y-=2;
		life--;
		if (life <= 0) this.isRemoved = true;
	}
}
