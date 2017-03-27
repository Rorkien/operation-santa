package com.rorkien.opsanta.Screen.Entities;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Utils.RNG;

public class SantaHelper extends Unit {
	public float gravity = 0.35F, actualGravity;
	public int[][] animationStep = {
			{4, 5, 6, 7, 6, 5},  //walk left
			{0, 1, 2, 3, 2, 1}, //walk right
	};
	
	public float velocity = 1F;
	public int color = RNG.getRNG(1);
	
	public SantaHelper(Level level, int x, int y) {		
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = 8;
		this.height = 22;
		this.hitboxX = 12;
		this.hitboxY = 10;		
		this.type = 0;
	}
	
	public void tick() {
		if (this.isExpired) {
			if (currentStep < 4) {
				if (Game.instance.ticks % 4 == 0) currentStep++;
			}
			else this.isRemoved = true;;
		}
		else {
			
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
	
	public void kill() {
		super.kill();
		level.objectives--;
	}
	
	public void render(int offsetX, int offsetY) {
		if (this.isExpired) {
			Image.tiles[currentStep + 8][3].draw((int)this.x - offsetX, (int)this.y - offsetY);
		}
		else Image.tiles[animationStep[dir][currentStep]][this.color].draw((int)this.x - offsetX, (int)this.y - offsetY);
	}

}
