package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Graphics.Image;

public class LadderTile extends Tile {
	
	public LadderTile(int x, int y) {
		super(Image.tiles[8][7], x, y);
		this.isBehind = true;
	}
}
