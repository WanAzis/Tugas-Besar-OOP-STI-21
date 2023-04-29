package objek.barang;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Kasur extends Barang{

	public Kasur(GamePanel gp) {
		this.gp = gp;
		name = "Kasur";
		action = "TIDUR";
		direction = "down";
		screenX = gp.tileSize;
		screenY = gp.tileSize;
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
			else{
				gp.ui.setNotifMessage("Anda belum cukup tidur");
			}
			gp.eHandler.setCurTidur();
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
		}
	}
}
