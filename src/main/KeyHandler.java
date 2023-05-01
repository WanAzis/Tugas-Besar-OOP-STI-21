package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Sim;
import objek.barang.Barang;
import tile.Rumah;

public class KeyHandler implements KeyListener{

	public GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
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
			if(gp.curSim.interactObject){
				durationState(code, gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName());
			} else {
				durationState(code, gp.curSim.getStatus());
			}
		}
		//NOTIF STATE
		else if(gp.gameState==gp.notifState){
			notifState(code);
		}
		//MENU STATE
		else if(gp.gameState == gp.menuMasakanState)
		{
			menuState(code);
		}
		//PLACE OBJEK STATE
		else if(gp.gameState==gp.placeObjectState){
			placeObjectScreen(code);
		}
		//CREATE SIM STATE
		else if(gp.gameState==gp.createSimState){
			createSimState(code);
		}
		//MENU SIM STATE
		else if(gp.gameState==gp.menuSimState){
			menuSimState(code);
		}
		//STORE STATE
		else if(gp.gameState==gp.storeState){
			storeState(code);
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
				gp.gameState=gp.createSimState;
				gp.createNewGame();
			}
			else if(gp.ui.commandNum==1){
				//LOAD GAME
			}
			else if(gp.ui.commandNum==2){
				System.exit(0);
			}
		}
	}
	private void createSimState(int code){
		//MEMASUKKAN NAMA PLAYER
		//JIKA TEKAN ENTER, SIM DIBUAT DAN DITAMBAHKAN KE LIST SIM DI GP DENGAN NAMA INPUTAN
		//OTOMATIS TAMBAHKAN RUMAH JUGA KE LIST RUMAH
		//JANGAN LUPA BUATKAN UI DARI CREATE SIM
		//UBAH KE STATE PLAY, DENGAN CURSIM ADALAH SIM YANG BARU DIBUAT
		if(code == KeyEvent.VK_ENTER){	//HAPUS BILA SUDAH
			Sim sim = new Sim(gp, this);
			gp.listSim.add(sim);
			gp.curSim = sim;
			Rumah rumah = new Rumah(sim);
			gp.listRumah.add(rumah);
			rumah.haveSim = sim;
			sim.curRumah = rumah;
			sim.curRuangan = rumah.listRuangan.get(0);
			gp.gameState=gp.playState;
		}
	}
	private void playState(int code){
		if(code == KeyEvent.VK_ESCAPE){
			gp.gameState = gp.titleState;
		}
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
		if(code == KeyEvent.VK_M){
			gp.gameState=gp.menuSimState;
		}
		if(code == KeyEvent.VK_ENTER && gp.curSim.interactObject){
			if(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName()=="Kasur")
			{
				gp.gameState=gp.durationState;
			}
			else if(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName()=="Toilet")
			{
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*10);
				gp.gameState=gp.useObjectState;
				gp.curSim.setNullImage();
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].used();
			}
			else if(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName()=="Kompor")
			{
				gp.gameState = gp.menuMasakanState;
			}
			else if(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName()=="Meja kursi")
			{
				gp.gameState=gp.simInfo;
			}
			else if(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName()=="Mesin cuci")
			{
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*20);
				gp.gameState=gp.useObjectState;
				// gp.curSim.setNullImage();
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].used();
			}
			else if(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName()=="Rak buku")
			{
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*60);
				gp.gameState=gp.useObjectState;
				// gp.curSim.setNullImage();
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].used();
			}
			else if(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName()=="Sajadah")
			{
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*45);
				gp.gameState=gp.useObjectState;
				gp.curSim.setNullImage();
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].used();
			}
		}
	}
	private void menuSimState(int code){
		if(code == KeyEvent.VK_M){
			gp.ui.subState = 0;
			gp.ui.commandNum = 0;
			gp.gameState=gp.playState;
		}
		if(code == KeyEvent.VK_ENTER){
			enterPressed=true;
		}
		if(code==KeyEvent.VK_ESCAPE){
			gp.ui.subState=0;
		}

		int maxCommandNum = 0;
		switch(gp.ui.subState){
			case 0: maxCommandNum=6;
			case 3: maxCommandNum=3;
		}
		if(code == KeyEvent.VK_UP){
			if(gp.ui.commandNum>0){
				gp.ui.commandNum--;
			}
		}
		if(code == KeyEvent.VK_DOWN){
			if(gp.ui.commandNum<maxCommandNum){
				gp.ui.commandNum++;
			}
		}
	}
	private void durationState(int code, String entitas){
		switch(entitas){
			case "Kasur" : durationKasurState(code);
			case "Kerja" : durationKerjaState(code);
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
			if(gp.ui.commandNum==0 && gp.curSim.checkAvailableInventory("Nasi Ayam")){ //nasiayam
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*(16+(16/2)));
				//nasi ayam masuk ke inventory
			}
			else if(gp.ui.commandNum==1){//nasikari
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*(30+(30/2)));
			}
			else if(gp.ui.commandNum==2){//susukacang
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*(5+(5/2)));
			}
			else if(gp.ui.commandNum==3){//tumissayur
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*(5+(5/2)));
			}
			else if(gp.ui.commandNum==3){//bistik
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*(22+(22/2)));
			}
			gp.gameState=gp.useObjectState;
			gp.curSim.setNullImage();
			gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].used();
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
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*5);
			}
			else if(gp.ui.commandNum==1){
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*60*4);
			}
			else if(gp.ui.commandNum==2){
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*60*6);
			}
			else if(gp.ui.commandNum==3){
				gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].setDuration(60*60*8);
			}
			gp.gameState=gp.useObjectState;
			gp.curSim.setNullImage();
			gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].used();
		}
	}
	private void durationKerjaState(int code){
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
			gp.gameState=gp.menuSimState;
		}
		if(code == KeyEvent.VK_ENTER){
			enterPressed = true;
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
			gp.curSim.selectItem();
		}
	}
	private void placeObjectScreen(int code) {
		if(code == KeyEvent.VK_R){
			gp.curSim.selectBarang.rotate();
		}
		if(code == KeyEvent.VK_UP){
			gp.curSim.selectBarang.moveUp();
		}
		if(code == KeyEvent.VK_DOWN){
			gp.curSim.selectBarang.moveDown();
		}
		if(code == KeyEvent.VK_RIGHT){
			gp.curSim.selectBarang.moveRight();
		}
		if(code == KeyEvent.VK_LEFT){
			gp.curSim.selectBarang.moveLeft();
		}
		if(code == KeyEvent.VK_ENTER && !gp.curSim.selectBarang.collisionWithOthers){
			gp.gameState=gp.playState;
		}
	}
	public void storeState(int code){
		if(code == KeyEvent.VK_ENTER){
			enterPressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE){
			gp.ui.subState = 0;
			gp.gameState=gp.menuSimState;
		}
		if(gp.ui.subState == 0){
			if(code == KeyEvent.VK_UP){
				if(gp.ui.commandNum>0){
					gp.ui.commandNum--;
				}
			}
			if(code == KeyEvent.VK_DOWN){
				if(gp.ui.commandNum<1){
					gp.ui.commandNum++;
				}
			}
		}
		if(gp.ui.subState == 1 || gp.ui.subState == 2){
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
			if(code == KeyEvent.VK_ESCAPE){
				gp.ui.subState = 0;
			}
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
