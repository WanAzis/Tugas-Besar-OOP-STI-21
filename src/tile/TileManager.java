package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	File mapFile = new File("../resources/map/map.txt");
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[10][10];
		
		getTileImage();
		loadMap();
	}
	
	public void getTileImage() {
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(new File("../resources/tile/jendela.png"));

			tile[1] = new Tile();
			tile[1].image = ImageIO.read(new File("../resources/tile/ubin_bawah.png"));

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(new File("../resources/tile/ubin_tembok.png"));

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(new File("../resources/tile/tembok.png"));

			tile[4] = new Tile();
			tile[4].image = ImageIO.read(new File("../resources/tile/frame_atas.png"));

			tile[5] = new Tile();
			tile[5].image = ImageIO.read(new File("../resources/tile/frame_pojok_kiri.png"));

			tile[6] = new Tile();
			tile[6].image = ImageIO.read(new File("../resources/tile/frame_kiri.png"));

			tile[7] = new Tile();
			tile[7].image = ImageIO.read(new File("../resources/tile/frame_pojok_kanan.png"));

			tile[8] = new Tile();
			tile[8].image = ImageIO.read(new File("../resources/tile/frame_kanan.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(){
		try {
			InputStream is = new FileInputStream(mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while(col < 10 && row < 10){
				String line = br.readLine();

				while(col < 10){
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}
				if(col == 10)  {
					col = 0;
					row++;
				}
			}br.close();

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

			int tileNum = mapTileNum[col][row];

			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
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
