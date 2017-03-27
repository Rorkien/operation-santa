package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Graphics.Image;

public class WaterTile extends Tile {
	public int currentAnim = 0;
	
	public WaterTile(boolean hazard, int x, int y) {
		super(Image.tiles[4][14], x, y);
		this.isTickable = true;
		this.isHazard = hazard;
	}
	
	public void tick() {
		if (Game.instance.ticks % 12 == 0) {
			this.currentAnim = ++this.currentAnim % 3;
			this.img = Image.tiles[5 + this.currentAnim][14];
		}
	}
}
