package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Currency;

import entity.Sim;
import tile.Rumah;
import tile.TileManager;
import tile.Tile;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    public final int originalTileSize = 16;  // 16x16 Pixel
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 pixel
    public final int maxScreenCol = 10;
    public final int maxScreenRow = 10;
    public final int screenWidth = tileSize * getMaxScreenCol();
    public final int screenHeight = tileSize * getMaxScreenRow();
	public final int roomWidth = tileSize * 6;
	public final int roomHeight = tileSize * 6;
	public final int startRoomX = tileSize;
	public final int startRoomY = tileSize * 4;
	public final int startPanelX = tileSize * 8;
	public final int startPanelY = tileSize * 0;
	public final int worldTileSize = 7;
	public final int startWorldScreen = 7*2;

    //FPS
    private final int FPS = 60;
    
	//SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
	public TimeHandler timeH = new TimeHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
	public UI ui = new UI(this);
    Thread gameThread;

	//PLAYER
    public Sim sim = new Sim(this ,keyH);
	public Sim curSim;
	public ArrayList<Sim> listSim = new ArrayList<>();
	public ArrayList<Rumah> listRumah = new ArrayList<>(); 
    
	//GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int simInfo = 3;
	public final int useObjectState = 4;
	public final int durationState = 5;
	public final int notifState = 6;
    public final int menuMasakanState = 7;
	public final int useMakananState = 8;
	public final int placeObjectState = 9;
	public final int placeRumahWorldState = 10;
	public final int menuSimState = 11;
	public final int storeState = 12;
	public final int kerjaState = 13;
  	public final int helpState = 14;
	public final int menuGameState = 15;
	public final int editRoomState = 16;
	public final int worldState = 17;
	public final int jamState= 21;
  
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setUpGame() {
		gameState = titleState;
    }
    
	//GETTER
	public int getMaxScreenCol() {
		return maxScreenCol;
	}
	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	//SETTER

	//METHOD
    public void startGameThread() {
    	
		gameThread = new Thread(this);
    	gameThread.start();
    }

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread!=null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				//UPDATE
				update();
				
				//DRAW
				repaint();
				delta--;
			}
		}
	} 
	
	public void update() {

		if(gameState==playState){
			curSim.update();
			timeH.update();
		}
		else if(gameState==pauseState){
			//Sim tidak di update
		}
		else if(gameState==useObjectState){
			timeH.update();
			useObjectUpdate(curSim.interactObjectIdx, curSim.curRuangan.obj[curSim.interactObjectIdx].getDuration());
		}
		else if(gameState==useMakananState){
			timeH.update();
			useMakananUpdate();
		}
		else if(gameState==placeObjectState){
			curSim.selectBarang.collisionWithOthers=false;
			cChecker.checkPlaceObject(curSim.selectBarang);
		}
		else if(gameState==kerjaState){
			timeH.update();
			curSim.kerja();
		}
		else{}
	}

	public void useObjectUpdate(int i, int duration){
		curSim.curRuangan.obj[i].effect(curSim, duration);
	}

	public void useMakananUpdate(){
		curSim.makanan.used(curSim);
	}

	public void paintComponent(Graphics gp) {
		super.paintComponent(gp);
		Graphics2D g2 = (Graphics2D)gp;

		//TITLE SCREEN
		if(gameState == titleState){
			ui.draw(g2);
		} 
		//WORLD SCREEN
		else if(gameState == worldState || gameState==placeRumahWorldState){
			tileM.drawWorld(g2);
			for(Rumah rm : listRumah){
				rm.draw(g2);
			}
		}
		//ELSE
		else{
			tileM.draw(g2);
			for(int i = 0; i<curSim.curRuangan.obj.length; i++) {
				if(curSim.curRuangan.obj[i]!=null) {
					curSim.curRuangan.obj[i].draw(g2, this);
				}
			}
			//PLAYER
			curSim.draw(g2);
	
			//UI
			ui.draw(g2);
			g2.dispose();
		}

	}
}
