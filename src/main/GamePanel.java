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

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16;  // 16x16 Pixel
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 pixel
    private final int maxScreenCol = 10;
    private final int maxScreenRow = 10;
    final int screenWidth = tileSize * getMaxScreenCol();
    final int screenHeight = tileSize * getMaxScreenRow();
	final int roomWidth = tileSize * 6;
	final int roomHeight = tileSize * 6;
	public final int startRoomX = tileSize;
	public final int startRoomY = tileSize * 4;
	final int startPanelX = tileSize * 8;
	final int startPanelY = tileSize * 0;

    //FPS
    final int FPS = 60;
    

	//SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public effectHandler eHandler = new effectHandler(this);
    Thread gameThread;
	private int dayCounter;
	private int day;
	private int jam;
	private int menit;

	//PLAYER
    public Sim sim = new Sim(this ,keyH);
	public Sim curSim;
	public ArrayList<Sim> listSim = new ArrayList<>();
	public ArrayList<Rumah> listRumah = new ArrayList<>(); 
    // public Barang obj[] = new Barang[10];
    
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
	public final int menuSimState = 11;
	public final int storeState = 12;
	public final int kerjaState = 13;
	public final int helpState = 14;
	
    
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
	public void createNewGame(){

		day = 1;
		dayCounter = 0;
		jam = 0;
		menit = 0;
	}
    
	//GETTER
	public int getDay(){return day;}
	public int getDayCounter(){return dayCounter;}
	public int getMaxScreenCol() {
		return maxScreenCol;
	}
	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	//SETTER
	// public void addBarang(Barang barang){
	// 	int i = 0;
	// 	while(i<obj.length && obj[i]!=null){
	// 		i++;
	// 	} 
	// 	obj[i] = barang;
	// }

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
			dayUpdate();
			eHandler.checkEffect();
		}
		else if(gameState==pauseState){
			//Sim tidak di update
		}
		else if(gameState==useObjectState){
			dayUpdate();
			useObjectUpdate(curSim.interactObjectIdx, curSim.curRuangan.obj[curSim.interactObjectIdx].getDuration());
		}
		else if(gameState==useMakananState){
			dayUpdate();
			useMakananUpdate();
		}
		else if(gameState==placeObjectState){
			curSim.selectBarang.collisionWithOthers=false;
			cChecker.checkPlaceObject(curSim.selectBarang);
		}
		else if(gameState==kerjaState){
			dayUpdate();
			curSim.kerja();
		}
		else{}
	}

	private void dayUpdate() {
		dayCounter++;
		if(dayCounter>=43200){
			day++;
			dayCounter=0;
		}
	}
	public String getTime(){
		String time, strJam, strMenit;
		jam = dayCounter/1800;
		int curMenit = dayCounter - (1800*jam);

		if(curMenit%300 == 0){
			menit+=10;
		}
		if(menit==60){
			menit=0;
		}
		if(jam<10){
			strJam = "0"+String.valueOf(jam);
		} else strJam = String.valueOf(jam);
		if(menit==0){
			strMenit = "00";
		} else strMenit = String.valueOf(menit);
		time = strJam + " : " + strMenit;

		return time;
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
