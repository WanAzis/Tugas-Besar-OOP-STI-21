package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objek.Barang;

public class KeyHandler implements KeyListener{

	public GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public int objDuration;

	public KeyHandler(GamePanel gp){
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//TITLE STATE
		if(gp.gameState==gp.titleState){
			titleState(code);
		}
		//PLAY STATE
		else if(gp.gameState==gp.playState){
			playState(code);
		}
		//PAUSE STATE
		else if(gp.gameState==gp.pauseState){
			pauseState(code);
		}
		//SIMINFO STATE
		else if(gp.gameState==gp.simInfo){
			simInfoState(code);
		}
		//INTERACTOBJECT STATE
		else if(gp.gameState==gp.durationState){
			durationState(code, gp.obj[gp.sim.interactObjectIdx]);
		}
		else if(gp.gameState==gp.notifState){
			notifState(code);
		}
		else if(gp.gameState == gp.menuState)
		{
			menuState(code);
		}
	}

	private void titleState(int code){
		if(code == KeyEvent.VK_UP){
			if(gp.ui.commandNum>0){
				gp.ui.commandNum--;
			}
		}
		if(code == KeyEvent.VK_DOWN){
			if(gp.ui.commandNum<2){
				gp.ui.commandNum++;
			}
		}
		if(code == KeyEvent.VK_ENTER){
			if(gp.ui.commandNum==0){
				gp.gameState=gp.playState;
			}
			else if(gp.ui.commandNum==1){
				//LOAD GAME
			}
			else if(gp.ui.commandNum==2){
				System.exit(0);
			}
		}
	}
	private void playState(int code){
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if(code == KeyEvent.VK_F){
			gp.gameState = gp.simInfo;
		}
		if(code == KeyEvent.VK_ENTER && gp.sim.interactObject){
			if(gp.obj[gp.sim.interactObjectIdx].getName()=="Kasur")
			{
				gp.gameState=gp.durationState;
			}
			else if(gp.obj[gp.sim.interactObjectIdx].getName()=="Toilet")
			{
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*10);
				gp.gameState=gp.useObjectState;
				gp.sim.setNullImage();
				gp.obj[gp.sim.interactObjectIdx].used();
			}
			else if(gp.obj[gp.sim.interactObjectIdx].getName()=="Kompor")
			{
				gp.gameState = gp.menuState;
			}
			else if(gp.obj[gp.sim.interactObjectIdx].getName()=="Meja kursi")
			{
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*30);
				gp.gameState=gp.useObjectState;
				gp.sim.setNullImage();
				gp.obj[gp.sim.interactObjectIdx].used();
			}
			else if(gp.obj[gp.sim.interactObjectIdx].getName()=="Mesin cuci")
			{
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*20);
				gp.gameState=gp.useObjectState;
				// gp.sim.setNullImage();
				gp.obj[gp.sim.interactObjectIdx].used();
			}
			else if(gp.obj[gp.sim.interactObjectIdx].getName()=="Rak buku")
			{
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60);
				gp.gameState=gp.useObjectState;
				// gp.sim.setNullImage();
				gp.obj[gp.sim.interactObjectIdx].used();
			}
			else if(gp.obj[gp.sim.interactObjectIdx].getName()=="Sajadah")
			{
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*45);
				gp.gameState=gp.useObjectState;
				gp.sim.setNullImage();
				gp.obj[gp.sim.interactObjectIdx].used();
			}
		}
	}
	private void durationState(int code, Barang obj){
		switch(obj.getName()){
			case "Kasur" : durationKasurState(code);
		}
	}

	private void menuState(int code)
	{
		if(code == KeyEvent.VK_UP){
			if(gp.ui.commandNum>0){
				gp.ui.commandNum--;
			}
		}
		if(code == KeyEvent.VK_DOWN){
			if(gp.ui.commandNum<5){
				gp.ui.commandNum++;
			}
		}
		if(code == KeyEvent.VK_ESCAPE){
			gp.gameState=gp.playState;
		}
		if(code == KeyEvent.VK_ENTER){
			if(gp.ui.commandNum==0){ //nasiayam
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*(16+(16/2)));
			}
			else if(gp.ui.commandNum==1){//nasikari
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*(30+(30/2)));
			}
			else if(gp.ui.commandNum==2){//susukacang
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*(5+(5/2)));
			}
			else if(gp.ui.commandNum==3){//tumissayur
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*(5+(5/2)));
			}
			else if(gp.ui.commandNum==3){//bistik
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*(22+(22/2)));
			}
			gp.gameState=gp.useObjectState;
			gp.sim.setNullImage();
			gp.obj[gp.sim.interactObjectIdx].used();
		}
	}

	private void durationKasurState(int code){
		if(code == KeyEvent.VK_UP){
			if(gp.ui.commandNum>0){
				gp.ui.commandNum--;
			}
		}
		if(code == KeyEvent.VK_DOWN){
			if(gp.ui.commandNum<3){
				gp.ui.commandNum++;
			}
		}
		if(code == KeyEvent.VK_ESCAPE){
			gp.gameState=gp.playState;
		}
		if(code == KeyEvent.VK_ENTER){
			if(gp.ui.commandNum==0){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*5);
			}
			else if(gp.ui.commandNum==1){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60*4);
			}
			else if(gp.ui.commandNum==2){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60*6);
			}
			else if(gp.ui.commandNum==3){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60*8);
			}
			gp.gameState=gp.useObjectState;
			gp.sim.setNullImage();
			gp.obj[gp.sim.interactObjectIdx].used();
		}
	}
	private void notifState(int code){
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	private void pauseState(int code){
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}
	private void simInfoState(int code){
		if(code == KeyEvent.VK_F){
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_UP){
			if(gp.ui.slotRow>0){
				gp.ui.slotRow--;
			}
		}
		if(code == KeyEvent.VK_DOWN){
			if(gp.ui.slotRow<3){
				gp.ui.slotRow++;
			}
		}
		if(code == KeyEvent.VK_RIGHT){
			if(gp.ui.slotCol<7){
				gp.ui.slotCol++;
			}
		}
		if(code == KeyEvent.VK_LEFT){
			if(gp.ui.slotCol>0){
				gp.ui.slotCol--;
			}
		}
		if(code == KeyEvent.VK_ENTER){
			gp.sim.selectItem();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}
}
