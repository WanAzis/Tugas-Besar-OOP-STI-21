package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class MejaKursi extends Objek{

	public MejaKursi(GamePanel gp) {
		this.gp = gp;
		name = "Meja kursi";
		action = "MAKAN";
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk makan";
		panjang = 3;
		lebar = 3;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png")); //ganti
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png")); //ganti
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void unUsed()
	{
		gp.sim.setStatus("IDLE");
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png")); //ganti foto
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
			sim.plusKekenyangan(sim.getMakanan().getKekenyangan());
            gp.ui.setNotifMessage("Selamat anda sudah memasak, \nkekenyangan " + sim.getMakanan().getKekenyangan());
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
		}
	}
}