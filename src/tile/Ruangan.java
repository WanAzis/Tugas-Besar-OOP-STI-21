package tile;

import objek.barang.*;

import java.util.ArrayList;

public class Ruangan
{
    private int sisaLuas;
    private String nama;
    private Ruangan[] ruanganTetangga;
    private ArrayList<Barang> listBarang;

    public Ruangan(String nama)
    {
        this.nama = nama;
        this.listBarang = new ArrayList<Barang>();
        this.ruanganTetangga = new Ruangan[4];
    }

    //Getter and setter

    public Ruangan getRuanganTetangga(int index)
    {
        return ruanganTetangga[index];
    }

    public void setRuanganTetangga(int index, Ruangan ruangan)
    {
        ruanganTetangga[index] = ruangan;
    }

    public String getNama()
    {
        return this.nama;
    }

    public void setNama(String nama)
    {
        this.nama = nama;
    }

    public ArrayList<Barang> getListBarang() 
    {
        return listBarang;
    }
}
/* yang masih kurang
1. sisaLuas itu gimana???
2. mungkin tambah addBarang
3. setListBarang gimana??
*/