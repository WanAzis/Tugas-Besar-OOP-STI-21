package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.File;

public class MejaKursi extends Barang{

	public MejaKursi(GamePanel gp) {
		this.gp = gp;
		name = "Meja kursi";
		action = "MAKAN";
		deskripsi = "[ " + name + " ] \nDibutuhkan \nuntuk makan";
		panjang = 3;
		lebar = 3;
		harga = 50;
		screenX=gp.tileSize;
		screenY=gp.tileSize*3+gp.tileSize/2;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/mejakursimakan/mejakursimakan.png"));
			downUsed = ImageIO.read(new File("../resources/barang/mejakursimakan/mejakursimakan_used.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		gp.curSim.setStatus(action);
		image=downUsed;
	}

	@Override
	public void unUsed(){
		gp.curSim.setStatus("IDLE");
		image=down;
	}

	@Override
	public void moveUp() {
		screenY -= gp.tileSize;
		solidArea.y -= gp.tileSize;
	}
	@Override
	public void moveDown() {
		screenY += gp.tileSize;
		solidArea.y += gp.tileSize;
	}
	@Override
	public void moveLeft() {
		screenX -= gp.tileSize;
		solidArea.x -= gp.tileSize;
	}
	@Override
	public void moveRight() {
		screenX += gp.tileSize;
		solidArea.x += gp.tileSize;
	}
}
