package zhrfrd.adventure.objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Boots extends SuperObject{
	public Boots() {
		name = "Boots";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}