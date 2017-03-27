package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Graphics.Image;

public class DoorTile extends Tile {
	
	public DoorTile(int x, int y) {
		super(Image.tiles[8][13], x, y);
		this.isSolid = true;
	}
}
