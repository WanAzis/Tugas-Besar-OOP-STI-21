package main;

public class effectHandler {
    GamePanel gp;
    int counter;
    int curTidur, curToilet;

    public effectHandler(GamePanel gp)
    {
        this.gp = gp;
        this.counter = 0;
        this.curTidur = 0;
        this.curToilet = 0;
    }

    public void checkEffect(){
        counter++;

        if(!checkTidur())
        {
            gp.ui.setNotifMessage("Anda belum tidur, kesehatan \ndan mood -5");
            gp.gameState=gp.notifState;
            gp.curSim.setKesehatan(gp.curSim.getKesehatan() - 5);
            gp.curSim.setMood(gp.curSim.getMood() - 5);
            counter = 0;
        }
        if(!checkToilet())
        {
            gp.ui.setNotifMessage("Anda belum buang air, kesehatan \ndan mood -5");
            gp.gameState=gp.notifState;
            gp.curSim.setKesehatan(gp.curSim.getKesehatan() - 5);
            gp.curSim.setMood(gp.curSim.getMood() - 5);
            counter = 0;
        }
    }

    public void setCurTidur()
    {
        curTidur = counter;
    }

    public void setCurToilet()
    {
        curToilet = counter;
    }

    public boolean checkTidur()
    {
        if(counter - curTidur >= (60*60*10))
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
            if(counter - curToilet >= (60*5))   //GANTI
            {
                return false;
            }
            else
            {
                return true;
            }
        } return true;
    }
}