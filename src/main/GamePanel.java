package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entity.Sim;
import tile.TileManager;
import objek.Objek;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16;  // 16x16 Pixel
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 pixel
    private final int maxScreenCol = 6;
    private final int maxScreenRow = 6;
    final int screenWidth = tileSize * getMaxScreenCol();
    final int screenHeight = tileSize * getMaxScreenRow();

    //FPS
    final int FPS = 60;
    

	//SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
    Thread gameThread;

	//PLAYER
    public Sim sim = new Sim(this,keyH);
    public Objek obj[] = new Objek[10];
    
	//GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
    
    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setUpGame() {
    	aSetter.setObject();
		gameState = titleState;
    }
    
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
			sim.update();
		}
		else if(gameState==pauseState){

		}
	}

	public void paintComponent(Graphics gp) {
		super.paintComponent(gp);
		Graphics2D g2 = (Graphics2D)gp;

		//TITLE SCREEN
		if(gameState == titleState){
			ui.draw(g2);
		} else{
			tileM.draw(g2);
			for(int i = 0; i<obj.length; i++) {
				if(obj[i]!=null) {
					obj[i].draw(g2, this);
				}
			}
			//PLAYER
			sim.draw(g2);
	
			//UI
			ui.draw(g2);
			g2.dispose();
		}

	}

	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	public int getMaxScreenRow() {
		return maxScreenRow;
	}
}
