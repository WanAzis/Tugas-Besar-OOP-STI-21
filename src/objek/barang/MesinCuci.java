package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class MesinCuci extends Barang{

	public MesinCuci(GamePanel gp) {
		this.gp = gp;
		name = "Mesin cuci";
		action = "MENCUCI";
		deskripsi = "[ " + name + " ] \nDibutuhkan \nuntuk mencuci";
		panjang = 2;
		lebar = 2;
		harga = 200;
		screenX = gp.tileSize;
		screenY = gp.tileSize*3;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
		direction="down";

	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/mesincuci/mesincuci_down.png"));
			left = ImageIO.read(new File("../resources/barang/mesincuci/mesincuci_samping.png"));
			right = ImageIO.read(new File("../resources/barang/mesincuci/mesincuci_samping.png"));
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
		switch(direction){
			case "down" : image=down; break;
			case "left" : image=left; break;
			case "right" : image=right; break;
		}
	}

	@Override
	public void effect(Sim sim, int duration) {
		counter++;
		if(counter>=duration){
			unUsed();
			counter=0;
			sim.plusKekenyangan(-5);
			sim.plusMood(5);
			gp.ui.setNotifMessage("Selamat anda sudah mencuci, \nkekenyangan -5 dan mood +5");
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
