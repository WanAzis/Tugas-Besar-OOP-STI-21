package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Kacang extends BahanMakanan{

    public Kacang(GamePanel gp) {
        super(gp);
        name = "Kacang";
        deskripsi = "[ " + name + " ] \nkekenyangan +2"; 
        loadImage();
        harga = 2;
        kekenyangan = 2;
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/kacang.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
