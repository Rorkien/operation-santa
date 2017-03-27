package com.rorkien.opsanta.Screen;

import java.util.Arrays;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Graphics.Renderer;
import com.rorkien.opsanta.Utils.RNG;

public class IntroScreen extends Screen {
	int ticks = 0;
	int rcolor = RNG.getRNG(255);
	int orgcolors[] = {0x0, 0xFF, 0x808080};
	int subcolors[] = {0xFFFFFF, Renderer.getHSL(rcolor, 100, 50), Renderer.getHSL(rcolor, 100, 50)};


	public void tick() {
		if (this.ticks >= 240) {
			Game.instance.screen = new MenuScreen();
		}
		
		this.ticks++;
		subcolors[1] = Renderer.getHSL(rcolor + this.ticks, 100, 50);
		subcolors[2] = Renderer.getHSL(rcolor + this.ticks, 60, 40);
	}
	
	public void render() {
		Arrays.fill(Game.instance.raster.pixels, 0x222222);
		Image.rorkien.subDraw((Game.instance.raster.width / 2) - (Image.rorkien.width / 2), (Game.instance.raster.height / 2) - (Image.rorkien.height / 2), orgcolors, subcolors);
	}
}
