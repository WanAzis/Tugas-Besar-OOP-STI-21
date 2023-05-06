package tile;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;

import entity.Sim;
import main.GamePanel;

public class Rumah
{
    public GamePanel gp;
    public Point lokasi;
    public ArrayList<Ruangan> listRuangan;
    public Sim haveSim;
    public BufferedImage image;

    public Rumah(Sim sim, GamePanel gp)
    {
        this.gp = gp;
        lokasi = new Point();
        this.listRuangan = new ArrayList<Ruangan>();
        Ruangan kamar = new Ruangan("Kamar");
        this.listRuangan.add(kamar);
        haveSim = sim;
        try {
			image = ImageIO.read(new File("../resources/world/house.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    //GETTER
    public Point getLokasi(){return lokasi;}
    public Ruangan getRuangan(int index)
    {
        return this.listRuangan.get(index);
    }

    //SETTER
    public void setLokasi(Point lokasi){this.lokasi = lokasi;}


    public void tambahRuang(String arah, String newRuangan, Ruangan currentRuangan)
    {
        Ruangan ruanganBaru = new Ruangan(newRuangan);
        listRuangan.add(ruanganBaru);
        haveSim.setItems();
        if(arah.equals("Atas"))
        {
            if(currentRuangan.getRuanganTetangga(0) == null)
            {
                currentRuangan.setRuanganTetangga(0, ruanganBaru);
                ruanganBaru.setRuanganTetangga(1, currentRuangan);
            }
            else
            {
                System.out.println("Sudah ada ruangan di lokasi tersebut.");
            }
        }
        else if(arah.equals("Bawah"))
        {
            if(currentRuangan.getRuanganTetangga(1) == null)
            {
                currentRuangan.setRuanganTetangga(1, ruanganBaru);
                ruanganBaru.setRuanganTetangga(0, currentRuangan);
            }
            else
            {
                System.out.println("Sudah ada ruangan di lokasi tersebut.");
            }
        }
        else if(arah.equals("Kiri"))
        {
            if(currentRuangan.getRuanganTetangga(2) == null)
            {
                currentRuangan.setRuanganTetangga(2, ruanganBaru);
                ruanganBaru.setRuanganTetangga(3, currentRuangan);
            }
            else
            {
                System.out.println("Sudah ada ruangan di lokasi tersebut.");
            }
        }
        else if(arah.equals("Kanan"))
        {
            if(currentRuangan.getRuanganTetangga(3) == null)
            {
                currentRuangan.setRuanganTetangga(3, ruanganBaru);
                ruanganBaru.setRuanganTetangga(2, currentRuangan);
            }
            else
            {
                System.out.println("Sudah ada ruangan di lokasi tersebut.");
            }
        }
    }

    public void moveUp(){
        lokasi.setY(lokasi.getY()-1);
    }
    public void moveDown(){
        lokasi.setY(lokasi.getY()+1);
    }
    public void moveLeft(){
        lokasi.setX(lokasi.getX()-1);
    }
    public void moveRight(){
        lokasi.setX(lokasi.getX()+1);
    }

    public void draw(Graphics2D g2) {
		g2.drawImage(image, lokasi.getX()*gp.worldTileSize, lokasi.getY()*gp.worldTileSize, gp.worldTileSize, gp.worldTileSize, null);
	}
}