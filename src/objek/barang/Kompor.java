package objek.barang;

import entity.Sim;
import main.GamePanel;

public class Kompor extends Barang {
    //konstruktor
    public Kompor(GamePanel gp)
    {
        this.gp = gp;
        // name = "Kompor";
        action = "MEMASAK";
        direction = "down";
        screenX = gp.tileSize*1;
		screenY = gp.tileSize*3;
    }
    //effect
    public void effect(Sim sim, int duration)
    {
        counter++;
        if(counter >= duration)
        {
            unUsed();
            counter = 0;
            sim.plusMood(10);
            gp.ui.setNotifMessage("Selamat anda sudah memasak, \nmood +10");
            gp.gameState=gp.notifState;
            sim.getPlayerImage();
        }
    }
}
