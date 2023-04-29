package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NasiAyam extends Masakan{

    public NasiAyam(GamePanel gp) {
        super(gp);
        name = "Nasi Ayam";
        loadImage();
        deskripsi = "";
        listBahan = new BahanMakanan[2];
        setListBahan();
        kekenyangan = 16;
    }

    //GETTER

    //SETTER
    @Override
    public void setListBahan(){
        listBahan[0] = new Nasi(gp);
        //BAHAN 2
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../../resources/makanan/masakan/nasi.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
