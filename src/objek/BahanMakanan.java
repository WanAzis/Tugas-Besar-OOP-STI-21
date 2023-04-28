package objek;

import entity.Sim;
import main.GamePanel;

public class BahanMakanan extends Makanan{
    
    protected int harga;
    protected int kekenyangan;

    BahanMakanan(GamePanel gp){
        this.gp = gp;
    }

    //GETTER
    public int getHarga(){return harga;}
    public int getKekenyangan(){return kekenyangan;}

    //SETTER

    public void used(Sim sim){
        counter++;
        if(counter>=60*5){
            sim.plusKekenyangan(kekenyangan);
            gp.ui.setNotifMessage("Anda selesai makan " + name + ",\nkekenyangan +" + kekenyangan);
            gp.gameState=gp.notifState;
        }
    }
}
