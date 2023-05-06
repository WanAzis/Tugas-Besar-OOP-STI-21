package entity;

import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import java.io.File;

import main.GamePanel;
import main.KeyHandler;
import objek.*;
import objek.barang.Barang;
import objek.barang.Jam;
import objek.barang.KasurKing;
import objek.barang.KasurQueen;
import objek.barang.KasurSingle;
import objek.barang.Kompor;
import objek.barang.KomporGas;
import objek.barang.KomporListrik;
import objek.barang.MejaKursi;
import objek.barang.MesinCuci;
import objek.barang.Radio;
import objek.barang.RakBuku;
import objek.barang.Sajadah;
import objek.barang.TV;
import objek.barang.Toilet;
import objek.barang.Treadmill;
import objek.makanan.Ayam;
import objek.makanan.BahanMakanan;
import objek.makanan.Bayam;
import objek.makanan.Bistik;
import objek.makanan.Kacang;
import objek.makanan.Kentang;
import objek.makanan.Makanan;
import objek.makanan.Masakan;
import objek.makanan.Nasi;
import objek.makanan.NasiAyam;
import objek.makanan.NasiKari;
import objek.makanan.Sapi;
import objek.makanan.Susu;
import objek.makanan.SusuKacang;
import objek.makanan.TumisSayur;
import objek.makanan.Wortel;
import tile.*;

public class Sim extends Entity implements Bekerja{
	
	private GamePanel gp;
	private KeyHandler keyH;

	//ATRIBUT
	public String pekerjaan;
	public String simName;
	public int uang;
	public ArrayList<Objek> inventory = new ArrayList<>();
	private final int maxInventorySize = 32;
	public int kekenyangan;
	public int mood;
	public int kesehatan;
	public String status;
	public Rumah curRumah;
	public Ruangan curRuangan;
	public Map<String,Integer> listPekerjaan;
	public ArrayList<Objek> listBelanja;
	public boolean berkunjung; 
	public boolean hasKerja;
	public boolean gantiKerja;
	private int counter = 0;
	public int durationKerja;
	public boolean useObject;
	public int interactObjectIdx;
	public Makanan makanan;
	public Barang selectBarang;
	private final int maxKekenyangan = 100;
	private final int maxMood = 100;
	private final int maxKesehatan = 100;
	public int waktuTidur = 0;
	public int waktuKerja = 0;
	public int akumulasiKerja = 0;
	
	public Sim(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;

		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX = 8;
		solidAreaDefaultY = 16;

		listPekerjaan = new HashMap<String,Integer>();
		loadPekerjaan();

		listBelanja = new ArrayList<>();
		
		setDefaultValues();
		getPlayerImage();
		setItems();}

	private void loadPekerjaan(){
		listPekerjaan.put("Badut Sulap",15);
		listPekerjaan.put("Koki",30);
		listPekerjaan.put("Polisi",35);
		listPekerjaan.put("Programmer",45);
		listPekerjaan.put("Dokter",50);}
	
	//GETTER
	public String getPekerjaan(){return pekerjaan;}
	public int getUang(){return uang;}
	public int getKekenyangan(){return kekenyangan;}
	public int getMood(){return mood;}
	public int getKesehatan(){return kesehatan;}
	public String getStatus(){return status;}
	public String getSimName(){return simName;}
	public int getWaktuTidur(){return waktuTidur;}
	public int getWaktuKerja(){return waktuKerja;}
	public Map<String, Integer> getListPekerjaan(){
		return listPekerjaan;
	}
    public int gajiPekerjaan(String pekerjaan){
		return listPekerjaan.get(pekerjaan);
	}
	public int getMaxInventorySize(){return maxInventorySize;}
	//SETTER
	public void setName(String simName){this.simName = simName;}
	public void setDefaultValues() {
		screenX = gp.startRoomX + gp.tileSize*2 + gp.tileSize/2;
		screenY = gp.startRoomY + gp.tileSize*2 + gp.tileSize/2;
		speed = 2;
		direction = "down";

		//ATRIBUT SIM
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
	public void setPekerjaan(int idx){
		String[] keys = listPekerjaan.keySet().toArray(new String[0]);
		this.pekerjaan = keys[idx];
	}
	public void setCurRumah(Rumah rumah){
		this.curRumah = rumah;
	}
	public void setCurRuangan(Ruangan ruangan){
		this.curRuangan = ruangan;
	}
	public void setPlusAkumulasiKerja(int waktu){akumulasiKerja+=waktu;}
	public void setItems(){
		inventory.add(new KasurSingle(gp));
		inventory.add(new Toilet(gp));
		inventory.add(new KomporGas(gp));
		inventory.add(new MejaKursi(gp));
		inventory.add(new Jam(gp));
	}
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
	public void setWaktuTidur(int duration){waktuTidur=duration;}
	public void setWaktuKerja(int duration){waktuKerja=duration;}
	public void plusWaktuTidur(int duration){waktuTidur+=duration;}
	public void plusWaktuKerja(int duration){waktuKerja+=duration;}


	//METHOD
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
			int gaji = gajiPekerjaan(pekerjaan);
			if(waktuKerja>=60*60*8){
				plusUang(2*gaji);
				waktuKerja-=60*60*8;
			} 
			if(waktuKerja>=60*60*4){
				plusUang(gaji);
				waktuKerja-=60*60*4;
			} 
			counter=0;
			gp.ui.setNotifMessage("Anda telah pulang bekerja");
			gp.gameState=gp.notifState;
			status="IDLE";
			getPlayerImage();
		}
		hasKerja = true;
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
			case "Nasi Kari" : return checkNasiKari();
			case "Bistik" 	 : return checkBistik();
			case "Tumis Sayur" : return checkTumisSayur();			
			case "Susu Kacang" : return checkSusuKacang();
			default : return false;
		}
    }
	private boolean checkNasiAyam(){
		boolean adaNasi = false;
		boolean adaAyam = false;
		Objek nasi = null;
		Objek ayam = null;

		for(Objek obj : inventory){
			if(obj.getName().equals("Nasi")){
				adaNasi = true;
				nasi = obj;
				break;
			}
		}
		
		for(Objek obj : inventory){
			if(obj.getName().equals("Ayam")){
				adaAyam = true;
				ayam = obj;
				break;
			}
		}

		if(adaAyam && adaNasi){
			inventory.remove(nasi);
			inventory.remove(ayam);
		}

		return adaAyam && adaNasi;
	}
	private boolean checkNasiKari(){
		boolean adaNasi = false;
		boolean adaKentang = false;
		boolean adaWortel = false;
		boolean adaSapi = false;
		Objek nasi = null;
		Objek kentang = null;
		Objek wortel = null;
		Objek sapi = null;

		for(Objek obj : inventory){
			if(obj.getName().equals("Nasi")){
				adaNasi = true;
				nasi = obj;
				break;
			}
		}

		for(Objek obj : inventory){
			if(obj.getName().equals("Kentang")){
				adaKentang = true;
				kentang = obj;
				break;
			}
		}

		for(Objek obj : inventory){
			if(obj.getName().equals("Wortel")){
				adaWortel = true;
				wortel = obj;
				break;
			}
		}

		for(Objek obj : inventory){
			if(obj.getName().equals("Sapi")){
				adaSapi = true;
				sapi = obj;
				break;
			}
		}

		if(adaNasi && adaKentang && adaWortel && adaSapi){
			inventory.remove(nasi);
			inventory.remove(kentang);
			inventory.remove(wortel);
			inventory.remove(sapi);
		}

		return adaNasi && adaKentang && adaWortel && adaSapi;
	}
	private boolean checkBistik(){
		boolean adaKentang = false;
		boolean adaSapi = false;
		Objek kentang = null;
		Objek sapi = null;

		for(Objek obj : inventory){
			if(obj.getName().equals("Kentang")){
				adaKentang = true;
				kentang= obj;
				break;
			}
		}

		for(Objek obj : inventory){
			if(obj.getName().equals("Sapi")){
				adaSapi = true;
				sapi=obj;
				break;
			}
		}
		if(adaKentang && adaSapi){
			inventory.remove(kentang);
			inventory.remove(sapi);
		}

		return adaKentang && adaSapi;
	}
	private boolean checkTumisSayur(){
		boolean adaWortel = false;
		boolean adaBayam = false;
		Objek wortel = null;
		Objek bayam = null;

		for(Objek obj : inventory){
			if(obj.getName().equals("Wortel")){
				adaWortel = true;
				wortel= obj;
				break;
			}
		}

		for(Objek obj : inventory){
			if(obj.getName().equals("Bayam")){
				adaBayam = true;
				bayam= obj;
				break;
			}
		}

		if(adaWortel && adaBayam){
			inventory.remove(wortel);
			inventory.remove(bayam);
		}

		return adaWortel && adaBayam;
	}
	private boolean checkSusuKacang(){
		boolean adaSusu = false;
		boolean adaKacang = false;
		Objek susu = null;
		Objek kacang = null;

		for(Objek obj : inventory){
			if(obj.getName().equals("Susu")){
				adaSusu = true;
				susu=obj;
				break;
			}
		}

		for(Objek obj : inventory){
			if(obj.getName().equals("Kacang")){
				adaKacang = true;
				kacang=obj;
				break;
			}
		}

		if(adaSusu && adaKacang){
			inventory.remove(susu);
			inventory.remove(kacang);
		}

		return adaSusu && adaKacang;
	}
}
