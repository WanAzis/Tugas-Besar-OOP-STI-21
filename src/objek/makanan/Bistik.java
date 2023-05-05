package objek.makanan;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Bistik extends Masakan{

    public Bistik(GamePanel gp) {
        super(gp);
        name = "Bistik";
        loadImage();
        deskripsi = "[ " + name + " ] \nkekenyangan +22";
        listBahan = new BahanMakanan[2];
        setListBahan();
        kekenyangan = 22;
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
        listBahan[0] = new Kentang(gp);
        //BAHAN 2
        listBahan[1] = new Sapi(gp);
    }

    private void loadImage(){
        try {
			image = ImageIO.read(new File("../resources/makanan/masakan/Bistik.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
