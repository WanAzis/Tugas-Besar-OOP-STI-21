package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.File;

public class KomporGas extends Kompor{

	public KomporGas(GamePanel gp) {
		super(gp);
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk tidur"; 
		panjang = 2;
		lebar = 1;
		harga = 100;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/komgas/komgas3.png"));
			left = ImageIO.read(new File("../resources/barang/komgas/komgas4.png"));
			right = ImageIO.read(new File("../resources/barang/komgas/komgas2.png"));
			up = ImageIO.read(new File("../resources/barang/komgas/komgas1.png"));
			// downUsed = ImageIO.read(new File("../resources/barang/komgas/komgas_down_used.png"));
			// leftUsed = ImageIO.read(new File("../resources/barang/komgas/komgas_left_used.png"));
			// rightUsed = ImageIO.read(new File("../resources/barang/komgas/komgas_right_used.png"));
			// upUsed = ImageIO.read(new File("../resources/barang/komgas/komgas_up_used.png"));
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