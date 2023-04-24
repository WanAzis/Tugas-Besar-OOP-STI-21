package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

public class Toilet extends Objek{

	public Toilet() {
		name = "Toilet";
		action = "buang air";
		panjang = 1;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/toilet.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
