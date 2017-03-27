package com.rorkien.opsanta.Screen.Entities;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Sounds;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.Entities.Particles.SnowballSplash;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Utils.RNG;


public class Snowball extends Entity {
	public float velocity = 5F;
	public int dir = 1;
	public int life = -20;
	
	public Snowball(Level level, int dir, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = 12;
		this.height = 12;
		this.hitboxX = 10;
		this.hitboxY = 10;
		this.dir = dir;
		this.type = 0;
	}

	public void tick() {
		this.life++;
		if (!this.isExpired) {	
			for (int i = 0; i < level.entities.size(); i++) {
				if (this.type != level.entities.get(i).type) {
					if (this.collides(level.entities.get(i))) {
						level.entities.get(i).life--;
						if (level.entities.get(i) instanceof Stick) ((Stick)level.entities.get(i)).explode();
						this.explode();
					}
				}
			}			
			
			this.x += this.velocity * this.dir;
			if (this.life > 20 || this.life < 0) this.y += this.life / 100F;
			if (!level.isFree(getXPos(), getYPos())) this.explode();
		}
	}
	
	public void explode() {
		if (Math.abs(level.player.x - this.x) < Game.WIDTH / 1.5) Sounds.hit.play();
		this.isRemoved = true;
		if (getXPos() / 32 > 0 && getXPos() / 32 < level.level.width - 2) for (int j = 0; j < 10; j++) level.entities.add(new SnowballSplash(level, RNG.getRNG(360), this.x + this.hitboxX, this.y + this.hitboxY));
	}
	
	public void render(int offsetX, int offsetY) {
		if (!this.isExpired) Image.tiles[6][5].draw((int)this.x - offsetX, (int)this.y - offsetY);
		//Game.instance.raster.fillOutline(this.getXPos() - offsetX, this.getYPos() - offsetY, this.width, this.height, 1, 0xFF00);
	}

}
