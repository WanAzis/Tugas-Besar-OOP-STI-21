package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Toilet extends Barang{

	public Toilet(GamePanel gp) {
		this.gp = gp;
		name = "Toilet";
		action = "BUANG AIR";
		deskripsi = "[ " + name + " ] \nDibutuhkan\nuntuk buang air"; 
		panjang = 1;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/toilet/toilet_down.png"));
			left = ImageIO.read(new File("../resources/barang/toilet/toilet_left.png"));
			right = ImageIO.read(new File("../resources/barang/toilet/toilet_right.png"));
			up = ImageIO.read(new File("../resources/barang/toilet/toilet_up.png"));
			// downUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_down_used.png"));
			// leftUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_left_used.png"));
			// rightUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_right_used.png"));
			// upUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_up_used.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		gp.sim.setStatus(action);
		image = left;
	}

	@Override
	public void unUsed()
	{
		gp.sim.setStatus("IDLE");
		image = down;
	}

	@Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.plusKekenyangan(-20);
			sim.plusMood(10);
			gp.ui.setNotifMessage("Selamat anda sudah buang air, \nkekenyangan -20 dan mood +10");
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
		}

	}
}

/* toilet tinggal masalah sebelum makan */
