package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Kasur extends Objek{

	public Kasur() {
		name = "Kasur";
		panjang = 4;
		lebar = 1;
		solidArea = new Rectangle(0,0,48*lebar,48*panjang);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/barang/kasursingle1.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
