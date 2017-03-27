package com.rorkien.opsanta.Screen.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextLoader_Levels {
	public String name;
	public int width, height;
	public int[][] tiles;
	
	//public static Levels level1 = load("levels/level1.txt");
	
	public static void init() {
		load("levels/level1.txt");
	}
	
	public static void load(String filename) {
		long start = System.nanoTime();
		
	    BufferedReader br = new BufferedReader(new InputStreamReader(TextLoader_Levels.class.getClassLoader().getResourceAsStream(filename)));	    

	    TextLoader_Levels level = new TextLoader_Levels();  
	    String line, metadata, tiledata = "";
	    
	    try {
	    	metadata = br.readLine();
	    	while ((line = br.readLine()) != null) tiledata = tiledata.concat(line);
			br.close();
			
			//Retrieving metadata
	    	for (int i = 0; i < numToken(metadata,';'); i++) {
    			if (getToken(getToken(metadata,i,';'),0,'=').equals("name")) level.name = getToken(getToken(metadata,i,';'),1,'=');
    			if (getToken(getToken(metadata,i,';'),0,'=').equals("width")) level.width = Integer.valueOf(getToken(getToken(metadata,i,';'),1,'='));
    			if (getToken(getToken(metadata,i,';'),0,'=').equals("height")) level.height = Integer.valueOf(getToken(getToken(metadata,i,';'),1,'='));
	    	}
	    	
	    	//Expanding compressed tiles
	    	for (int i = 0; i < numToken(tiledata,';'); i++) {
	    		String actualToken = getToken(tiledata,i,';');
	    		if (numToken(actualToken,':') > 1) {
	    			int times = Integer.valueOf(getToken(actualToken,0,':'));
	    			int tile = Integer.valueOf(getToken(actualToken,1,':'));
		    		String expanded = "";
		    		for (int j = 0; j < times; j++) expanded += tile + (j + 1 < times ? "," : "");
		    		tiledata = tiledata.replaceAll(actualToken, expanded);
	    		}
	    	}
	    	
	    	//Generating the array
	    	int[][] tiles = new int[level.height][level.width];
	    	
	    	for (int y = 0; y < tiles.length; y++) {
	    		String actualY = getToken(tiledata,y,';');
	    		for (int x = 0; x < tiles[y].length; x++) {
	    			tiles[y][x] = Integer.valueOf(getToken(actualY,x,','));
	    		}
	    	}
	    	
	    	/*for (int y = 0; y < tiles.length; y++) {
	    		for (int x = 0; x < tiles[y].length; x++) {
	    			System.out.print(tiles[y][x]);
	    		}
	    		System.out.println();
	    	}*/

		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Loaded " + filename + " in: " + (System.nanoTime() - start) / 1000 / 1000D + "ms");
	}
	
	public static String getToken(String str, int result, char delimiter) {
		String[] abs = str.split(Character.toString(delimiter));
		if (result >= 0 && result < abs.length) return abs[result];
		else return null;
	}
	
	public static int numToken(String str, char delimiter) {
		return str.split(Character.toString(delimiter)).length;
	}
}
