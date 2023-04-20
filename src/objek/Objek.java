package objek;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import main.GamePanel;

public class Objek {

	public BufferedImage image;
	public String name;
	public boolean collision = true;
	public int screenX, screenY;
	public int panjang, lebar;
	public Rectangle solidArea;
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		g2.drawImage(image, screenX, screenY, gp.tileSize*lebar, gp.tileSize*panjang, null);
	}
}
