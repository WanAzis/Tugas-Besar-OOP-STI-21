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
            sim.setKesehatan(sim.getKesehatan() - 5);
            sim.setMood(sim.getMood() - 5);
        }
        if(!checkToilet())
        {
            sim.setKesehatan(sim.getKesehatan() - 5);
            sim.setMood(sim.getMood() - 5);
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