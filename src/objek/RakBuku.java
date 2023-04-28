package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class RakBuku extends Barang{

	public RakBuku(GamePanel gp) {
		this.gp = gp;
		name = "Rak buku";
		action = "MEMBACA";
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk membaca"; 
		panjang = 3;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png")); //ganti foto
			
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
			sim.plusMood(10);
			gp.ui.setNotifMessage("Selamat anda sudah membaca, \nmood +10");
			gp.gameState=gp.notifState;
			// sim.getPlayerImage(); ini gatau kalo baca gambarannya gmn guys? ini asumsi klo dia ga ilang ya ky berdiri doang
		}
	}
}
