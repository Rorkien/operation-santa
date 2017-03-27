package com.rorkien.opsanta.Screen.Entities.Particles;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Screen.Entities.Entity;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Utils.RNG;

public class SnowballSplash extends Entity {
	public int angle;
	public float velocity = 2F;
	public float gravity = 0.2F, actualGravity;
	public int size = RNG.getRNG(2,4);
	
	public SnowballSplash(Level level, int angle, float x, float y) {
		this.level = level;
		this.angle = angle % 360;
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		if (this.actualGravity < 10) this.actualGravity += this.gravity;	
		else this.isRemoved = true;

		this.x += this.velocity * Math.cos(Math.toRadians(this.angle));    
		this.y += (this.velocity * Math.sin(Math.toRadians(this.angle))) + this.actualGravity;
		
		if (this.actualGravity > this.gravity * 5 && !level.isFree((int)this.x, (int)this.y)) this.isRemoved = true;
	}
	
	public void render(int offsetX, int offsetY) {
		Game.instance.raster.fillSquare((int)this.x - offsetX, (int)this.y - offsetY, this.size, this.size, 0xFFFFFF);
	}
}
