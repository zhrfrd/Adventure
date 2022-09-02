package zhrfrd.adventure.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	final int originalTileSize = 16;   // 16x16 pixels tile
	final int scale = 3;   // Rescale tiles 
	final int tileSize = originalTileSize * scale;   // 48*48 pixels tile
	final int maxScreenCol = 16;   // Max tiles to display on the screen
	final int maxScreenRow = 12;   // 
	final int screenWidth = tileSize * maxScreenCol;    // 768 pixels
	final int screenHeight = tileSize * maxScreenRow;   // 576 pixels
	Thread gameThread;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
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
		
	}
	
	/*
	 * To paint on a subclass of a JPanel you must override paintComponent()
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);   // Must be done for the JPanel painting takes place
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(100, 100, tileSize, tileSize);
		g2.dispose();   // Dispose g2 in order to save resources
	}

	@Override
	public void run () {
		while(gameThread != null) {
			update();   // Update information such as character position
			repaint();   // Call paintComponent() to draw the screen with the updated information
		}
	}
}
