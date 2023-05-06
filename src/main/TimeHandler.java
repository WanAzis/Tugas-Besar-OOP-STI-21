package main;

import objek.Objek;

public class TimeHandler {
    //ATRIBUT
    private GamePanel gp;
    private int dayCounter;
	private int day;
	private int detik;
    private int curTidur, curToilet;
    private int beliBarang, upgradeRumah;
    private String namaCalonRuangan, posisiCalonRuangan;
    public int counterBerkunjung;
    public boolean createSim = true;

    public TimeHandler(GamePanel gp)
    {
        this.gp = gp;
        createNewGame();
    }

    public void minusTime(int x)
    {
        this.detik += x;
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
    public void setUpgradeRumah(int upgradeRumah){this.upgradeRumah=upgradeRumah;}
    public void setNamaCalonRuangan(String namaCalonRuangan){this.namaCalonRuangan=namaCalonRuangan;}
    public void setPosisiCalonRuangan(String posisiCalonRuangan){this.posisiCalonRuangan=posisiCalonRuangan;}

    public void update() {
        if(!gp.curSim.getStatus().equals("IDLE")){
            dayCounter++;
            if(dayCounter>=60){
                detik++;
                dayCounter=0;
                if(beliBarang>0){
                    checkBeliBarang();
                }
                if(upgradeRumah>0){
                    checkUpgradeRumah();
                }
                if(gp.curSim.berkunjung){
                    counterBerkunjung++;
                }
            }
            if(detik>=720){
                day++;
                dayReset();
            }
            checkEffect();
        }
    }
    private void dayReset(){
        createSim = true;
        detik=0;
        curTidur = 0;
        curToilet = 0; 
        gp.curSim.gantiKerja=false;       
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
    public int getCounterBerkunjung() {
        return this.counterBerkunjung;
    }

    public void setCounterBerkunjung(int x)
    {
        this.counterBerkunjung = x;
    }

    public void checkEffect(){
        if(!checkTidur())
        {
            gp.curSim.plusKesehatan(-5);
            gp.curSim.plusMood(-5);
            setCurTidur();
        }
        if(!checkToilet())
        {
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
            for(Objek obj : gp.curSim.listBelanja){
                gp.curSim.inventory.add(obj);
            }
            gp.curSim.listBelanja.clear();
        }
    }

    public void checkUpgradeRumah(){
        upgradeRumah--;
        if(upgradeRumah==0){
            gp.curSim.curRumah.tambahRuang(posisiCalonRuangan, namaCalonRuangan, gp.ui.getChooseRuangan());
        }
    }

}
