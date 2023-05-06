package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.File;

public class KasurSingle extends Kasur{

	public KasurSingle(GamePanel gp) {
		super(gp);
		name = "Kasur Single";
		deskripsi = "[ " + name + " ] \nDibutuhkan\nuntuk tidur"; 
		panjang = 4;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		harga = 50;
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/kasur/kasursingle_down.png"));
			left = ImageIO.read(new File("../resources/barang/kasur/kasursingle_left.png"));
			right = ImageIO.read(new File("../resources/barang/kasur/kasursingle_right.png"));
			up = ImageIO.read(new File("../resources/barang/kasur/kasursingle_up.png"));
			downUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_down_used.png"));
			leftUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_left_used.png"));
			rightUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_right_used.png"));
			upUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_up_used.png"));
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
