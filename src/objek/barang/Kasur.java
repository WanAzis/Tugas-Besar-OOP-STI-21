package objek.barang;

import entity.Sim;
import main.GamePanel;

public class Kasur extends Barang{

	public Kasur(GamePanel gp) {
		this.gp = gp;
		action = "TIDUR";
		direction = "down";
		screenX = gp.tileSize;
		screenY = gp.tileSize*3;
	}

	@Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			if(sim.getWaktuTidur()>=60*60*8){
				sim.plusKesehatan(40);
				sim.plusMood(60);
				sim.plusWaktuTidur(-60*60*8);
				gp.ui.setNotifMessage("Selamat anda sudah tidur,\nkesehatan +40 dan mood +60");
			}
			if(sim.getWaktuTidur()>=60*60*4){
				sim.plusKesehatan(20);
				sim.plusMood(30);
				sim.plusWaktuTidur(-60*60*4);
				gp.ui.setNotifMessage("Selamat anda sudah tidur,\nkesehatan +20 dan mood +30");
			}
			else{
				gp.ui.setNotifMessage("Anda belum cukup tidur");
			}
			gp.timeH.setCurTidur();
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
		}
	}
}
