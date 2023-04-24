package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

public class Kasur extends Objek{

	public Kasur() {
		name = "Kasur";
		action = "tidur";
		panjang = 4;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/kasursingle1.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
