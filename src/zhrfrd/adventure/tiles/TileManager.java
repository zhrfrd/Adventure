package zhrfrd.adventure.tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import zhrfrd.adventure.main.GamePanel;

public class TileManager {
	GamePanel gamePanel;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		tile = new Tile[10];
		mapTileNum = new int[gamePanel.MAX_SCREEN_COL][gamePanel.MAX_SCREEN_ROW];
		
		getTileImage();
		loadMap("/maps/map1.txt");
	}
	
	/*
	 * Get the tiles images
	 */
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
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
			
			while (col < gamePanel.MAX_SCREEN_COL && row < gamePanel.MAX_SCREEN_ROW) {
				String line = bufferedReader.readLine();
				
				while (col < gamePanel.MAX_SCREEN_COL) {
					String number[] = line.split(" ");   // Read each number in a line excluding spaces and save it inside the array
					int num = Integer.parseInt(number[col]);
					mapTileNum[col][row] = num;
					
					col ++;
				}
				
				if (col == gamePanel.MAX_SCREEN_COL) {
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
	 * Draw the tiles on the gamePanel from the map data
	 */
	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < gamePanel.MAX_SCREEN_COL && row < gamePanel.MAX_SCREEN_ROW) {
			int tileNumber = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNumber].image, x, y, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
			
			col ++;
			x += gamePanel.TILE_SIZE;
			
			if (col == gamePanel.MAX_SCREEN_COL) {
				col = 0;
				x = 0;
				row ++;
				y += gamePanel.TILE_SIZE;
			}
		}
	}
}
