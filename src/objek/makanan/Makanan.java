package objek.makanan;



import entity.Sim;
import objek.Objek;

public class Makanan extends Objek {

    protected int kekenyangan;

    //GETTER
    public int getKekenyangan(){return kekenyangan;}

    public void used(Sim sim){
        counter++;
        if(counter>=60*5){
            counter = 0;
            sim.makanan = null;
            sim.plusKekenyangan(kekenyangan);
            gp.timeH.setCurToilet();
            gp.ui.setNotifMessage("Anda selesai makan " + name + ",\nkekenyangan +" + kekenyangan);
            if(sim.findMejaKursiIdx()!=999){
                gp.curSim.curRuangan.obj[sim.findMejaKursiIdx()].unUsed();
            }
            gp.gameState=gp.notifState;
            sim.getPlayerImage();
        }
    } 
}
