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
            gp.sim.setKesehatan(gp.sim.getKesehatan() - 5);
            gp.sim.setMood(gp.sim.getMood() - 5);
            counter = 0;
        }
        if(!checkToilet())
        {
            gp.sim.setKesehatan(gp.sim.getKesehatan() - 5);
            gp.sim.setMood(gp.sim.getMood() - 5);
            counter = 0;
        }
    }

    public void setCurTidur()
    {
        curTidur = counter;
    }

    public void serCurToilet()
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
        if(counter - curToilet >= (60*60*4))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}