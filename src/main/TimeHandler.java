package main;

import objek.Objek;

public class TimeHandler {
    //ATRIBUT
    private GamePanel gp;
    private int dayCounter;
	private int day;
	private int detik;
    private int curTidur, curToilet;
    private int sumkerja, sumTidur, sumOlahraga, sumMasak, waktuKunjung, beliBarang, upgradeRumah;

    public TimeHandler(GamePanel gp)
    {
        this.gp = gp;
        createNewGame();
    }
    
    private void createNewGame(){
        day = 1;
		dayCounter = 0;
        detik = 0;
        curTidur = 0;
        curToilet = 0;
	}
    //GETTER
    public int getDay(){return day;}
    public int getDetik(){return detik;}
    public String getTime(){
        String value = String.valueOf(720-detik);
        return value;
    }
    public int getBeliBarang() {
        return beliBarang;
    }
    public int getUpgradeRumah(){
        return upgradeRumah;
    }

    //SETTER
    public void setBeliBarang(int beliBarang){this.beliBarang = beliBarang;}

    public void update() {
        if(!gp.curSim.getStatus().equals("IDLE")){
            dayCounter++;
            if(dayCounter>=60){
                detik++;
                dayCounter=0;
                if(beliBarang>0){
                    checkBeliBarang();
                }
            }
            if(detik>=720){
                day++;
                detik=0;
                curTidur = 0;
                curToilet = 0;
            }
            checkEffect();
        }
    }

    //BAGIAN EFFECT HANDLER
    public void setCurTidur()
    {
        curTidur = detik;
    }

    public void setCurToilet()
    {
        curToilet = detik;
    }

    public void checkEffect(){
        if(!checkTidur())
        {
            gp.ui.setNotifMessage("Anda belum tidur, kesehatan \ndan mood -5");
            gp.gameState=gp.notifState;
            gp.curSim.setKesehatan(gp.curSim.getKesehatan() - 5);
            gp.curSim.setMood(gp.curSim.getMood() - 5);
            setCurTidur();
        }
        if(!checkToilet())
        {
            gp.ui.setNotifMessage("Anda belum buang air, kesehatan \ndan mood -5");
            gp.gameState=gp.notifState;
            gp.curSim.setKesehatan(gp.curSim.getKesehatan() - 5);
            gp.curSim.setMood(gp.curSim.getMood() - 5);
            setCurToilet();
        }
    }
    public boolean checkTidur()
    {
        if(detik - curTidur >= (60*10))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean checkToilet()
    {
        if(curToilet!=0){
            if(detik - curToilet >= (60*4))
            {
                return false;
            }
            else
            {
                return true;
            }
        } return true;
    }
    
    public void checkBeliBarang(){
        beliBarang--;
        if(beliBarang==0){
            gp.ui.setNotifMessage("Barang delivery anda sudah datang!");
            gp.gameState=gp.notifState;
            for(Objek obj : gp.curSim.listBelanja){
                gp.curSim.inventory.add(obj);
            }
            gp.curSim.listBelanja.clear();
        }
    }
}
