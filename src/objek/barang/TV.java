package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class TV extends Barang{

	public TV(GamePanel gp) {
		this.gp = gp;
		name = "TV";
		action = "MENONTON";
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk menonton"; 
		panjang = 2;
		lebar = 2;
		harga = 200;
		screenX = gp.tileSize;
		screenY = gp.tileSize;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/TV/TV_down.png"));
			left = ImageIO.read(new File("../resources/barang/TV/TV_left.png"));
			right = ImageIO.read(new File("../resources/barang/TV/TV_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		gp.curSim.setStatus(action);
		// switch(direction){
		// 	case "down" : image=downUsed; break;
		// 	case "left" : image=leftUsed; break;
		// 	case "right" : image=rightUsed; break;
		// 	case "up" : image=upUsed; break;
		// }
	}

	@Override
	public void unUsed() {
		gp.curSim.setStatus("IDLE");
		// switch(direction){
		// 	case "down" : image=down; break;
		// 	case "left" : image=left; break;
		// 	case "right" : image=right; break;
		// 	case "up" : image=up; break;
		// }
	}

	@Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.setMood(10);
			sim.setKekenyangan(-5);
			gp.ui.setNotifMessage("Selamat anda sudah menonton TV, \nkekenyangan -5 dan mood +10");
			gp.gameState=gp.notifState;
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
			// swapSize();
		}
		else if(direction=="left"){
			direction="up";
			image=up;
			// swapSize();
		}
		else if(direction=="up"){
			direction="right";
			image=right;
			// swapSize();
		}
		else if(direction=="right"){
			direction="down";
			image=down;
			// swapSize();
		}
	}
	// private void swapSize(){
	// 	int temp = panjang;
	// 	panjang = lebar;
	// 	lebar = temp;

	// 	temp = solidArea.height;
	// 	solidArea.height = solidArea.width;
	// 	solidArea.width = temp;
	// }
}


//gabisa rotate