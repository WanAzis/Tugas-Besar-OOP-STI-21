package objek;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

public class Jam extends Objek{

    public Jam() {
		name = "Jam";
		panjang = 1;
		lebar = 1;
		solidArea = new Rectangle(3,3,48*lebar,48*panjang);
		try {
			image = ImageIO.read(new File("../resources/barang/jam1.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
