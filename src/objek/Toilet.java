package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Toilet extends Barang{

	public Toilet(GamePanel gp) {
		this.gp = gp;
		name = "Toilet";
		action = "BUANG AIR";
		deskripsi = "[ " + name + " ] \nDibutuhkan\nuntuk buang air"; 
		panjang = 1;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		try {
			image = ImageIO.read(new File("../resources/barang/komlis4.png")); //BELOM ganti gambar
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void unUsed()
	{
		gp.sim.setStatus("IDLE");
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.plusKekenyangan(-20);
			sim.plusMood(10);
			gp.ui.setNotifMessage("Selamat anda sudah buang air, \nkekenyangan -20 dan mood +10");
			gp.eHandler.setCurToilet();
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
		}

	}
}

/* toilet tinggal masalah sebelum makan */
