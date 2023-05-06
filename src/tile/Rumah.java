package tile;
import java.util.ArrayList;

import entity.Sim;

public class Rumah
{
    public Point lokasi;
    public ArrayList<Ruangan> listRuangan;
    public Sim haveSim;

    public Rumah(Sim sim)
    {
        this.listRuangan = new ArrayList<Ruangan>();
        Ruangan kamar = new Ruangan("Kamar");
        this.listRuangan.add(kamar);
        haveSim = sim;
    }

    //GETTER

    //SETTER
    

    // public Ruangan getRuangan(int index)
    // {
    //     return this.listRuangan.get(index);
    // }

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
}

/* yang masih kurang:
1. cara nambah furniture ke inventory sim di konstruktor gimana?
2. setter getter untuk list
*/