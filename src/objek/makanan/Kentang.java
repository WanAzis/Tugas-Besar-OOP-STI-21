package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Kentang extends BahanMakanan{

    public Kentang(GamePanel gp) {
        super(gp);
        name = "Kentang";
        deskripsi = "[ " + name + " ] \nkekenyangan +4"; 
        loadImage();
        harga = 3;
        kekenyangan = 4;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/kentang.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
