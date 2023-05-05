package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Bayam extends BahanMakanan{

    public Bayam(GamePanel gp) {
        super(gp);
        name = "Bayam";
        deskripsi = "[ " + name + " ] \nkekenyangan +2"; 
        loadImage();
        harga = 3;
        kekenyangan = 2;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/bayam.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
