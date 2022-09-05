package zhrfrd.adventure.main;

import zhrfrd.adventure.entities.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	/*
	 * Check tile for collision detection and pass the entity that collide with the tile
	 */
	public void checkTile(Entity entity) {
		// Calculate the position in the world of the 4 sides of the entity solid area (left, right, top, bottom)
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		// Calculate the column and row position of the solid area of the entity
		int entityLeftCol = entityLeftWorldX / gp.TILE_SIZE;
		int entityRightCol = entityRightWorldX / gp.TILE_SIZE;
		int entityTopRow = entityTopWorldY / gp.TILE_SIZE;
		int entityBottomRow = entityBottomWorldY / gp.TILE_SIZE;
		// You need to check only two tiles for each direction the entity goes
		int tileNum1, tileNum2;
		
		if (entity.direction == "up") {
			entityTopRow = (entityTopWorldY - entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "down") {
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "left") {
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "right") {
			entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
	}
}
