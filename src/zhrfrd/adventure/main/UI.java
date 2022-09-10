package zhrfrd.adventure.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import zhrfrd.adventure.objects.Key;

public class UI {
	GamePanel gp;
	final Font ARIAL_PLAIN_40;   // Objects number
	final Font ARIAL_PLAIN_30;   // Pop-up notification
	final Font ARIAL_BOLD_80;   // Congratulation message
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	public boolean gameFinished = false;
	int notificationCounter = 0;
	double playTime;
	DecimalFormat decimalFormat = new DecimalFormat("#0.00");   // Decimal format with 2 decimal digits
	
	public UI(GamePanel gp) {
		this.gp = gp;
		ARIAL_PLAIN_40 = new Font("Arial", Font.PLAIN, 40);
		ARIAL_PLAIN_30 = new Font("Arial", Font.PLAIN, 30);
		ARIAL_BOLD_80 = new Font("Arial", Font.BOLD, 80);
		Key key = new Key();
		keyImage = key.image; 
	}
	
	/*
	 * Show pop-up notification
	 */
	public void showMessage(String message) {
		this.message = message;
		messageOn = true;
	}
	
	/*
	 * Draw UI elements in the panel (don't instantiate here. Since  draw(Graphics2D g2) will be called 60 times per seconds it will affect the performance of the game)
	 */
	public void draw(Graphics2D g2) {
		// Display a congratulation message when you win the game and display it in the centre of the screen
		if (gameFinished) {
			String text;
			int textLength;
			int x;
			int y;
			
			g2.setFont(ARIAL_PLAIN_40);
			g2.setColor(Color.white);
			text = "You found the treasure!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();   // Get the length of the text on the screen
			x = gp.SCREEN_WIDTH / 2 - textLength / 2;
			y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE * 3);
			g2.drawString(text, x, y);
			
			text = "Your time is: " + decimalFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.SCREEN_WIDTH / 2 - textLength / 2;
			y = gp.SCREEN_HEIGHT / 2 + (gp.TILE_SIZE * 4);
			g2.drawString(text, x, y);
			
			g2.setFont(ARIAL_BOLD_80);
			g2.setColor(Color.yellow);
			text = "Congratulations!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.SCREEN_WIDTH / 2 - textLength / 2;
			y = gp.SCREEN_HEIGHT / 2 + (gp.TILE_SIZE * 2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;   // Stop the thread and so the game
		}
		
		else {
			// Display keys information
			g2.setFont(ARIAL_PLAIN_40);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.TILE_SIZE / 2, gp.TILE_SIZE / 2, gp.TILE_SIZE, gp.TILE_SIZE, null);
			g2.drawString("x " + gp.player.keyCount, 74, 62);
			
			// Timer
			playTime += (double)1 / 60;
			g2.drawString("Time: " + decimalFormat.format(playTime), gp.TILE_SIZE * 11, 65);
			
			// Display pop-up notification
			if (messageOn) {
				g2.setFont(ARIAL_PLAIN_30);
				g2.drawString(message, gp.TILE_SIZE / 2, gp.TILE_SIZE * 5);
				
				notificationCounter ++;
				
				// Display pop-up notification for 2 seconds
				if (notificationCounter > 120) {
					notificationCounter = 0;
					messageOn = false;
				}
			}
		}
	}
}
