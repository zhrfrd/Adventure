package zhrfrd.adventure.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure.main.GamePanel;
import zhrfrd.adventure.main.KeyHandler;

public class Player extends Entity {
	GamePanel gamePanel;
	KeyHandler keyHandler;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	/*
	 * Set default values of the player when a new instance of Player is created
	 */
	public void setDefaultValues() {
		x = 100;
		y = 100;
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
			if (keyHandler.upPressed) {
				direction = "up";
				y -= speed;
			}
			
			if (keyHandler.downPressed) {
				direction = "down";
				y += speed;
			}
			
			if (keyHandler.leftPressed) {
				direction = "left";
				x -= speed;
			}
			
			if (keyHandler.rightPressed) {
				direction = "right";
				x += speed;
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
	 * Re-sraw player each update
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
		
		g2.drawImage(image, x, y, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
	}
}
