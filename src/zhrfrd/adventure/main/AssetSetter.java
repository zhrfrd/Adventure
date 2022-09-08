package zhrfrd.adventure.main;

import zhrfrd.adventure.objects.Boots;
import zhrfrd.adventure.objects.Chest;
import zhrfrd.adventure.objects.Door;
import zhrfrd.adventure.objects.Key;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	/*
	 * Create and set position of the objects on the map
	 */
	public void setObject() {
		gp.obj[0] = new Key();
		gp.obj[0].worldX = 23 * gp.TILE_SIZE;
		gp.obj[0].worldY = 7 * gp.TILE_SIZE;
		
		gp.obj[1] = new Key();
		gp.obj[1].worldX = 23 * gp.TILE_SIZE;
		gp.obj[1].worldY = 40 * gp.TILE_SIZE;
		
		gp.obj[2] = new Key();
		gp.obj[2].worldX = 37 * gp.TILE_SIZE;
		gp.obj[2].worldY = 7 * gp.TILE_SIZE;
		
		gp.obj[3] = new Door();
		gp.obj[3].worldX = 10 * gp.TILE_SIZE;
		gp.obj[3].worldY = 11 * gp.TILE_SIZE;
		
		gp.obj[4] = new Door();
		gp.obj[4].worldX = 8 * gp.TILE_SIZE;
		gp.obj[4].worldY = 28 * gp.TILE_SIZE;
		
		gp.obj[5] = new Door();
		gp.obj[5].worldX = 12 * gp.TILE_SIZE;
		gp.obj[5].worldY = 22 * gp.TILE_SIZE;
		
		gp.obj[6] = new Chest();
		gp.obj[6].worldX = 10 * gp.TILE_SIZE;
		gp.obj[6].worldY = 7 * gp.TILE_SIZE;
		
		gp.obj[7] = new Boots();
		gp.obj[7].worldX = 37 * gp.TILE_SIZE;
		gp.obj[7].worldY = 42 * gp.TILE_SIZE;
	}
}
