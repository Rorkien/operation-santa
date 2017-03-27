package com.rorkien.opsanta.Screen.Level.Tiles;

import com.rorkien.opsanta.Graphics.Image;

public class SignTile extends Tile {
	public static String text[] = {
			//lvl1
			"Press arrow keys to move and jump.",
			"Press the A key to ready a snowball,\nand release it to launch it.",
			"Rescue all the Santa Helpers to be able\nto exit the level.",
			"Keep your eyes open for secret walls!",
			
			//lvl2
			"Caution!",
			"Watch your head!",
			"To climb stairs, stay in front of one and\npress UP. To stop climbing, press LEFT or RIGHT.",
			
			//lvl4
			"Leap of faith?",
			"Beware with flying sticks!\nThey can also be destroyed with snowballs.",
			
			//lvl5
			"Welcome to Grinchville.\n-No santas allowed\n-No snowballs allowed",
			"'Grinchville Detention Center'\nAuthorized personnel only",
			
			//lvl6
			"Secret weapon. Keep it a secret.\nPS: This is one huge snowball!",
			"Beware: Mad dozer."
			
	};
	public String signtext;
	
	public SignTile(Image img, int id, int x, int y) {
		super(img, x, y);
		this.isBehind = true;
		this.signtext = "\n" + text[id];
	}
}
