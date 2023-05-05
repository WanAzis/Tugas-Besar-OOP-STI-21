package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Sapi extends BahanMakanan{

    public Sapi(GamePanel gp) {
        super(gp);
        name = "Sapi";
        deskripsi = "[ " + name + " ] \nkekenyangan +15"; 
        loadImage();
        harga = 12;
        kekenyangan = 15;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/sapi.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
