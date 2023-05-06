package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Jam extends Barang{

    public Jam(GamePanel gp) {
		this.gp = gp;
		name = "Jam";
		action = "LIHAT WAKTU";
		deskripsi = "[ " + name + " ] \nDibutuhkan\nuntuk melihat\n waktu"; 
		screenX = gp.tileSize;
		screenY= gp.tileSize;
		panjang = 1;
		lebar = 1;
		harga = 10;
		solidArea = new Rectangle(3,3,48*lebar,48*panjang*2+10);
		try {
			image = ImageIO.read(new File("../resources/barang/jam.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
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
