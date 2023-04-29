package objek.barang;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Sim;
import main.GamePanel;

import java.io.File;

public class MejaKursi extends Barang{

	public MejaKursi(GamePanel gp) {
		this.gp = gp;
		name = "Meja kursi";
		action = "MAKAN";
		deskripsi = "[ " + name + " ] \nDibutuhkan untuk makan";
		panjang = 3;
		lebar = 3;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		loadImage();
		image = down;
	}

	private void loadImage(){
		try {
			down = ImageIO.read(new File("../resources/barang/toilet/toilet_down.png"));
			left = ImageIO.read(new File("../resources/barang/toilet/toilet_left.png"));
			right = ImageIO.read(new File("../resources/barang/toilet/toilet_right.png"));
			up = ImageIO.read(new File("../resources/barang/toilet/toilet_up.png"));
			// downUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_down_used.png"));
			// leftUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_left_used.png"));
			// rightUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_right_used.png"));
			// upUsed = ImageIO.read(new File("../resources/barang/kasur/kasursingle_up_used.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void used() {
		gp.sim.setStatus(action);
		image = left;
	}

	@Override
	public void unUsed(){
		gp.sim.setStatus("IDLE");
		image = down;
	}
}