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
	int worldMapTileNum[][];
	File mapFile = new File("../resources/map/map.txt");
	File worldMapFile = new File("../resources/map/world.txt");
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[30];
		mapTileNum = new int[10][10];
		worldMapTileNum = new int[64][64];
		
		getTileImage();
		loadMap();
		loadWorldMap();
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
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(new File("../resources/world/grass_flower.png"));

			tile[10] = new Tile();
			tile[10].image = ImageIO.read(new File("../resources/world/grass.png"));
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(new File("../resources/world/grass_grass.png"));

			tile[12] = new Tile();
			tile[12].image = ImageIO.read(new File("../resources/world/grass_tree.png"));

			tile[13] = new Tile();
			tile[13].image = ImageIO.read(new File("../resources/world/ground.png"));

			tile[14] = new Tile();
			tile[14].image = ImageIO.read(new File("../resources/world/lake.png"));

			tile[15] = new Tile();
			tile[15].image = ImageIO.read(new File("../resources/world/lake_upleft.png"));

			tile[16] = new Tile();
			tile[16].image = ImageIO.read(new File("../resources/world/lake_up.png"));

			tile[17] = new Tile();
			tile[17].image = ImageIO.read(new File("../resources/world/lake_upright.png"));

			tile[18] = new Tile();
			tile[18].image = ImageIO.read(new File("../resources/world/lake_right.png"));

			tile[19] = new Tile();
			tile[19].image = ImageIO.read(new File("../resources/world/lake_downright.png"));

			tile[20] = new Tile();
			tile[20].image = ImageIO.read(new File("../resources/world/lake_down.png"));

			tile[21] = new Tile();
			tile[21].image = ImageIO.read(new File("../resources/world/lake_downleft.png"));

			tile[22] = new Tile();
			tile[22].image = ImageIO.read(new File("../resources/world/lake_left.png"));

			tile[23] = new Tile();
			tile[23].image = ImageIO.read(new File("../resources/world/lake_letterl.png"));
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

	public void loadWorldMap(){
		try {
			InputStream is = new FileInputStream(worldMapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while(col < 64 && row < 64){
				String line = br.readLine();

				while(col < 64){
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					worldMapTileNum[col][row] = num;
					col++;
				}
				if(col == 64)  {
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
	public void drawWorld(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < 64 && row < 64) {

			int tileNum = worldMapTileNum[col][row];

			g2.drawImage(tile[tileNum].image, x, y, gp.worldTileSize, gp.worldTileSize, null);
			col++;
			x += gp.worldTileSize;
			if(col==64) {
				col=0;
				x = 0;
				row++;
				y+=gp.worldTileSize;
			}
		}
	}
}
