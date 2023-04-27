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
		action = "TIDUR";
		panjang = 4;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/kasursingle1.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		gp.sim.setStatus(action);
		try {
			image = ImageIO.read(new File("../resources/barang/kasursingle2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unUsed() {
		gp.sim.setStatus("IDLE");
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
			if(duration>=60*60*4){
				sim.plusKesehatan(20);
				sim.plusMood(30);
				gp.ui.setNotifMessage("Selamat anda sudah tidur,\nkesehatan +20 dan mood +30");
			}
			else if(duration>=60*60*8){
				sim.plusKesehatan(40);
				sim.plusMood(60);
				gp.ui.setNotifMessage("Selamat anda sudah tidur,\nkesehatan +40 dan mood +60");
			}
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
		}
	}
}
