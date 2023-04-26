package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Jam extends Objek{

    public Jam(GamePanel gp) {
		this.gp = gp;
		name = "Jam";
		action = "lihat waktu";
		panjang = 1;
		lebar = 1;
		solidArea = new Rectangle(3,3,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/jam1.png"));
			
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
			sim.setKesehatan(sim.getKesehatan()+20);
			sim.setMood(sim.getMood()+30);
			gp.gameState=gp.playState;
			sim.getPlayerImage();
		}
	}
}
