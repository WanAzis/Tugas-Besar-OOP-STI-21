package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Kasur extends Objek{

	public Kasur(GamePanel gp) {
		this.gp = gp;
		name = "Kasur";
		action = "tidur";
		duration = 
		panjang = 4;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/kasursingle1.png"));
			// imageUsed = ImageIO.read(new File("../resources/barang/kasursingle2.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		try {
			image = ImageIO.read(new File("../resources/barang/kasursingle2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unUsed() {
		try {
			image = ImageIO.read(new File("../resources/barang/kasursingle1.png"));
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
			sim.setKesehatan(sim.getKesehatan()+20);
			sim.setMood(sim.getMood()+30);
			gp.gameState=gp.playState;
			sim.getPlayerImage();
		}
	}
}
