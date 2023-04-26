import java.util.ArrayList;
import java.util.List;

public class Rumah
{
    Point lokasi;
    private int luas;
    private List<Ruangan> listRuangan;

    public Rumah()
    {
        this.listRuangan = new ArrayList<Ruangan>();
        Room kamar = new Room("Kamar");
        this.listRuangan.add(kamar);
        
        // gimana cara nambahin furniture ke list inventory sim

    }

    public void tambahRuang(String arah, String newRuangan, Ruangan currentRuangan)
    {
        Ruangan ruanganBaru = new Room(newRuangan)
        if(arah.equals("Atas"))
        {
            if(currentRuangan.getRuanganTetangga(0) == null)
            {
                currentRuangan.setRuanganTetannga(0, ruanganBaru);
            }
            else
            {
                System.out.printlnt("Sudah ada ruangan di lokasi tersebut.")
            }
        }
        else if(arah.equals("Bawah"))
        {
            if(currentRuangan.getRuanganTetangga(1) == null)
            {
                currentRuangan.setRuanganTetannga(1, ruanganBaru);
            }
            else
            {
                System.out.printlnt("Sudah ada ruangan di lokasi tersebut.")
            }
        }
        else if(arah.equals("Kanan"))
        {
            if(currentRuangan.getRuanganTetangga(2) == null)
            {
                currentRuangan.setRuanganTetannga(2, ruanganBaru);
            }
            else
            {
                System.out.printlnt("Sudah ada ruangan di lokasi tersebut.")
            }
        }
        else if(arah.equals("Kiri"))
        {
            if(currentRuangan.getRuanganTetangga(3) == null)
            {
                currentRuangan.setRuanganTetannga(3, ruanganBaru);
            }
            else
            {
                System.out.printlnt("Sudah ada ruangan di lokasi tersebut.")
            }
        }
    }
}

/* yang masih kurang:
1. cara nambah furniture ke inventory sim di konstruktor gimana?
2. setter getter untuk list
*/