package com.rorkien.opsanta.Screen;

import com.rorkien.opsanta.Game;
import com.rorkien.opsanta.Screen.Background;

public class Screen {
	public static Background background = new Background();
	
	public boolean transition = false;
	public int transitionValue = 0x0;
	public int transitionDirection = 1;
	public int transitionSpeed = 4;
	
	public void tick() {
		background.tick();
		
		if (transition) {
			transitionValue += transitionSpeed * transitionDirection;
			if (transitionDirection < 0) {
				if (transitionValue <= 0) {
					transitionValue = 0;
					transition = false;
				}

			}
			else {
				if (transitionValue >= 0xFF) {
					transitionValue = 0xFF;
					transition = false;
				}
			}
		}
	}
	public void render() {
		background.render();
	}
	
	public void transition() {
		Game.instance.raster.blendTo(0x0, transitionValue);
	}
	
	public void fadeOut() {
		fadeOut(4);
	}
	
	public void fadeIn() {
		fadeIn(4);
	}
	
	public void fadeOut(int speed) {
		if (!transition) {
			transitionSpeed = speed;
			transitionValue = 0xFF;
			transitionDirection = -1;
			transition = true;
		}
	}
	
	public void fadeIn(int speed) {
		if (!transition) {
			transitionSpeed = speed;
			transitionValue = 0x0;
			transitionDirection = 1;
			transition = true;
		}
	}
}
