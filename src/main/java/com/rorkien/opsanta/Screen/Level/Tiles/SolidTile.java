package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Graphics.Image;

public class SolidTile extends Tile {
	
	public SolidTile(Image img, int x, int y) {
		super(img, x, y);
		this.isSolid = true;
	}
}
