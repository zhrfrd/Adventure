package zhrfrd.adventure.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import zhrfrd.adventure.main.GamePanel;

public class SuperObject {
	public BufferedImage image;
	public String name;
	public boolean solid = false;
	public int worldX, worldY;
	
	/*
	 * Draw the objects on the screen
	 */
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;   // Object position on the screen
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;   //
		
		// Render only the objects visible on the screen plus one extra tile in order to not show black edges when moving
		if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
				worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
				worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
				worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) 
			g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
		
	}
}
