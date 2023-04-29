package objek.makanan;

import main.GamePanel;

public class Masakan extends Makanan{
    
    public BahanMakanan[] listBahan;
    protected int kekenyangan;

    public Masakan(GamePanel gp){
        this.gp = gp;
    }

    //GETTER

    //SETTER
    public void setListBahan(){}

    public void checkAvailable(String masakan){
        if(masakan=="Nasi Ayam"){
            gp.sim.checkAvailableInventory(masakan);
        }
    }
}
