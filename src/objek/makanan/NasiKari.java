package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NasiKari extends Masakan{

    public NasiKari(GamePanel gp) {
        super(gp);
        name = "Nasi Kari";
        loadImage();
        deskripsi = "[ " + name + " ] \nkekenyangan +30";
        listBahan = new BahanMakanan[4];
        setListBahan();
        kekenyangan = 30;
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
        listBahan[1] = new Kentang(gp);
        listBahan[2] = new Wortel(gp);
        listBahan[3] = new Sapi(gp);
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/masakan/NasiKari.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
