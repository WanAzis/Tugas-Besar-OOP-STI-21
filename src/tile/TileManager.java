package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tile;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		
		getTileImage();
	}
	
	public void getTileImage() {
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(new File("../resources/tile/ubin.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {
			g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			if(col==gp.getMaxScreenCol()) {
				col=0;
				x = 0;
				row++;
				y+=gp.tileSize;
			}
		}
	}
}
