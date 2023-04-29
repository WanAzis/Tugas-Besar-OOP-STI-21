package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Ayam extends BahanMakanan{

    public Ayam(GamePanel gp) {
        super(gp);
        name = "Ayam";
        deskripsi = "[ " + name + " ] \nkekenyangan +8"; 
        loadImage();
        harga = 10;
        kekenyangan = 8;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../../resources/makanan/ayam.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
