package objek.barang;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Sim;

import java.awt.Rectangle;

import main.GamePanel;
import objek.Objek;

public class Barang extends Objek{

	public GamePanel gp;
	public BufferedImage imageUsed;
	public BufferedImage down, downUsed, right, rightUsed, left, leftUsed, up, upUsed;
	public String direction;
	public String action;
	protected int duration;
	public boolean collision = true;
	public int screenX, screenY;
	public int panjang, lebar;
	public Rectangle solidArea;
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	//GETTER
	public String getName(){return name;}
	public int getDuration(){return duration;}
	public BufferedImage getImage(){return image;}
	public BufferedImage getImageUsed(){return imageUsed;}
	public void draw(Graphics2D g2, GamePanel gp) {
		g2.drawImage(image, screenX, screenY, gp.tileSize*lebar, gp.tileSize*panjang, null);
	}

	//SETTER
	public void setDuration(int duration){this.duration=duration;}

	public void used(){}
	public void unUsed(){}
	public void effect(Sim sim, int duration){}
}
