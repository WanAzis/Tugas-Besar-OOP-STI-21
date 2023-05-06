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
		deskripsi = "[ " + name + " ] \nDibutuhkan \nuntuk menonton"; 
		panjang = 2;
		lebar = 2;
		harga = 200;
		screenX = gp.tileSize;
		screenY = gp.tileSize*3;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		direction="down";
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/tv/tv_down.png"));
			left = ImageIO.read(new File("../resources/barang/tv/tv_left.png"));
			right = ImageIO.read(new File("../resources/barang/tv/tv_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		gp.curSim.setStatus(action);
	}

	@Override
	public void unUsed() {
		gp.curSim.setStatus("IDLE");
	}

	@Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.plusMood(10);
			sim.plusKekenyangan(-5);
			gp.ui.setNotifMessage("Selamat anda sudah menonton TV, \nkekenyangan -5 dan mood +10");
			gp.gameState=gp.notifState;
		}
	}


	@Override
	public void moveUp() {
		screenY -= gp.tileSize;
	}
	@Override
	public void moveDown() {
		screenY += gp.tileSize;
	}
	@Override
	public void moveLeft() {
		screenX -= gp.tileSize;
	}
	@Override
	public void moveRight() {
		screenX += gp.tileSize;
	}
	@Override
	public void rotate() {
		if(direction=="down"){
			direction="left";
			image = left;
		}
		else if(direction=="left"){
			direction="right";
			image=right;
		}
		else if(direction=="right"){
			direction="down";
			image=down;
		}
	}
}