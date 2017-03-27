package com.rorkien.opsanta.Screen.Entities;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Sounds;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.Entities.Particles.MachineDebris;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Screen.Level.Tiles.Tile;
import com.rorkien.opsanta.Utils.RNG;

public class GrinchMachine extends Unit {
	public float gravity = 0.35F, actualGravity;
	public int[][] animationStep = {
			{8, 10},  //walk left
			{12, 14}, //walk right
	};
	
	public float velocity = 1.7F;
	
	public GrinchMachine(Level level, int x, int y) {		
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = 63;
		this.height = 63;
		this.hitboxX = 1;
		this.hitboxY = 1;		
		this.type = 1;
		this.life = 50;
	}
	
	public void onKill() {
		for (int j = 0; j < 80; j++) level.entities.add(new MachineDebris(level, RNG.getRNG(360), this.x + RNG.getRNG(this.hitboxX, this.hitboxX + this.width), this.y + RNG.getRNG(this.hitboxY, this.hitboxY + this.height)));
		int doorXPos = 49, doorYPos = 47;
		level.tiles[doorYPos][doorXPos] = null;
		level.toplayer.add(new Tile(Image.tiles[10][13], doorXPos * 32 + 8, doorYPos * 32));
		Sounds.door.play();
	}
	
	public void tick() {
		super.tick();
		
		if (this.isExpired) {
			if (currentStep < 4) if (Game.instance.ticks % 4 == 0) currentStep++;
		}
		else {			
			if (dir == 0) {
				if (canWalk(-1)) {
					if (Game.instance.ticks % 6 == 0) {
						currentStep = (currentStep + 1) % animationStep[dir].length;
						if (Math.abs(level.player.x - this.x) < Game.WIDTH / 1.3) Sounds.truck[RNG.getRNG(2)].play((-Math.abs(level.player.x - this.x)) / 600F + 1);
					}
					this.x += -this.velocity;
				}
				else dir = 1;
			}
			else if (dir == 1) {
				if (canWalk(1)) {
					if (Game.instance.ticks % 6 == 0) {
						currentStep = (currentStep + 1) % animationStep[dir].length;
						if (Math.abs(level.player.x - this.x) < Game.WIDTH / 1.3) Sounds.truck[RNG.getRNG(2)].play((-Math.abs(level.player.x - this.x)) / 600F + 1);
					}
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
		if (!this.isExpired) {
			Image.tiles[animationStep[dir][currentStep]][14].draw((int)this.x - offsetX, (int)this.y - offsetY);
			Image.tiles[animationStep[dir][currentStep] + 1][14].draw((int)this.x + 32 - offsetX, (int)this.y - offsetY);
			
			Image.tiles[animationStep[dir][currentStep]][15].draw((int)this.x - offsetX, (int)this.y + 32 - offsetY);
			Image.tiles[animationStep[dir][currentStep] + 1][15].draw((int)this.x + 32 - offsetX, (int)this.y + 32 - offsetY);
		}

		
		//Game.instance.raster.fillOutline(this.getXPos() - offsetX, this.getYPos() - offsetY, this.width, this.height, 1, 0xFF00);
	}

}
