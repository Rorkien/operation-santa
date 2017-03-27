package com.rorkien.opsanta.Screen;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Graphics.Font;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Graphics.Renderer;

public class VictoryScreen extends Screen {
	public int score;
	
	public VictoryScreen(int score) {
		Game.instance.sounds.stopAllMusic();
		this.score = score;
		fadeIn();
	}
	
	public void tick() {
		super.tick();
		
		if (!transition) {
			if (transitionDirection > 0) {
				for (Boolean b : Game.instance.input.keys) if (b) fadeOut();
			}
			else {
				if (transitionDirection < 0) Game.instance.screen = new MenuScreen();
			}
		}
	}

	public void render() {
		super.render();

		Image.logo.draw((Game.WIDTH / 2) - (Image.logo.width / 2), 30);
		
		String a = "Congratulations! You beat the game!", b = "Final score: " + this.score + " points";
		int color = Renderer.getHSL(40, 100, (float) (75 + Math.sin(Game.instance.ticks / 5F) * 25F));

		Font.outlineWrite(a, (Game.WIDTH / 2) - ((a.length() * Image.font_16[0][0].width) / 2), 320, Image.font_16, 2, color, 0x0);
		Font.outlineWrite(b, (Game.WIDTH / 2) - ((b.length() * Image.font_16[0][0].width) / 2), 340, Image.font_16, 2, color, 0x0);
		
		transition();
	}
}
