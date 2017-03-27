package com.rorkien.opsanta.Screen.Level.Tiles;

import kuusisto.tinysound.Sound;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Sounds;
import com.rorkien.opsanta.Graphics.Image;

public class PickupTile extends Tile {
	public int defaultAnimSprite;
	public int animPos = 1;
	public int worth = 0;
	public int ammo = 0;
	public Sound sound;
	
	public static final byte GIFT = 0;
	public static final byte MILK = 1;
	public static final byte COOKIE = 2;
	public static final byte SNOWBALLS5 = 3;
	public static final byte SNOWBALLS10 = 4;
	public static final byte BIGSNOWBALL = 5;
	
	public PickupTile(int i, int x, int y) {
		super(Image.tiles[i * 2][5], x, y);
		if (i == BIGSNOWBALL) this.img = Image.bigsnowball;
		
		if (i == GIFT) {
			this.worth = 100;
			this.sound = Sounds.pickupGift;
		}
		else if (i == MILK) {
			this.worth = 200;
			this.sound = Sounds.pickupMilk;
		}
		else if (i == COOKIE) {
			this.worth = 500;
			this.sound = Sounds.pickupCookie;
		}
		else if (i == SNOWBALLS5) {
			this.ammo = 3;
			this.sound = Sounds.pickupSnowball1;
		}
		else if (i == SNOWBALLS10) {
			this.ammo = 6;	
			this.sound = Sounds.pickupSnowball2;
		}
		else if (i == BIGSNOWBALL) {
			this.ammo = 9001;	
			this.sound = Sounds.pickupSnowball2;
		}

		this.defaultAnimSprite = i * 2;
		this.isTickable = true;
	}
	
	public void tick() {		
		if (Game.instance.ticks % 30 == 0) {
			animPos = ++animPos % 2;
		}
		this.img = Image.tiles[defaultAnimSprite + animPos][5];
		if (this.ammo == 9001) this.img = Image.bigsnowball;
	}
}
