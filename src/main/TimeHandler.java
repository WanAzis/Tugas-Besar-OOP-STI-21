package main;

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

    //SETTER
    public void update() {
        if(!gp.curSim.getStatus().equals("IDLE")){
            dayCounter++;
            if(dayCounter>=60){
                detik++;
                dayCounter=0;
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
            dayCounter = 0;
        }
        if(!checkToilet())
        {
            gp.ui.setNotifMessage("Anda belum buang air, kesehatan \ndan mood -5");
            gp.gameState=gp.notifState;
            gp.curSim.setKesehatan(gp.curSim.getKesehatan() - 5);
            gp.curSim.setMood(gp.curSim.getMood() - 5);
            dayCounter = 0;
        }
    }
    public boolean checkTidur()
    {
        if(detik - curTidur >= (5))
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
            if(detik - curToilet >= (60*5))   //GANTI
            {
                return false;
            }
            else
            {
                return true;
            }
        } return true;
    }
    
    public String getTime(){
		// String time, strJam, strMenit;
		// jam = dayCounter/1800;
		// int curMenit = dayCounter - (1800*jam);

		// if(curMenit%300 == 0){
		// 	menit+=10;
		// }
		// if(menit==60){
		// 	menit=0;
		// }
		// if(jam<10){
		// 	strJam = "0"+String.valueOf(jam);
		// } else strJam = String.valueOf(jam);
		// if(menit==0){
		// 	strMenit = "00";
		// } else strMenit = String.valueOf(menit);
		// time = strJam + " : " + strMenit;

        String value = String.valueOf(720-detik);
		return value;
	}
}
