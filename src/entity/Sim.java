package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import java.io.File;

import main.GamePanel;
import main.KeyHandler;
import objek.*;

public class Sim extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;

	//ATRIBUT
	private String namaLengkap;
	private String pekerjaan;
	private int uang;
	public ArrayList<Objek> inventory = new ArrayList<>();
	private final int inventorySize = 32;
	private int kekenyangan;
	private int mood;
	private int kesehatan;
	private String status;
	//rumah
	//ruangan
	//listStore
	private Map<String,Integer> listPekerjaan;
	
	public boolean useObject;
	public int interactObjectIdx;
	private final int maxKekenyangan = 100;
	private final int maxMood = 100;
	private final int maxKesehatan = 100;
	
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
		setItems();
	}
	
	public void setDefaultValues() {
		screenX = 100;
		screenY = 100;
		speed = 2;
		direction = "down";

		//ATRIBUT SIM
		namaLengkap = "Sim-A";
		kekenyangan = 80;
		mood = 80;
		kesehatan = 80;
		uang = 100;
		status = "IDLE";

		//RANDOM PEKERJAAN
		Random rand = new Random();
		int n = rand.nextInt(5);
		String[] keys = listPekerjaan.keySet().toArray(new String[0]);
		pekerjaan = keys[n];
	}

	public void setItems(){
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
	}
	
	//GETTER
	public String getNamaLengkap(){return namaLengkap;}
	public String getPekerjaan(){return pekerjaan;}
	public int getUang(){return uang;}
	public int getKekenyangan(){return kekenyangan;}
	public int getMood(){return mood;}
	public int getKesehatan(){return kesehatan;}
	public String getStatus(){return status;}

	//SETTER
	public void setUang(int uang){this.uang = uang;}
	public void setKekenyangan(int kekenyangan){this.kekenyangan = kekenyangan;}
	public void plusKekenyangan(int plusKekenyangan){
		kekenyangan += plusKekenyangan;
		if(kekenyangan>maxKekenyangan){
			kekenyangan=maxKekenyangan;
		}
	}
	public void setMood(int mood){this.mood = mood;}
	public void plusMood(int plusMood){
		mood += plusMood;
		if(mood > maxMood){
			mood = maxMood;
		}
	}
	public void setKesehatan(int kesehatan){this.kesehatan = kesehatan;}
	public void plusKesehatan(int plusKesehatan){
		kesehatan+=plusKesehatan;
		if(kesehatan>maxKesehatan){
			kesehatan=maxKesehatan;
		}
	}
	public void setStatus(String status){this.status=status;}

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
	public void setNullImage(){
		up1 = null;
		up2 = null;
		down1 = null;
		down2 = null;
		right1 = null;
		right2 = null;
		left1 = null;
		left2 = null;
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
