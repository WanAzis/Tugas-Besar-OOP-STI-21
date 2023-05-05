package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class RakBuku extends Barang{

	public RakBuku(GamePanel gp) {
		this.gp = gp;
		name = "Rak buku";
		action = "MEMBACA";
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk membaca"; 
		panjang = 3;
		lebar = 2;
		harga = 150;
		screenX = gp.tileSize;
		screenY = gp.tileSize;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/rakbuku/rakbuku.png"));
			left = ImageIO.read(new File("../resources/barang/rakbuku/rakbuku_kiri.png"));
			right = ImageIO.read(new File("../resources/barang/rakbuku/rakbuku_kanan.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() { 
		gp.curSim.setStatus(action);
		// switch(direction){
		// 	case "down" : image=down; break; //up comment di line 35
		// 	case "left" : image=left; break;
		// 	case "right" : image=right; break;
		// }
	}

	@Override
	public void unUsed() {
		gp.curSim.setStatus("IDLE");
		// switch(direction){
		// 	case "down" : image=down; break;
		// 	case "left" : image=left; break;
		// 	case "right" : image=right; break;
		// }
	}

	@Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.plusMood(10);
			gp.ui.setNotifMessage("Selamat anda sudah membaca, \nmood +10");
			gp.gameState=gp.notifState;
			// sim.getPlayerImage(); ini gatau kalo baca gambarannya gmn guys? ini asumsi klo dia ga ilang ya ky berdiri doang
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

	@Override
	public void rotate() {
		if(direction=="down"){
			direction="left";
			image = left;
			swapSize();
		}
		else if(direction=="left"){
			direction="right";
			image=right;
			swapSize();
		}
		else if(direction=="right"){
			direction="down";
			image=down;
			swapSize();
		}
	}
	private void swapSize(){
		int temp = panjang;
		panjang = lebar;
		lebar = temp;

		temp = solidArea.height;
		solidArea.height = solidArea.width;
		solidArea.width = temp;
	}
}
