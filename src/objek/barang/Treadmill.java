package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class Treadmill extends Barang{

	public Treadmill(GamePanel gp) {
        this.gp = gp;
        name = "Treadmill";
		action = "OLAHRAGA";
		deskripsi = "[ " + name + " ] \nDibutuhkan \nuntuk olahraga"; 
		panjang = 2;
		lebar = 1;
		harga = 200;
		screenX = gp.tileSize;
		screenY = gp.tileSize;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		direction="up";
        image = up;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/treadmill/treadmill_down.png"));
			left = ImageIO.read(new File("../resources/barang/treadmill/treadmill_left.png"));
			right = ImageIO.read(new File("../resources/barang/treadmill/treadmill_right.png"));
			up = ImageIO.read(new File("../resources/barang/treadmill/treadmill_up.png"));
			downUsed = ImageIO.read(new File("../resources/barang/treadmill/treadmill_used_down.png"));
			leftUsed = ImageIO.read(new File("../resources/barang/treadmill/treadmill_used_left.png"));
			rightUsed = ImageIO.read(new File("../resources/barang/treadmill/treadmill_used_right.png"));
			upUsed = ImageIO.read(new File("../resources/barang/treadmill/treadmill_used_up.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
        gp.curSim.setStatus(action);
		switch(direction){
			case "down" : image=downUsed; break;
			case "left" : image=leftUsed; break;
			case "right" : image=rightUsed; break;
			case "up" : image=upUsed; break;
		}
	}

	@Override
	public void unUsed() {
        gp.curSim.setStatus("IDLE");
		switch(direction){
			case "down" : image=down; break;
			case "left" : image=left; break;
			case "right" : image=right; break;
			case "up" : image=up; break;
		}
	}

    @Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.plusKekenyangan(-20);
			sim.plusMood(10);
			gp.ui.setNotifMessage("Selamat anda sudah olahraga, \nkekenyangan -20 dan mood +10");
			gp.gameState=gp.notifState;
			sim.getPlayerImage();
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
			direction="up";
			image=up;
			swapSize();
		}
		else if(direction=="up"){
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


//gabisa rotate