package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Sajadah extends Barang{

	public Sajadah(GamePanel gp) {
		this.gp = gp;
		name = "Sajadah";
		action = "BERIBADAH";
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk buang air";
		panjang = 2;
		lebar = 1;
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
			sim.plusMood(15);
			gp.ui.setNotifMessage("Selamat anda sudah beribadah, \nmood +15");
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
		}
	}
}
