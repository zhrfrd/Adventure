package zhrfrd.adventure.tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import zhrfrd.adventure.main.GamePanel;
import zhrfrd.adventure.main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	/*
	 * Get the tiles image and relative information
	 */
	public void getTileImage() {
		setup(0, "grass", false);
		setup(1, "wall", true);
		setup(2, "water", true);
		setup(3, "earth", false);
		setup(4, "tree", true);
		setup(5, "sand", false);
	}
	
	/*
	 * Handle instantiation, import of images, scaling and collision
	 */
	public void setup(int index, String imageName, boolean solid) {
		UtilityTool utilityTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = utilityTool.scaleImage(tile[index].image, gp.TILE_SIZE, gp.TILE_SIZE);   // Scale the image by using the UtilityTool class created
			tile[index].solid = solid;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Read text file and load the map from 
	 */
	public void loadMap(String filePath) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(filePath);   // Get text file containing map data
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));   // Read from inpputStream
			
			int col = 0;
			int row = 0;
			
			while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
				String line = bufferedReader.readLine();
				
				while (col < gp.MAX_WORLD_COL) {
					String number[] = line.split(" ");   // Read each number in a line excluding spaces and save it inside the array
					int num = Integer.parseInt(number[col]);
					mapTileNum[col][row] = num;
					
					col ++;
				}
				
				if (col == gp.MAX_WORLD_COL) {
					col = 0;
					row ++;
				}
			}
			
			bufferedReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Render the tiles of the map on the gamePanel from the map data retrieved from the text file
	 */
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {
			int tileNum = mapTileNum[worldCol][worldRow];
			int worldX = worldCol * gp.TILE_SIZE;   // Tiles position of the world
			int worldY = worldRow * gp.TILE_SIZE;   //
			int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;   // Tiles position on the screen
			int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;   //
			
			// Render only the tiles visible on the screen plus one extra tile in order to not show black edges when moving
			if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
					worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
					worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
					worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) 
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			
			worldCol ++;
			
			if (worldCol == gp.MAX_WORLD_COL) {
				worldCol = 0;
				worldRow ++;
			}
		}
	}
}
