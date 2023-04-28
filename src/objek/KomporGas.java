package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class KomporGas extends Objek{

	public KomporGas(GamePanel gp) {
		this.gp = gp;
		name = "KomporGas";
		action = "Memasak";
		duration = 
		panjang = 2;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/komgas1.png"));
			// imageUsed = ImageIO.read(new File("../resources/barang/kasursingle2.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		try {
			image = ImageIO.read(new File("../resources/barang/komgas2.png")); //ganti sama gambar kl lagi masak
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unUsed() {
		try {
			image = ImageIO.read(new File("../resources/barang/komgas1.png"));
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
			sim.setMood(sim.getMood()+10);
			gp.gameState=gp.playState;
			sim.getPlayerImage();
		}
	}
}