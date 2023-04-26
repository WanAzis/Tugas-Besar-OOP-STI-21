package objek;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Sim;

import java.awt.Rectangle;

import main.GamePanel;

public abstract class Objek {

	public GamePanel gp;
	public BufferedImage image;
	public BufferedImage imageUsed;
	public String name;
	public String action;
	protected int duration;
	public boolean collision = true;
	public int screenX, screenY;
	public int panjang, lebar;
	public Rectangle solidArea;
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;

	//Use Counter
	protected int counter = 0;
	
	//GETTER
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
	public abstract void effect(Sim sim, int duration);
}