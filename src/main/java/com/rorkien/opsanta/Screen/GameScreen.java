package com.rorkien.opsanta.Screen;

import kuusisto.tinysound.Music;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Sounds;
import com.rorkien.opsanta.Graphics.Font;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.Level.Camera;
import com.rorkien.opsanta.Screen.Level.Level;
import com.rorkien.opsanta.Screen.Level.Levels;
import com.rorkien.opsanta.Utils.RNG;

public class GameScreen extends Screen {
	int ticks = 0;	
	Camera camera = new Camera(Game.WIDTH, Game.HEIGHT);
	Level level;
	public int actualLevel;
	public boolean onTransition = false;
	public int restartScore, restartAmmo;
	public Music soundtrack;
	public int soundtrackTicks;

	public GameScreen() {
		onTransition = true;
		actualLevel = 1;
		level = new Level(actualLevel);
		Screen.background.offsetX = -camera.x / 8;
	}
	
	public void switchMusic() {
		if (soundtrack != null) soundtrack.stop();
		int a = RNG.getRNG(Sounds.soundtrack.length - 1);
		soundtrack = Sounds.soundtrack[a];
		soundtrackTicks = this.ticks + Sounds.sizeInTicks[a];
		soundtrack.play(false, 0.5);
		Game.instance.sounds.musics.add(soundtrack);
	}
	
	public void tick() {		
		if (this.ticks > soundtrackTicks) switchMusic();
		
		super.tick();
		this.ticks++;
		
		if ((level.player.isExpired && level.player.currentStep == 2) && !onTransition) {
			onTransition = true;
			fadeOut(8);	
		}
		
		if (!level.isCompleted) level.tick();
		
		if (level.isCompleted && !onTransition) {
			actualLevel++;
			onTransition = true;
			fadeOut();
		}		
		
		if (onTransition && !transition) {
			for (Boolean b : Game.instance.input.keys) if (b) {
				onTransition = false;
				level.isCompleted = false;
				level.isLoading = true;
				
				if (level.player.isExpired) {
					level.score = restartScore;
					level.ammo = restartAmmo;
				}
				else {
					restartScore = level.score;
					restartAmmo = level.ammo;
				}				
				level.init(actualLevel);
				fadeIn(8);
			}
		}
		if (!transition && level.isLoading) level.isLoading = false;
		
		if (!level.player.isExpired) {			
			camera.x = (int)(level.player.x) - (Game.WIDTH / 2);			
			if (camera.x < 70) camera.x = 70;
			if (camera.x > (level.level.width * 32) - Game.WIDTH - 70) camera.x = (level.level.width * 32) - Game.WIDTH - 70;
			
			if (level.player.y > (Game.HEIGHT / 2) + 70 && level.player.y < (level.level.height * 32) - (Game.HEIGHT / 2) - 70) camera.y = (int)(level.player.y) - (Game.HEIGHT / 2);
			Screen.background.offsetX = -camera.x / 8;
		}
	}
	
	public void render() {		
		super.render();

		level.render(camera.x, camera.y);
		Image.hud.draw(10, 10);
		Font.write("" + level.score, 206 - (Integer.toString(level.score).length() * 16), 21, Image.font_16);
		Font.write("" + level.objectives, 108 - (Integer.toString(level.objectives).length() * 16), 49, Image.font_16);
		Font.write("" + level.ammo, 206 - (Integer.toString(level.ammo).length() * 16), 49, Image.font_16);

		transition();

		if (level.isCompleted || (onTransition && !transition)) {
			String a = "Entering level " + (actualLevel);
			Font.outlineWrite(a, (Game.WIDTH - a.length() * Image.font_16[0][0].width) / 2, 21, Image.font_16, 2, 0xFFFFFF, 0xFF);
			Font.write(Levels.levels.get(actualLevel).name, (Game.WIDTH - Levels.levels.get(actualLevel).name.length() * Image.font_16[0][0].width) / 2, 41, Image.font_16);
			
			if (level.isCompleted) {
				String b = "Level Complete at", c = level.levelScore + "/" + level.maxScore + " (" + (level.levelScore * 100 / level.maxScore) + "%)";
				Font.outlineWrite(b, (Game.WIDTH - b.length() * Image.font_16[0][0].width) / 2, 231, Image.font_16, 2, 0xFFFFFF, 0xFF0000);
				Font.outlineWrite(c, (Game.WIDTH - c.length() * Image.font_16[0][0].width) / 2, 251, Image.font_16, 2, 0xFFFFFF, 0xFF0000);
			}
		}
		if (level.player.isExpired) {
			String b = "You died!";
			Font.outlineWrite(b, (Game.WIDTH - b.length() * Image.font_16[0][0].width) / 2, 241, Image.font_16, 2, 0xFFFFFF, 0xFF0000);
		}
	}
}
