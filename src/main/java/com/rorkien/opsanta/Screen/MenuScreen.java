package com.rorkien.opsanta.Screen;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Graphics.Font;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Graphics.Renderer;

public class MenuScreen extends Screen {	
	public MenuScreen() {
		fadeIn();
	}
	
	public void tick() {
		super.tick();
		
		if (!transition) {
			if (transitionDirection > 0) {
				for (Boolean b : Game.instance.input.keys) if (b) fadeOut();
			}
			else {
				if (transitionDirection < 0) Game.instance.screen = new GameScreen();
			}
		}
	}

	public void render() {
		super.render();

		Image.logo.draw((Game.WIDTH / 2) - (Image.logo.width / 2), 30);
		Image.tiles[0][0].draw(130, 80);

		Font.subWrite("Press any key to start!", (Game.WIDTH / 2) - (("Press any key to start!".length() * Image.font_16[0][0].width) / 2) - 2, 320 - 2, Image.font_16, 0xFFFFFF, 0);
		Font.subWrite("Press any key to start!", (Game.WIDTH / 2) - (("Press any key to start!".length() * Image.font_16[0][0].width) / 2) - 2, 320 + 2, Image.font_16, 0xFFFFFF, 0);
		Font.subWrite("Press any key to start!", (Game.WIDTH / 2) - (("Press any key to start!".length() * Image.font_16[0][0].width) / 2) + 2, 320 - 2, Image.font_16, 0xFFFFFF, 0);
		Font.subWrite("Press any key to start!", (Game.WIDTH / 2) - (("Press any key to start!".length() * Image.font_16[0][0].width) / 2) + 2, 320 + 2, Image.font_16, 0xFFFFFF, 0);

		Font.subWrite("Press any key to start!", (Game.WIDTH / 2) - (("Press any key to start!".length() * Image.font_16[0][0].width) / 2), 320, Image.font_16, 0xFFFFFF, Renderer.getHSL(210, 100, (float) (75 + Math.sin(Game.instance.ticks / 5F) * 25F)));
		
		Font.write("@Rorkien - 2012 - Merry Christmas!", 2, Game.HEIGHT - 10, Image.font_8);
		
		transition();
	}
}
