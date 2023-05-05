package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Susu extends BahanMakanan{

    public Susu(GamePanel gp) {
        super(gp);
        name = "Susu";
        deskripsi = "[ " + name + " ] \nkekenyangan +1"; 
        loadImage();
        harga = 2;
        kekenyangan = 1;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/susu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
