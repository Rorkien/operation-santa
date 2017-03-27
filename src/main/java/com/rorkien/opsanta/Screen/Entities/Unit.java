package com.rorkien.opsanta.Screen.Entities;

import com.rorkien.opsanta.Sounds;


public class Unit extends Entity {
	public boolean isFalling, isJumping, isClimbing;
	public int currentStep = 0;
	public int dir = 1;	
	
	
	/*public boolean canFall(int offset) {	
		if (this.level.isFree(this.getXPos(), this.getYPos() + this.height + offset) && this.level.isFree(this.getXPos() + this.width - 1, this.getYPos() + this.height + offset)) return true;
		else return false;		
	}*/
	
	public boolean canFall(int offset) {
		return canFall(0, offset);
	}
	
	public boolean canFall(int offsetX, int offsetY) {	
		if (this.level.isFree(this.getXPos() + offsetX, this.getYPos() + this.height + offsetY) && this.level.isFree(this.getXPos() + this.width - 1 + offsetX, this.getYPos() + this.height + offsetY)) return true;
		else return false;		
	}
	
	public boolean canJump() {	
		if (this.level.isFree(this.getXPos(), this.getYPos() - 1) && this.level.isFree(this.getXPos() + this.width - 1, this.getYPos() - 1)) return true;
		else return false;		
	}
	
	public boolean canWalk(int dir) {
		if (dir == -1 && this.level.isFree(this.getXPos() - 1, this.getYPos()) && this.level.isFree(this.getXPos() - 1, this.getYPos() + this.height - 1)) return true;			
		else if (dir == 1 && this.level.isFree(this.getXPos() + this.width, this.getYPos()) && this.level.isFree(this.getXPos() + this.width, this.getYPos() + this.height - 1)) return true;
		else return false;		
	}
	
	public void kill() {
		if (!this.isExpired) {
			onKill();
			this.isExpired = true;
			this.currentStep = 0;
			Sounds.edeath.play();
		}
	}
	
	public void onKill() {
		
	}
	
	public void tick() {
		if (this.life <= 0) this.kill();
	}
	public void render(int offsetX, int offsetY) {
		
	}
}
