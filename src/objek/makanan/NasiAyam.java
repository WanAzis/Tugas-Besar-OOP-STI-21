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
        deskripsi = "[ " + name + " ] \nkekenyangan +16";
        listBahan = new BahanMakanan[2];
        setListBahan();
        kekenyangan = 16;
    }

    //GETTER
    public String getName(){
        return name;
    }
    public String getDeskripsi(){
        return deskripsi;
    }
    public BahanMakanan[] getListBahan(){
        return listBahan;
    }
    public int getKekenyangan(){
        return kekenyangan;
    }
    //SETTER
    @Override
    public void setListBahan(){
        listBahan[0] = new Nasi(gp);
        //BAHAN 2
        listBahan[1] = new Ayam(gp);
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/masakan/NasiAyam.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
