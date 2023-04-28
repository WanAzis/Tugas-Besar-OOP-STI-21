package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Sajadah extends Objek{

	public Sajadah(GamePanel gp) {
		this.gp = gp;
		name = "Sajadah";
		action = "beribadah";
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
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.setKekenyangan(sim.getKekenyangan()-20); //belom diset
			sim.setMood(sim.getMood()+10);
			gp.gameState=gp.playState;
			sim.getPlayerImage();
		}
	}
}
