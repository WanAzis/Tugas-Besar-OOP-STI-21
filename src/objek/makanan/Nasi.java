package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Nasi extends BahanMakanan{

    public Nasi(GamePanel gp) {
        super(gp);
        name = "Nasi";
        deskripsi = "[ " + name + " ] \nkekenyangan +5"; 
        loadImage();
        harga = 5;
        kekenyangan = 5;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/nasi.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
