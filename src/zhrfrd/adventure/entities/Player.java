package zhrfrd.adventure.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure.main.GamePanel;
import zhrfrd.adventure.main.KeyHandler;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyHandler;
	public final int SCREEN_X, SCREEN_Y;   // Position of the player of the screen
	public int keyCount = 0;
	
	public Player(GamePanel gp, KeyHandler keyHandler) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);    // Player in the middle of the screen
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);   //
		
		solidArea = new Rectangle(8, 16, 32, 32);   // Rectangle solid area of the player
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDefaultValues();
		getPlayerImage();
	}
	
	/*
	 * Set default values of the player when a new instance of Player is created
	 */
	public void setDefaultValues() {
		worldX = gp.TILE_SIZE * 23;   // Player starting position on the map
		worldY = gp.TILE_SIZE * 21;   //
		speed = 4;
		direction = "down";   // Default direction
	}
	
	/*
	 * Get the sprites of the player
	 */
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Update player information
	 */
	public void update() {
		// Get keyboard strokes and update player position
		if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
			if (keyHandler.upPressed)
				direction = "up";
			
			if (keyHandler.downPressed)
				direction = "down";
			
			if (keyHandler.leftPressed)
				direction = "left";
			
			if (keyHandler.rightPressed)
				direction = "right";
			
			collisionOn = false;
			gp.collisionChecker.checkTile(this);   // Check the tile collision
			
			int objIndex = gp.collisionChecker.checkObject(this, true);   // Check if player collided with an object and save the object index
			
			if (objIndex != 999)   // If the player collided with an existing object, pick it up
				pickUpObject(objIndex);
			
			// Move the player only in case there is no collision detected
			if (collisionOn == false) {
				if (direction == "up")
					worldY -= speed;
				
				if (direction == "down")
					worldY += speed;
					
				if (direction == "left")
					worldX -= speed;
					
				if (direction == "right")
					worldX += speed;
			}
			
			spriteCounter ++;   // Increase spriteCounter every 0.01666 seconds
			
			// Swap sprite every 10 FPS
			if (spriteCounter > 9) {
				if (spriteNumber == 1)
					spriteNumber = 2;
				
				else
					spriteNumber = 1;
				
				spriteCounter = 0;
			}
		}
	}
	
	/*
	 * Remove object from the map and add it to the player inventory
	 */
	public void pickUpObject(int index) {
		String objectName = gp.obj[index].name;
		
		switch (objectName) {
			case "Key":
				gp.playSoundEffect(1);
				keyCount ++;
				gp.obj[index] = null;
				gp.ui.showMessage("You got a key!");
				
				break;
				
			case "Door":   // Open doors only when you pick up the key
				if (keyCount > 0) {
					gp.playSoundEffect(3);
					gp.obj[index] = null;
					keyCount --;
					gp.ui.showMessage("You opened the door!");
				}
				
				else
					gp.ui.showMessage("You need a key!");
					
				break;
		
			case "Boots":   // Increase player's speed when picking up the boots
				gp.playSoundEffect(2);
				speed += 1;
				gp.obj[index] = null;
				gp.ui.showMessage("Speed up!");
				
				break;
				
			case "Chest":   // When you get the chest you win the game. The game will stop
				gp.ui.gameFinished = true;
				gp.soundTrack.stop();
				gp.playSoundEffect(4);
				
				break;
		}
	}
	
	/*
	 * Re-draw player each update
	 */
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		if (direction == "up") {
			if (spriteNumber == 1)
				image = up1;
			
			else
				image = up2;
		}
		
		if (direction == "down") {
			if (spriteNumber == 1)
				image = down1;
			
			else
				image = down2;
		}
		
		if (direction == "left") {
			if (spriteNumber == 1)
				image = left1;
			
			else
				image = left2;
		}
		
		if (direction == "right") {
			if (spriteNumber == 1)
				image = right1;
			
			else
				image = right2;
		}
		
		g2.drawImage(image, SCREEN_X, SCREEN_Y, gp.TILE_SIZE, gp.TILE_SIZE, null);
	}
}
