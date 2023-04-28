package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class TV extends Barang{

	public TV(GamePanel gp) {
		this.gp = gp;
		name = "TV";
		action = "MENONTON";
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk menonton"; 
		panjang = 2;
		lebar = 2;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png")); //ganti gambar
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png"));
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
			sim.setMood(10);
			sim.setKekenyangan(-5);
			gp.ui.setNotifMessage("Selamat anda sudah menonton TV, \nkekenyangan -5 dan mood +10");
			gp.gameState=gp.notifState;
		}
	}
}
