package com.rorkien.opsanta.Screen.Entities;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Sounds;
import com.rorkien.opsanta.Graphics.Font;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.VictoryScreen;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Screen.Level.Tiles.DoorTile;
import com.rorkien.opsanta.Screen.Level.Tiles.EndTile;
import com.rorkien.opsanta.Screen.Level.Tiles.ExitTile;
import com.rorkien.opsanta.Screen.Level.Tiles.LadderTile;
import com.rorkien.opsanta.Screen.Level.Tiles.SignTile;
import com.rorkien.opsanta.Screen.Level.Tiles.Tile;

public class Player extends Unit {
	public int[][] animationStep = {
			{4, 5, 6, 7, 6, 5}, //walk left
			{0, 1, 2, 3, 2, 1},  //walk right
			{0, 1, 0, 1, 0, 1} //climb
	};
	
	public float velocity = 2F;
	public float gravity = 0.35F, actualGravity;
	public float jumpForce = 9.0F, actualJumpForce;
	public boolean jumpOnCooldown = false, shootOnCooldown = false;
		
	public Player(Level level, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = 8;
		this.height = 28;
		this.hitboxX = 12;
		this.hitboxY = 4;
		this.type = 0;
	}
	
	public boolean hasPickup(int xp, int yp) {
		int xpp = xp / 32;
		int ypp = yp / 32;
		
		if (level.pickup[ypp][xpp] != null) return true;
		else return false;	
	}
	
	public Tile getTile(int x, int y) {
		if (level.tiles[y / 32][x / 32] != null) return level.tiles[y / 32][x / 32];
		else return null;
	}
	
	public void tick() {		
		for (Entity e : level.entities) {
			if (this.collides(e)) {
				if (e.type != this.type) {
					this.kill();
				}
				else e.kill();
			}
		}
		
		if (this.isExpired) {
			actualJumpForce = 0;
			if (this.isClimbing) {
				this.isClimbing = false;
				dir = 0;
			}
			if (currentStep < 2 && !this.isFalling) if (Game.instance.ticks % 8 == 0) currentStep++;
		}
		else {
			//Hazards, Signs and Exit
			if (level.tiles[getYPos() / 32][getXPos() / 32] instanceof Tile && level.tiles[getYPos() / 32][getXPos() / 32].isHazard) this.kill();
			else if (level.tiles[getYPos() / 32][getXPos() / 32] instanceof SignTile) {
				Image.board.draw(230, 10);
				Font.outlineWrite(((SignTile)level.tiles[getYPos() / 32][getXPos() / 32]).signtext, 235, 10, Image.font_8, 1, 0xFFFFFF, 0x0);
			}
			else if (level.tiles[getYPos() / 32][getXPos() / 32] instanceof ExitTile) {
				if (level.objectives <= 0) {
					level.isCompleted = true;
				}
				else {
					Image.board.draw(230, 10);
					Font.outlineWrite("\nThere's still " + level.objectives + " helper" + (level.objectives > 1 ? 's' : "") + " to save!", 235, 10, Image.font_8, 1, 0xFFFFFF, 0x0);
				}
			}
			
			else if (level.tiles[getYPos() / 32][getXPos() / 32] instanceof EndTile) {
				if (level.objectives <= 0) {
					Game.instance.screen = new VictoryScreen(level.score);
				}
				else {
					Image.board.draw(230, 10);
					Font.outlineWrite("\nThere's still " + level.objectives + " helper" + (level.objectives > 1 ? 's' : "") + " to save!", 235, 10, Image.font_8, 1, 0xFFFFFF, 0x0);
				}
			}
			
			//Shooting
			if (Game.instance.input.action) {
				if (level.ammo > 0) if (!this.shootOnCooldown && !this.isClimbing) this.shootOnCooldown = true;
			}
			else {
				if (this.shootOnCooldown) {
					this.shootOnCooldown = false;
					level.ammo--;
					level.entities.add(new Snowball(level, this.dir == 0 ? -1 : 1, getXPos() - 10, getYPos() - 4));
				}
			}
			
			//Climbing up/down, Jumping
			if (Game.instance.input.up) {
				if (this.isClimbing) {
					this.shootOnCooldown = false;
					this.isFalling = false;
					if (getTile(this.getXPos(), this.getYPos() - (int)this.velocity) != null && getTile(this.getXPos(), this.getYPos() - (int)this.velocity) instanceof LadderTile) {
						dir = 2;
						if (Game.instance.ticks % 8 == 0) currentStep = (currentStep + 1) % animationStep[dir].length;

						jumpOnCooldown = true;
						this.y += -this.velocity;
					}
				}
				else {
					if (!this.isJumping && !this.isFalling && !jumpOnCooldown) {
						this.actualJumpForce = this.jumpForce;
						this.isJumping = true;
						jumpOnCooldown = true;
					}
				}
			}
			else if (Game.instance.input.down) {
				if (this.isClimbing && this.canFall(0)) {
					dir = 2;
					if (Game.instance.ticks % 8 == 0) currentStep = (currentStep + 1) % animationStep[dir].length;

					this.y += this.velocity;
					if (!this.canFall((int)this.actualGravity)) this.y = (getYPos() / 32) * 32;
				}
				else this.isClimbing = false;
			}
			else jumpOnCooldown = false;

			//Moving left/right			
			if (Game.instance.input.left) {				
				if (this.canWalk(-1)) {
					if (this.isClimbing) {
						this.x += -this.velocity;
						this.isClimbing = false;
					}
					else {
						dir = 0;
						if (Game.instance.ticks % 6 == 0) currentStep = (currentStep + 1) % animationStep[dir].length;
					}

					this.x += -this.velocity;
				}
				else currentStep = 0;
			}
			else if (Game.instance.input.right) {				
				if (this.canWalk(1)) {
					if (this.isClimbing) {
						this.x += this.velocity;
						this.isClimbing = false;
					}
					else {
						dir = 1;
						if (Game.instance.ticks % 6 == 0) currentStep = (currentStep + 1) % animationStep[dir].length;		
					}

					this.x += this.velocity;
				}
				else currentStep = 0;
			}
			else if (!this.isClimbing) currentStep = 0;

			//Pickups
			if (hasPickup(this.getXPos(),this.getYPos())) {
				level.destroyPickup(this.getXPos() / 32, this.getYPos() / 32);
			}	

			//Ladders
			if (getTile(this.getXPos(), this.getYPos()) != null && getTile(this.getXPos(), this.getYPos()) instanceof LadderTile) {
				if (Game.instance.input.up) {
					this.x = (this.getXPos() / 32 * 32);
					dir = 2;
					this.isClimbing = true;
					this.isJumping = false;
					this.isFalling = false;
					this.actualGravity = 0;
					this.actualJumpForce = 0;
				}
			}
			else this.isClimbing = false;

			//Door
			if (getTile(this.getXPos() + this.width, this.getYPos()) != null && getTile(this.getXPos() + this.width, this.getYPos()) instanceof DoorTile) {
				int doorXPos = (this.getXPos() + this.width) / 32;
				int doorYPos = this.getYPos() / 32;
				level.tiles[doorYPos][doorXPos] = null;
				level.toplayer.add(new Tile(Image.tiles[10][13], doorXPos * 32 + 8, doorYPos * 32));
				Sounds.door.play();
			}
			else if (getTile(this.getXPos() - 1, this.getYPos()) != null && getTile(this.getXPos() - 1, this.getYPos()) instanceof DoorTile) {
				int doorXPos = (this.getXPos() - 1) / 32;
				int doorYPos = this.getYPos() / 32;
				level.tiles[doorYPos][doorXPos] = null;
				level.toplayer.add(new Tile(Image.tiles[9][13], doorXPos * 32 - 8, doorYPos * 32));
				Sounds.door.play();
			}
		}
		
		//Tick Jump
		if (this.isJumping) {
			if (!this.isExpired) currentStep = 1;

			if (this.actualJumpForce > 0) {
				if (this.canJump()) {				
					this.isFalling = false;
					this.actualJumpForce -= this.gravity;
					this.y -= actualJumpForce;
					if (this.actualGravity > this.actualJumpForce) this.isJumping = false;
				}
				else {
					this.actualJumpForce = 0;
					Sounds.tuck.play();
				}
			}
		}

		//Tick Gravity
		if (this.isFalling) {
			if (!this.isExpired) currentStep = 3;

			if (this.actualGravity < 4) this.actualGravity += this.gravity;	
			if (!this.canFall((int)this.actualGravity)) this.y = (getYPos() / 32) * 32 ;
			else this.y += this.actualGravity;		
		}

		//Gravity Check and Status Reset
		if (this.canFall(0) && !this.isClimbing) {
			this.isFalling = true;
		}
		else {
			if (!this.isClimbing) {
				this.isJumping = false;
				this.isFalling = false;
				this.actualJumpForce = 0;
				this.actualGravity = 0;
			} 
		}
	}
	
	public void render(int offsetX, int offsetY) {
		if (this.isExpired) {
			Image.tiles[currentStep + 2 + (3 * dir)][4].draw((int)this.x - offsetX, (int)this.y - offsetY);
		}
		else if (this.shootOnCooldown) {
			Image.tiles[animationStep[dir][currentStep]][3].draw((int)this.x - offsetX, (int)this.y - offsetY);
		}
		else Image.tiles[animationStep[dir][currentStep]][2 + (this.isClimbing ? 2 : 0)].draw((int)this.x - offsetX, (int)this.y - offsetY);
		
		//Game.instance.raster.fillOutline(this.getXPos() - offsetX, this.getYPos() - offsetY, this.width, this.height, 1, 0xFF00);
	}

}
