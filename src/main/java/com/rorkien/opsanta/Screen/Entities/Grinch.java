package com.rorkien.opsanta.Screen.Entities;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Utils.RNG;

public class Grinch extends Unit {
	public float gravity = 0.35F, actualGravity;
	public int[][] animationStep = {
			{12, 13, 14, 15, 14, 13},  //walk left
			{8, 9, 10, 11, 10, 9}, //walk right
	};
	
	public float velocity = 1F;
	public int stickCooldown = 60, actualStickCooldown;
	
	public Grinch(Level level, int x, int y) {		
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 28;
		this.hitboxX = 12;
		this.hitboxY = 4;		
		this.type = 1;
	}
	
	public void tick() {
		super.tick();
		
		if (this.isExpired) {
			if (currentStep < 4) if (Game.instance.ticks % 4 == 0) currentStep++;
		}
		else {
			
			if (actualStickCooldown > 0) actualStickCooldown--;
			if (this.yCollides(level.player)) {
				if ((level.player.x > this.x && this.dir == 1) || (level.player.x < this.x && this.dir == 0)) {
					if (actualStickCooldown <= 0) {
						level.entities.add(new Stick(level, this.dir == 0 ? -1 : 1, getXPos() - 10, getYPos() - 4));
						actualStickCooldown = stickCooldown * RNG.getRNG(1, 3);
					}
				}
			}

			if (dir == 0) {
				if (canWalk(-1)) {
					if (Game.instance.ticks % 6 == 0) currentStep = (currentStep + 1) % animationStep[dir].length;
					this.x += -this.velocity;
				}
				else dir = 1;
			}
			else if (dir == 1) {
				if (canWalk(1)) {
					if (Game.instance.ticks % 6 == 0) currentStep = (currentStep + 1) % animationStep[dir].length;
					this.x += this.velocity;
				}
				else dir = 0;
			}
		}
		
		if (this.canFall((dir < 1 ? -this.hitboxX : this.width), 0)) {
			if (dir == 0) dir = 1;
			else if (dir == 1) dir = 0;
		}
	}
	
	public void render(int offsetX, int offsetY) {
		if (this.isExpired) {
			Image.tiles[currentStep + 8][2].draw((int)this.x - offsetX, (int)this.y - offsetY);
		}
		else Image.tiles[animationStep[dir][currentStep]][1].draw((int)this.x - offsetX, (int)this.y - offsetY);
		
		//Game.instance.raster.fillOutline(this.getXPos() - offsetX, this.getYPos() - offsetY, this.width, this.height, 1, 0xFF00);
	}

}
