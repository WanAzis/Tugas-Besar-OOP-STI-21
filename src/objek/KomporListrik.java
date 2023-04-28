package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class KomporListrik extends Objek{

	public KomporListrik(GamePanel gp) {
		this.gp = gp;
		name = "Kompor Listrik";
		action = "Memasak";
		duration = 
		panjang = 1;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/komlis1.png"));
			// imageUsed = ImageIO.read(new File("../resources/barang/kasursingle2.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		try {
			image = ImageIO.read(new File("../resources/barang/komlis2.png")); //perlu diganti ke gambar yg kalo lagi masak
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unUsed() {
		try {
			image = ImageIO.read(new File("../resources/barang/komlis1.png"));
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
			// sim.setMood(sim.getMood()+10);
			gp.gameState=gp.playState;
			sim.getPlayerImage();
		}
	}
}


