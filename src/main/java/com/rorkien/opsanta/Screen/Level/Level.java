package com.rorkien.opsanta.Screen.Level;

import java.util.ArrayList;
import java.util.List;

import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Screen.Entities.Entity;
import com.rorkien.opsanta.Screen.Entities.Grinch;
import com.rorkien.opsanta.Screen.Entities.GrinchHelper;
import com.rorkien.opsanta.Screen.Entities.GrinchMachine;
import com.rorkien.opsanta.Screen.Entities.Player;
import com.rorkien.opsanta.Screen.Entities.SantaHelper;
import com.rorkien.opsanta.Screen.Level.Tiles.DoorTile;
import com.rorkien.opsanta.Screen.Level.Tiles.EndTile;
import com.rorkien.opsanta.Screen.Level.Tiles.ExitTile;
import com.rorkien.opsanta.Screen.Level.Tiles.LadderTile;
import com.rorkien.opsanta.Screen.Level.Tiles.LockedDoorTile;
import com.rorkien.opsanta.Screen.Level.Tiles.PickupTile;
import com.rorkien.opsanta.Screen.Level.Tiles.RaisingTile;
import com.rorkien.opsanta.Screen.Level.Tiles.SignTile;
import com.rorkien.opsanta.Screen.Level.Tiles.SolidTile;
import com.rorkien.opsanta.Screen.Level.Tiles.Tile;
import com.rorkien.opsanta.Screen.Level.Tiles.WaterTile;
import com.rorkien.opsanta.Utils.RNG;

public class Level {
	public Player player;
	public Levels level;
	public int score = 0, levelScore = 0, maxScore = 0;
	public int objectives = 0;
	public int ammo = 5;
	public boolean isCompleted = false, isLoading = false;
	
		//1-20 = build tiles
	//1 = snow
	//2 = not-solid snow
	//3 = cave
	//4 = not-solid cave
	//5 = house
	//6 = not-solid house
	//7 = water
	//8 = non-hazardous water
	
	//21 = void
	//22 = edge
	//23 = exit
	//24 = spawn
	//25 = end
	
		//31-x = decos
	//31 = rock
	//32 = snow rock
	//33 = smallrock
	//34 = snow smallrock
	//35 = snow branch
	//36 = branch
	//37 = normal door
	//38 = bed
	//39 = shelf
	//40 = tabletop
	//41 = lamp
	//42 = locked door
	
	//XX80 = board with XX id 
	
	//back
	//21 = void
	//1 = earth
	//2 = bricks
	//3 = brick spidey
	//4 = brick lamp
	//5 = brick window
	
	//6 = earth beam
	
	//pickup and creatures
	//21 = void
	//1 = gift
	//2 = milk
	//3 = cookie
	//4 = snowballs x 5
	//5 = snowballs x 10
	
	//31 = grinch helper
	//32 = santa helper
	//33 = grinch
	//34 = grinchmachine


	public Tile[][] background;
	public Tile[][] tiles;
	public PickupTile[][] pickup;
	
	public List<Entity> entities = new ArrayList<Entity>();	
	public List<Entity> newEntities = new ArrayList<Entity>();
	
	public List<Tile> toplayer = new ArrayList<Tile>();
	
	public void init(int levelNumber) {		
		int x, y;
		level = Levels.levels.get(levelNumber);
		tiles = new Tile[level.height][level.width];
		background = new Tile[level.height][level.width];
		pickup = new PickupTile[level.height][level.width];
		
		toplayer.clear();
		entities.clear();
		this.objectives = 0;
		this.maxScore = 0;
		this.levelScore = 0;
		if (this.ammo < 5) this.ammo = 5;
		if (player == null) player = new Player(this, 3660, 0);

		
		for (int i = 0; i < level.tiles.length; i++) {	
			y = i / level.width;
			x = i % level.width;
			
			//Edges
			if (y == 0 || x == 0 || y == level.height - 1 || x == level.width - 1) {
				tiles[y][x] = new SolidTile(Image.tiles[0][6], x * 32, y * 32);
				tiles[y][x].isEdge = true;
			}
			
			else if (Levels.getTile(level, x, y) == 1 || Levels.getTile(level, x, y) == 2) {
				//Outside
				if (Levels.getTile(level, x - 1, y) > 20 && Levels.getTile(level, x, y - 1) > 20) {
					tiles[y][x] = new SolidTile(Image.tiles[0][7], x * 32, y * 32);
					toplayer.add(new Tile(Image.tiles[RNG.getRNG(0, 2)][13], x * 32, (y - 1) * 32));
				}
				else if (Levels.getTile(level, x + 1, y) > 20 && Levels.getTile(level, x, y - 1) > 20) {
					tiles[y][x] = new SolidTile(Image.tiles[1][7], x * 32, y * 32);
					toplayer.add(new Tile(Image.tiles[RNG.getRNG(0, 2)][13], x * 32, (y - 1) * 32));
				}
				else if (Levels.getTile(level, x - 1, y) > 20 && Levels.getTile(level, x, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[0][8], x * 32, y * 32);
				else if (Levels.getTile(level, x + 1, y) > 20 && Levels.getTile(level, x, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[1][8], x * 32, y * 32);
				
				//Inside
				else if (Levels.getTile(level, x + 1, y) < 20 && Levels.getTile(level, x, y + 1) < 20 && Levels.getTile(level, x + 1, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[2][7], x * 32, y * 32);
				else if (Levels.getTile(level, x - 1, y) < 20 && Levels.getTile(level, x, y + 1) < 20 && Levels.getTile(level, x - 1, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[3][7], x * 32, y * 32);
				else if (Levels.getTile(level, x + 1, y) < 20 && Levels.getTile(level, x, y - 1) < 20 && Levels.getTile(level, x + 1, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[2][8], x * 32, y * 32);
				else if (Levels.getTile(level, x - 1, y) < 20 && Levels.getTile(level, x, y - 1) < 20 && Levels.getTile(level, x - 1, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[3][8], x * 32, y * 32);
				
				//Sides
				else if (Levels.getTile(level, x, y - 1) > 20) {
					tiles[y][x] = new SolidTile(Image.tiles[4 + (x % 2)][7], x * 32, y * 32);
					toplayer.add(new Tile(Image.tiles[RNG.getRNG(0, 2)][13], x * 32, (y - 1) * 32));
				}
				else if (Levels.getTile(level, x, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[4 + (x % 2)][8], x * 32, y * 32);
				else if (Levels.getTile(level, x - 1, y) > 20) tiles[y][x] = new SolidTile(Image.tiles[6 + RNG.getRNG(1)][7], x * 32, y * 32);			
				else if (Levels.getTile(level, x + 1, y) > 20) tiles[y][x] = new SolidTile(Image.tiles[6 + RNG.getRNG(1)][8], x * 32, y * 32);		
				
				//Earth
				else tiles[y][x] = new SolidTile(Image.tiles[(RNG.getRNG(100) > 91 ? RNG.getRNG(2) : 0)][11], x * 32, y * 32);
				
				if (Levels.getTile(level, x, y) == 2) tiles[y][x].isSolid = false;
			}
			else if (Levels.getTile(level, x, y) == 3 || Levels.getTile(level, x, y) == 4) {
				//Outside
				if (Levels.getTile(level, x - 1, y) > 20 && Levels.getTile(level, x, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[0][9], x * 32, y * 32);
				else if (Levels.getTile(level, x + 1, y) > 20 && Levels.getTile(level, x, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[1][9], x * 32, y * 32);
				else if (Levels.getTile(level, x - 1, y) > 20 && Levels.getTile(level, x, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[0][10], x * 32, y * 32);
				else if (Levels.getTile(level, x + 1, y) > 20 && Levels.getTile(level, x, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[1][10], x * 32, y * 32);
				
				//Inside
				else if (Levels.getTile(level, x + 1, y) < 20 && Levels.getTile(level, x, y + 1) < 20 && Levels.getTile(level, x + 1, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[2][9], x * 32, y * 32);
				else if (Levels.getTile(level, x - 1, y) < 20 && Levels.getTile(level, x, y + 1) < 20 && Levels.getTile(level, x - 1, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[3][9], x * 32, y * 32);
				else if (Levels.getTile(level, x + 1, y) < 20 && Levels.getTile(level, x, y - 1) < 20 && Levels.getTile(level, x + 1, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[2][10], x * 32, y * 32);
				else if (Levels.getTile(level, x - 1, y) < 20 && Levels.getTile(level, x, y - 1) < 20 && Levels.getTile(level, x - 1, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[3][10], x * 32, y * 32);
			
				//Floor Junctions
				else if (Levels.getTile(level, x - 1, y) == 1 && Levels.getTile(level, x, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[4][11], x * 32, y * 32);
				else if (Levels.getTile(level, x + 1, y) == 1 && Levels.getTile(level, x, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[5][11], x * 32, y * 32);
				
				//Sides
				else if (Levels.getTile(level, x, y - 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[4][9], x * 32, y * 32);
				else if (Levels.getTile(level, x, y + 1) > 20) tiles[y][x] = new SolidTile(Image.tiles[4 + (x % 2)][10], x * 32, y * 32);
				else if (Levels.getTile(level, x - 1, y) > 20) tiles[y][x] = new SolidTile(Image.tiles[6][9], x * 32, y * 32);			
				else if (Levels.getTile(level, x + 1, y) > 20) tiles[y][x] = new SolidTile(Image.tiles[6][10], x * 32, y * 32);	
				
				//Earth
				else tiles[y][x] = new SolidTile(Image.tiles[(RNG.getRNG(100) > 91 ? RNG.getRNG(2) : 0)][11], x * 32, y * 32);
				
				if (Levels.getTile(level, x, y) == 4) tiles[y][x].isSolid = false;
			}
			else if (Levels.getTile(level, x, y) == 5 || Levels.getTile(level, x, y) == 6) {
				
				//Top/Bottom Wall
				if (Levels.getTile(level, x, y + 1) < 5) tiles[y][x] = new SolidTile(Image.tiles[8][9], x * 32, y * 32);
				else if (Levels.getTile(level, x, y - 1) > 20 && Levels.getTile(level, x, y + 1) < 20) tiles[y][x] = new SolidTile(Image.tiles[10][7], x * 32, y * 32);
				else if (Levels.getTile(level, x, y + 1) > 20 && Levels.getTile(level, x, y - 1) < 20) tiles[y][x] = new SolidTile(Image.tiles[11][7], x * 32, y * 32);
				
				//Wooden Floor
				else if (Levels.getTile(level, x, y - 1) > 20) {
					tiles[y][x] = new SolidTile(Image.tiles[12][7], x * 32, y * 32);
					
					//Floor Borders
					if (Levels.getTile(level, x + 1, y) > 20) {						
						if (Levels.getTile(level, x + 2, y) < 20 && Levels.getTile(level, x + 2, y - 1) > 20) toplayer.add(new Tile(Image.tiles[13][7], (x + 1) * 32, y * 32));
						else toplayer.add(new Tile(Image.tiles[14][7], (x + 1) * 32, y * 32));	
					}
					else if (Levels.getTile(level, x - 1, y) > 20) {
						if (Levels.getTile(level, x + 2, y) < 20 && Levels.getTile(level, x + 2, y - 1) > 20) toplayer.add(new Tile(Image.tiles[13][7], (x - 1) * 32, y * 32));
						else toplayer.add(new Tile(Image.tiles[15][7], (x - 1) * 32, y * 32));	
					}
				}
				
				//Normal Wall
				else tiles[y][x] = new SolidTile(Image.tiles[9][7], x * 32, y * 32);
				
				if (Levels.getTile(level, x, y) == 6) tiles[y][x].isSolid = false;
			}
			else if (Levels.getTile(level, x, y) == 7 || Levels.getTile(level, x, y) == 8) {
				//Water
				
				if (Levels.getTile(level, x, y - 1) > 20) {
					tiles[y][x] = new WaterTile((Levels.getTile(level, x, y) == 7 ? true : false), x * 32, y * 32);
					toplayer.add(tiles[y][x]);
				}
				else tiles[y][x] = new WaterTile((Levels.getTile(level, x, y) == 7 ? true : false), x * 32, y * 32);
				
				if (Levels.getTile(level, x - 1, y) < 6) {
					if (Levels.getTile(level, x, y + 1) < 6) toplayer.add(new Tile(Image.tiles[0][15], x * 32, y * 32)); //Left edge
					else toplayer.add(new Tile(Image.tiles[2][15], x * 32, y * 32 - RNG.getRNG(1)));
				}
				
				if (Levels.getTile(level, x + 1, y) < 6) {
					if (Levels.getTile(level, x, y + 1) < 6) toplayer.add(new Tile(Image.tiles[1][15], x * 32, y * 32)); //Right edge
					else toplayer.add(new Tile(Image.tiles[3][15], x * 32, y * 32 - RNG.getRNG(1)));
				}
				
			}
			
			//Special blocks
			//Spawn
			else if (Levels.getTile(level, x, y) == 23) tiles[y][x] = new ExitTile(x * 32, y * 32);
			else if (Levels.getTile(level, x, y) == 24) player = new Player(this, x * 32, y * 32);
			else if (Levels.getTile(level, x, y) == 25) tiles[y][x] = new EndTile(x * 32, y * 32);
			
			//Rocks
			else if (Levels.getTile(level, x, y) == 31) tiles[y][x] = new SolidTile(Image.tiles[1][12], x * 32, y * 32);	
			else if (Levels.getTile(level, x, y) == 32) tiles[y][x] = new SolidTile(Image.tiles[0][12], x * 32, y * 32);
			else if (Levels.getTile(level, x, y) == 33) tiles[y][x] = new Tile(Image.tiles[3][12], x * 32, y * 32);	
			else if (Levels.getTile(level, x, y) == 34) tiles[y][x] = new Tile(Image.tiles[2][12], x * 32, y * 32);
			
			//Branches
			else if (Levels.getTile(level, x, y) == 35) tiles[y][x] = new SolidTile(Image.tiles[3 + (Levels.getTile(level, x - 1, y) < 20 ? 1 : 0)][13], x * 32, y * 32);
			else if (Levels.getTile(level, x, y) == 36) {
				tiles[y][x] = new SolidTile(Image.tiles[5 + (Levels.getTile(level, x - 1, y) < 20 ? 1 : 0)][13], x * 32, y * 32);
				toplayer.add(new Tile(Image.tiles[5 + (Levels.getTile(level, x - 1, y) < 20 ? 1 : 0)][13], x * 32 + (Levels.getTile(level, x - 1, y) < 20 ? -20 : 20), y * 32));
			}
			
			//Doors
			else if (Levels.getTile(level, x, y) == 37) tiles[y][x] = new DoorTile(x * 32, y * 32);
			
			//Bed
			else if (Levels.getTile(level, x, y) == 38) {
				if (Levels.getTile(level, x + 1, y) == 38) tiles[y][x] = new Tile(Image.tiles[8][11], x * 32, y * 32);
				else tiles[y][x] = new Tile(Image.tiles[9][11], x * 32, y * 32);
				tiles[y][x].isBehind = true;
			}
			
			//Shelves
			else if (Levels.getTile(level, x, y) == 39) {
				tiles[y][x] = new Tile(Image.tiles[8 + RNG.getRNG(3)][10], x * 32, y * 32);
				tiles[y][x].isBehind = true;
			}
	
			//Tabletop
			else if (Levels.getTile(level, x, y) == 40) {
				tiles[y][x] = new Tile(Image.tiles[12 + RNG.getRNG(3)][10], x * 32, y * 32);
				tiles[y][x].isBehind = true;
			}
			
			//Lamps			
			else if (Levels.getTile(level, x, y) == 41) {
				if (Levels.getTile(level, x - 1, y) < 20) tiles[y][x] = new Tile(Image.tiles[11][13], x * 32 - 7, y * 32);
				else tiles[y][x] = new Tile(Image.tiles[12][13], x * 32 + 7, y * 32);
				tiles[y][x].isBehind = true;
			}
			
			//Locked Door
			else if (Levels.getTile(level, x, y) == 42) {
				System.out.println(x + " " + y);
				tiles[y][x] = new LockedDoorTile(x * 32, y * 32);
			}
			
			//Boards
			else if ((Levels.getTile(level, x, y) & 0xFF) == 80) {
				tiles[y][x] = new SignTile(Image.tiles[5 + (Levels.getTile(level, x, y + 1) > 2 ? 1 : 0)][12], (Levels.getTile(level, x, y) & 0xFF00) >> 8, x * 32 - 7, y * 32);
				tiles[y][x].isBehind = true;
			}
			else if (Levels.getTile(level, x, y) == 81) {
				tiles[y][x] = new Tile(Image.tiles[7][12], x * 32 - 7, y * 32);
				tiles[y][x].isBehind = true;
			}
			
			//Interact Tiles (Ladders, One-way Tiles, etc)
			else if (Levels.getTile(level, x, y) == 60) tiles[y][x] = new LadderTile(x * 32, y * 32);		
			
			//Background
			if (Levels.getBack(level, x, y) == 1) {
				
				if (Levels.getBack(level, x - 1, y) > 20 && Levels.getBack(level, x, y - 1) > 20) background[y][x] = new Tile(Image.tiles[0][9], x * 32, y * 32);
				else if (Levels.getBack(level, x + 1, y) > 20 && Levels.getBack(level, x, y - 1) > 20) background[y][x] = new Tile(Image.tiles[1][9], x * 32, y * 32);
				else if (Levels.getBack(level, x - 1, y) > 20 && Levels.getBack(level, x, y + 1) > 20) background[y][x] = new Tile(Image.tiles[0][10], x * 32, y * 32);
				else if (Levels.getBack(level, x + 1, y) > 20 && Levels.getBack(level, x, y + 1) > 20) background[y][x] = new Tile(Image.tiles[1][10], x * 32, y * 32);
				
				else if (Levels.getBack(level, x, y - 1) > 20) background[y][x] = new Tile(Image.tiles[4][9], x * 32, y * 32);
				else if (Levels.getBack(level, x, y + 1) > 20) background[y][x] = new Tile(Image.tiles[4 + (x % 2)][10], x * 32, y * 32);
				else if (Levels.getBack(level, x - 1, y) > 20) background[y][x] = new Tile(Image.tiles[6][9], x * 32, y * 32);			
				else if (Levels.getBack(level, x + 1, y) > 20) background[y][x] = new Tile(Image.tiles[6][10], x * 32, y * 32);	
				
				else background[y][x] = new Tile(Image.tiles[(RNG.getRNG(100) > 91 ? RNG.getRNG(2) : 0)][11], x * 32, y * 32);
			}
			else if (Levels.getBack(level, x, y) == 2) {

				if (Levels.getBack(level, x - 1, y) > 20) {
					//Left Edge
					if (Levels.getTile(level, x + 1, y) < 20 && Levels.getTile(level, x, y) < 20) background[y][x] = new Tile(Image.tiles[12][8], x * 32, y * 32);
					else background[y][x] = new Tile(Image.tiles[9][8], x * 32, y * 32);
				}
				else if (Levels.getBack(level, x + 1, y) > 20) {
					//Right Edge
					if (Levels.getTile(level, x - 1, y) < 20 && Levels.getTile(level, x, y) < 20) background[y][x] = new Tile(Image.tiles[13][8], x * 32, y * 32);
					else background[y][x] = new Tile(Image.tiles[10][8], x * 32, y * 32);			
				}
				else if (Levels.getTile(level, x, y) < 20 && (Levels.getTile(level, x, y - 1) < 20 || Levels.getTile(level, x, y + 1) < 20)) {
					//Both Floor
					if (Levels.getTile(level, x - 1, y) < 20 && Levels.getTile(level, x + 1, y) < 20) background[y][x] = new Tile(Image.tiles[11][8], x * 32, y * 32);
					
					//Only Left Floor
					else if (Levels.getTile(level, x - 1, y) < 20) background[y][x] = new Tile(Image.tiles[15][8], x * 32, y * 32);				
					
					//Only Right Floor
					else if (Levels.getTile(level, x + 1, y) < 20) background[y][x] = new Tile(Image.tiles[14][8], x * 32, y * 32);	
					
					else background[y][x] = new Tile(Image.tiles[8][8], x * 32, y * 32);
				}
				else {
					//if (Levels.getTile(level, x, y - 1) < 20 && RNG.getRNG(100) > 70) background[y][x] = new Tile(Image.tiles[9 + RNG.getRNG(2)][9], x * 32, y * 32);
					//else 
					background[y][x] = new Tile(Image.tiles[8][8], x * 32, y * 32);
				}
			}
			
			//Spidey
			else if (Levels.getBack(level, x, y) == 3) background[y][x] = new Tile(Image.tiles[9][9], x * 32, y * 32);
			
			//Lamp
			else if (Levels.getBack(level, x, y) == 4) background[y][x] = new Tile(Image.tiles[10][9], x * 32, y * 32);
			
			//Window
			else if (Levels.getBack(level, x, y) == 5) background[y][x] = new Tile(Image.tiles[11][9], x * 32, y * 32);			
			
			//Beam
			else if (Levels.getBack(level, x, y) == 6) background[y][x] = new Tile(Image.tiles[3][11], x * 32, y * 32);			
			
			//Pickup
			if (Levels.getPickup(level, x, y) == 1) pickup[y][x] = new PickupTile(PickupTile.GIFT, x * 32, y * 32);
			else if (Levels.getPickup(level, x, y) == 2) pickup[y][x] = new PickupTile(PickupTile.MILK, x * 32, y * 32);
			else if (Levels.getPickup(level, x, y) == 3) pickup[y][x] = new PickupTile(PickupTile.COOKIE, x * 32, y * 32);
			else if (Levels.getPickup(level, x, y) == 4) pickup[y][x] = new PickupTile(PickupTile.SNOWBALLS5, x * 32, y * 32);
			else if (Levels.getPickup(level, x, y) == 5) pickup[y][x] = new PickupTile(PickupTile.SNOWBALLS10, x * 32, y * 32);
			else if (Levels.getPickup(level, x, y) == 6) pickup[y][x] = new PickupTile(PickupTile.BIGSNOWBALL, x * 32, y * 32);
			if (pickup[y][x] != null) this.maxScore += pickup[y][x].worth;
			
			else if (Levels.getPickup(level, x, y) == 31) entities.add(new GrinchHelper(this, x * 32, y * 32));
			else if (Levels.getPickup(level, x, y) == 32) {
				entities.add(new SantaHelper(this, x * 32, y * 32));
				this.objectives++;
			}
			else if (Levels.getPickup(level, x, y) == 33) entities.add(new Grinch(this, x * 32, y * 32));
			else if (Levels.getPickup(level, x, y) == 34) entities.add(new GrinchMachine(this, x * 32, y * 32));	
		}
	}
	
	public Level(int level) {
		init(1);
	}
	
	public boolean isFree(int x, int y) {
		int xp = x / 32;
		int yp = y / 32;
		
		if (tiles[yp][xp] != null && tiles[yp][xp].isSolid) return false;
		else return true;	
	}
	
	public void destroyPickup(int xp, int yp) {
		pickup[yp][xp].sound.play();
		this.score += pickup[yp][xp].worth;
		this.levelScore += pickup[yp][xp].worth;
		this.ammo += pickup[yp][xp].ammo;
		int image = Levels.getPickup(level, xp, yp) + 1;		
		toplayer.add(new RaisingTile(Image.tiles[image][6], xp * 32, yp * 32));
		pickup[yp][xp] = null;
	}
	
	public void tick() {
		player.tick();
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
			if(!entities.get(i).isRemoved) newEntities.add(entities.get(i));
		}
		entities.clear();
	    List<Entity> temp = newEntities;
	    newEntities = entities;
	    entities = temp;
		
		for (int i = 0; i < pickup[0].length * pickup.length; i++) if (pickup[i / pickup[0].length][i % pickup[0].length] != null) pickup[i / pickup[0].length][i % pickup[0].length].tick();
		for (int i = 0; i < toplayer.size(); i++) {
			if (toplayer.get(i).isTickable) toplayer.get(i).tick();
			if (toplayer.get(i).isRemoved) toplayer.remove(i);
		}
	}
	
	public void render(int x, int y) {		
			for (int i = 0; i < background[0].length * background.length; i++) if (background[i / background[0].length][i % background[0].length] != null) background[i / background[0].length][i % background[0].length].render(x, y);
			for (int i = 0; i < tiles[0].length * tiles.length; i++) if (tiles[i / tiles[0].length][i % tiles[0].length] != null && tiles[i / tiles[0].length][i % tiles[0].length].isBehind) tiles[i / tiles[0].length][i % tiles[0].length].render(x, y);
			player.render(x, y);
			for (Entity e : entities) e.render(x, y);
			for (int i = 0; i < pickup[0].length * pickup.length; i++) if (pickup[i / pickup[0].length][i % pickup[0].length] != null) pickup[i / pickup[0].length][i % pickup[0].length].render(x, y);
			for (int i = 0; i < tiles[0].length * tiles.length; i++) if (tiles[i / tiles[0].length][i % tiles[0].length] != null && !tiles[i / tiles[0].length][i % tiles[0].length].isBehind) tiles[i / tiles[0].length][i % tiles[0].length].render(x, y);		
			for (Tile t : toplayer) t.render(x, y);
	}
}
