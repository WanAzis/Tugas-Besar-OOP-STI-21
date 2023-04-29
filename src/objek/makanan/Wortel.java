package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Wortel extends BahanMakanan{

    public Wortel(GamePanel gp) {
        super(gp);
        name = "Wortel";
        deskripsi = "[ " + name + " ] \nkekenyangan +2"; 
        loadImage();
        harga = 3;
        kekenyangan = 2;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../../resources/makanan/wortel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
