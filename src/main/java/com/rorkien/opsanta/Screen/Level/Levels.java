package com.rorkien.opsanta.Screen.Level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.rorkien.opsanta.Graphics.Image;

public class Levels {
	public int width, height;
	public int[] tiles;
	public int[] background;
	public int[] pickup;
	public String name;
	public static Map<Integer, Levels> levels = new HashMap<Integer, Levels>();
	
	public static Levels level1 = load(1, "level1", "Mound Mountain");
	public static Levels level2 = load(2, "level2", "Mound Caverns");
	public static Levels level3 = load(3, "level3", "Mound Mines");
	public static Levels level4 = load(4, "level4", "Mound Outpost");
	public static Levels level5 = load(5, "level5", "Grinchville");
	public static Levels level6 = load(6, "level6", "Grinchville Detention Center");
	
	public static void init() {
	}
	
	public static int getTile(Levels level, int xp, int yp) {
		return level.tiles[(yp * level.width) + xp] & 0xFFFFFF;
	}
	
	public static int getBack(Levels level, int xp, int yp) {
		return level.background[(yp * level.width) + xp] & 0xFFFFFF;
	}
	
	public static int getPickup(Levels level, int xp, int yp) {
		return level.pickup[(yp * level.width) + xp] & 0xFFFFFF;
	}
	
	public static Levels load(int number, String filename, String name) {
		Levels level = new Levels();
		level.name = name;

		try {
			BufferedImage img = ImageIO.read(Image.class.getResource("/levels/" + filename + ".png"));
			level.width = img.getWidth() / 3;
			level.height = img.getHeight();
			
			level.tiles = new int[level.width * level.height];
			level.background = new int[level.width * level.height];
			level.pickup = new int[level.width * level.height];
			
			img.getRGB(level.width * 0, 0, level.width, level.height, level.tiles, 0, level.width);
			img.getRGB(level.width * 1, 0, level.width, level.height, level.background, 0, level.width);
			img.getRGB(level.width * 2, 0, level.width, level.height, level.pickup, 0, level.width);
			
			levels.put(number, level);
			
			return level;
		}
		catch (IOException e) {
			throw new RuntimeException("Loading " + filename + " failed");
		}

	}
}