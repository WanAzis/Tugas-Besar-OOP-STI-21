package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TumisSayur extends Masakan{

    public TumisSayur(GamePanel gp) {
        super(gp);
        name = "Tumis Sayur";
        loadImage();
        deskripsi = "[ " + name + " ] \nkekenyangan +5";
        listBahan = new BahanMakanan[2];
        setListBahan();
        kekenyangan = 5;
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
        listBahan[0] = new Wortel(gp);
        //BAHAN 2
        listBahan[1] = new Bayam(gp);
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../../resources/makanan/masakan/TumisSayur.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
