package objek.makanan;

import main.GamePanel;

public class Masakan extends Makanan{
    
    public BahanMakanan[] listBahan;

    public Masakan(GamePanel gp){
        this.gp = gp;
    }

    //GETTER

    //SETTER
    public void setListBahan(){}

    public void checkAvailable(String masakan){
        if(masakan=="Nasi Ayam"){
            gp.curSim.checkAvailableInventory(masakan);}
        else if (masakan=="Nasi Kari"){
            gp.curSim.checkAvailableInventory(masakan);}
        else if (masakan=="Bistik"){
            gp.curSim.checkAvailableInventory(masakan);}
        else if (masakan=="Tumis Sayur"){
            gp.curSim.checkAvailableInventory(masakan);}
        else if (masakan=="Susu Kacang"){
            gp.curSim.checkAvailableInventory(masakan);}
    }
}
