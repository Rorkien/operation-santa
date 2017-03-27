package com.rorkien.opsanta.Screen.Entities;

import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.Entities.Particles.StickSplash;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Utils.RNG;


public class Stick extends Entity {
	public float velocity = 4F;
	public int dir = 1;
	
	public Stick(Level level, int dir, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = 12;
		this.height = 12;
		this.hitboxX = 10;
		this.hitboxY = 10;
		this.dir = dir;
		this.type = 1;
	}

	public void tick() {
		if (!this.isExpired) {	
			for (int i = 0; i < level.entities.size(); i++) {
				if (this.type != level.entities.get(i).type) {
					if (this.collides(level.entities.get(i))) {
						level.entities.get(i).kill();
						if (level.entities.get(i) instanceof Snowball) ((Snowball)level.entities.get(i)).explode();
						this.explode();
					}
				}
			}			
			
			this.x += this.velocity * this.dir;
			if (!level.isFree(getXPos(), getYPos())) this.explode();
		}
	}
	
	public void explode() {
		this.isRemoved = true;
		if (getXPos() / 32 > 0 && getXPos() / 32 < level.level.width - 2) for (int j = 0; j < 4; j++) level.entities.add(new StickSplash(level, RNG.getRNG(360), this.x + this.hitboxX, this.y + this.hitboxY));
	}
	
	public void render(int offsetX, int offsetY) {
		if (!this.isExpired) Image.tiles[(this.dir > 0 ? 14 : 13)][2].draw((int)this.x - offsetX, (int)this.y - offsetY);
		//Game.instance.raster.fillOutline(this.getXPos() - offsetX, this.getYPos() - offsetY, this.width, this.height, 1, 0xFF00);
	}

}
