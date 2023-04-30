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
import objek.barang.Barang;
import objek.barang.KasurSingle;
import objek.barang.Sajadah;
import objek.barang.Toilet;
import objek.makanan.Makanan;
import objek.makanan.Masakan;
import objek.makanan.Nasi;
import objek.makanan.NasiAyam;
import tile.*;

public class Sim extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;

	//ATRIBUT
	private String namaLengkap;
	private String pekerjaan;
	private int uang;
	public ArrayList<Objek> inventory = new ArrayList<>();
	public final int maxInventorySize = 32;
	private int kekenyangan;
	private int mood;
	private int kesehatan;
	private String status;
	// public Rumah haveRumah;
	public Rumah curRumah;
	public Ruangan curRuangan;
	private Map<String,Integer> listPekerjaan;
	
	public int counter = 0;
	private int durationKerja;
	public boolean useObject;
	public int interactObjectIdx;
	public Makanan makanan;
	public Barang selectBarang;
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
		// setItems();
	}
	
	public void setDefaultValues() {
		screenX = gp.startRoomX + gp.tileSize*2 + gp.tileSize/2;
		screenY = gp.startRoomY + gp.tileSize*2 + gp.tileSize/2;
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
		inventory.add(new Sajadah(gp));
		inventory.add(new Nasi(gp));
		inventory.add(new Nasi(gp));
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
	public void plusUang(int plusUang){
		uang+=plusUang;
	}
	public void setStatus(String status){this.status=status;}
	public void setDurationKerja(int duration){durationKerja=duration;}

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

		//CHECK GAME OVER
		if(kekenyangan <= 0 || mood <= 0 || kesehatan <= 0){
			gp.ui.setNotifMessage("Salah satu dari kesejahteraan SIM\nhabis sehingga SIM mati");
			//MASUK STATE GAME OVER
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
	
	public void kerja(){
		counter++;
		if(counter>=durationKerja){
			int effectCounter = durationKerja/1800;
			plusKekenyangan(-10*effectCounter);
			plusMood(-10*effectCounter);
			int gaji = listPekerjaan.get(pekerjaan);
			if(durationKerja==60*60*4){
				plusUang(gaji);
			} else if(durationKerja==60*60*8){
				plusUang(2*gaji);
			}
			counter=0;
			gp.ui.setNotifMessage("Anda telah pulang bekerja");
			gp.gameState=gp.notifState;
			status="IDLE";
			getPlayerImage();
		}

	}
	public void selectItem(){
		int itemIdx = gp.ui.getItemIndexOnSlot();

		if(itemIdx < inventory.size()){
			Objek selectedItem = inventory.get(itemIdx);

			if(selectedItem instanceof Makanan){
				setNullImage();
				if(findMejaKursiIdx()!=999){
					curRuangan.obj[findMejaKursiIdx()].used();
				}
				gp.gameState = gp.useMakananState;
				makanan = (Makanan) selectedItem;
				inventory.remove(itemIdx);
			}
			else if(selectedItem instanceof Barang){
				gp.gameState = gp.placeObjectState;
				selectBarang = (Barang) selectedItem;
				curRuangan.addBarang(selectBarang);
				inventory.remove(itemIdx);
			}
		}
	}
	public int findMejaKursiIdx(){
		for(int i = 0; i<curRuangan.obj.length; i++){
			if(curRuangan.obj[i].getName()=="Meja kursi"){
				return i;
			}
		} return 999;
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

    public boolean checkAvailableInventory(String masakan) {
		switch(masakan){
			case "Nasi Ayam" : return checkNasiAyam();
			default : return false;
		}
    }
	private boolean checkNasiAyam(){
		boolean beAble = true;

		Masakan nasiAyam = new NasiAyam(gp);

		int i = 0;

		while(i<nasiAyam.listBahan.length && beAble){
			if(!inventory.contains(nasiAyam.listBahan[i])){
				beAble = false;
			} i++;
		}

		return beAble;
	}
}
