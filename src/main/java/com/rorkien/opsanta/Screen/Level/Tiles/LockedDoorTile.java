package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Graphics.Image;

public class LockedDoorTile extends Tile {
	
	public LockedDoorTile(int x, int y) {
		super(Image.tiles[8][13], x, y);
		this.isSolid = true;
	}
}
