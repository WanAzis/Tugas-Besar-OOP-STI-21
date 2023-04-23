package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

import main.GamePanel;
import main.KeyHandler;

public class Sim extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public Sim(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX = 8;
		solidAreaDefaultY = 16;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		screenX = 100;
		screenY = 100;
		speed = 3;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(new File("../resources/player/sim_up_1.png"));
			up2 = ImageIO.read(new File("../resources/player/sim_up_2.png"));
			down1 = ImageIO.read(new File("../resources/player/sim_down_1.png"));
			down2 = ImageIO.read(new File("../resources/player/sim_down_2.png"));
			right1 = ImageIO.read(new File("../resources/player/sim_right_1.png"));
			right2 = ImageIO.read(new File("../resources/player/sim_right_2.png"));
			left1 = ImageIO.read(new File("../resources/player/sim_left_1.png"));
			left2 = ImageIO.read(new File("../resources/player/sim_left_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
		
		if(keyH.upPressed || keyH.downPressed || 
				keyH.leftPressed || keyH.rightPressed) {
			if(keyH.upPressed) {
				direction = "up";
//				screenY -= speed;
			}
			else if(keyH.downPressed) {
				direction = "down";
//				screenY += speed;
			}
			else if(keyH.leftPressed) {
				direction = "left";
//				screenX -= speed;
			}
			else if(keyH.rightPressed) {
				direction = "right";
//				screenX += speed;
			}
			
			// CHECK COLLISION
			collisionOn = false;
			int objIndex = gp.cChecker.checkObjek(this, true);
			interactObject(objIndex);
			
			if(!collisionOn) {
				switch(direction) {
				case "up": screenY -= speed; break;
				case "down": screenY += speed; break;
				case "left": screenX -= speed; break;
				case "right": screenX += speed; break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum==1) spriteNum=2;
				else if(spriteNum==2)spriteNum=1;
				spriteCounter=0;
			}
		}
	}
	
	public void interactObject(int i) {
		if(i!=999) {
			gp.obj[i]=null;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum==1) {
				image = up1;
			}
			if(spriteNum==2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum==1) {
				image = down1;
			}
			if(spriteNum==2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum==1) {
				image = left1;
			}
			if(spriteNum==2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum==1) {
				image = right1;
			}
			if(spriteNum==2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
