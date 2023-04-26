package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import java.io.File;

import main.GamePanel;
import main.KeyHandler;

public class Sim extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;

	//ATRIBUT
	String namaLengkap;
	String pekerjaan;
	int uang;
	//inventory
	int kekenyangan;
	int mood;
	int kesehatan;
	String status;
	//rumah
	//ruangan
	//listStore
	Map<String,Integer> listPekerjaan;
	
	public boolean useObject;
	public int interactObjectIdx;
	
	public Sim(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX = 8;
		solidAreaDefaultY = 16;

		listPekerjaan = new HashMap<String,Integer>();
		listPekerjaan.put("Badut Sulap",15);
		listPekerjaan.put("Koki",30);
		listPekerjaan.put("Polisi",35);
		listPekerjaan.put("Programmer",45);
		listPekerjaan.put("Dokter",50);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		screenX = 100;
		screenY = 100;
		speed = 2;
		direction = "down";
		kekenyangan = 80;
		mood = 80;
		kesehatan = 80;
		uang = 100;

		//RANDOM PEKERJAAN
		Random rand = new Random();
		int n = rand.nextInt(5);
		String[] keys = listPekerjaan.keySet().toArray(new String[0]);
		pekerjaan = keys[n];
	}
	
	public void getPlayerImage() {
		try {
			def = ImageIO.read(new File("../resources/player/sim_down_default.png"));
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
			}
			else if(keyH.downPressed) {
				direction = "down";
			}
			else if(keyH.leftPressed) {
				direction = "left";
			}
			else if(keyH.rightPressed) {
				direction = "right";
			}
			
			// CHECK COLLISION
			collisionOn = false;
			interactObjectIdx = gp.cChecker.checkObjek(this, true);
			gp.cChecker.checkScreen(this);
			interactObject(interactObjectIdx);
			
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
			interactObject=true;
		} 
		else {
			interactObject=false;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		if(!useObject){
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
}
