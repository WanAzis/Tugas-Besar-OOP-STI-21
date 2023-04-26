package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
			durationState(code);
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
			gp.gameState=gp.durationState;
		}
	}
	private void durationState(int code){
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
		if(code == KeyEvent.VK_ENTER){
			if(gp.ui.commandNum==0){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60);
			}
			else if(gp.ui.commandNum==1){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60*2);
			}
			else if(gp.ui.commandNum==2){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60*3);
			}
			else if(gp.ui.commandNum==3){
				gp.obj[gp.sim.interactObjectIdx].setDuration(60*60*4);
			}
			gp.gameState=gp.useObjectState;
			gp.sim.setNullImage();
			gp.obj[gp.sim.interactObjectIdx].used();
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