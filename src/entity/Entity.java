package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int screenX,screenY;
	public int speed;
	
	public BufferedImage def,up1,up2,down1,down2,left1,left2,right1,right2;
	public String direction;
	
	int spriteCounter = 0;
	int spriteNum = 1;
	
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public boolean interactObject = false;

}
