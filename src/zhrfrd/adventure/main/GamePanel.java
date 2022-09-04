package zhrfrd.adventure.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import zhrfrd.adventure.entities.Player;

public class GamePanel extends JPanel implements Runnable {
	final int ORIGINAL_TILE_SIZE = 16;   // 16x16 pixels tile
	final int SCALE = 3;   // Re-scale tiles 
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;   // 48*48 pixels tile
	final int MAX_SCREEN_COL = 16;   // Max tiles to display on the screen
	final int MAX_SCREEN_ROW = 12;   // 
	final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;    // 768 pixels
	final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;   // 576 pixels
	final int FPS = 60;
	Thread gameThread;
	KeyHandler keyHandler = new KeyHandler();
	Player player = new Player(this, keyHandler);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	
	/*
	 * Start the game thread
	 */
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/*
	 * Update information such as character position
	 */
	public void update() {
		player.update();
	}
	
	/*
	 * To paint on a subclass of a JPanel you must override paintComponent()
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);   // Must be done for the JPanel painting takes place
		
		Graphics2D g2 = (Graphics2D)g;	
		player.draw(g2);
		g2.dispose();   // Dispose g2 in order to save resources
	}

	@Override
	public void run () {
		double drawInterval = 1000000000 / FPS;   // Draw every 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		// Game loop
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			// Every 0.01666 seconds (60 FPS)
			if (delta >= 1) {
				update();   // Update information such as character position
				repaint();   // Call paintComponent() to draw the screen with the updated information
				
				delta --;
			}
		}
	}
}
