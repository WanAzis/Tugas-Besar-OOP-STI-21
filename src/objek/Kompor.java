package objek;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Kompor extends Objek {
    //konstruktor
    public Kompor(GamePanel gp)
    {
        this.gp = gp;
        name = "Kompor";
        action = "MEMASAK";
        direction = "down";
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
        }
    }
}
