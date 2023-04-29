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
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk mencuci";
		panjang = 2;
		lebar = 2;
		screenX = gp.tileSize;
		screenY = gp.tileSize;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_down.png"));
			left = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_left.png"));
			right = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_right.png"));
			up = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_up.png"));
			// downUsed = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_down_used.png")); kayaknya buat nyuci sekedar dia berdiri aja di depan mesin cuci tapi gabisa gerak?
			// leftUsed = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_left_used.png")); kalo ternyata ada gambarnya kabarin
			// rightUsed = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_right_used.png"));
			// upUsed = ImageIO.read(new File("../resources/barang/MesinCuci/MesinCuci_up_used.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		gp.sim.setStatus(action);
		switch(direction){
			case "down" : image=down; break; //up comment line 35
			case "left" : image=left; break;
			case "right" : image=right; break;
			case "up" : image=up; break;
		}
	}

	@Override
	public void unUsed() {
		gp.sim.setStatus("IDLE");
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
			sim.plusKekenyangan(-5);
			sim.plusMood(5);
			gp.ui.setNotifMessage("Selamat anda sudah mencuci, \nkekenyangan -5 dan mood +5");
			gp.gameState=gp.notifState;
		}
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
